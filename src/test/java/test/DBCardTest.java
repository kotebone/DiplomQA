package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DBUtils;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.OfferTourPage;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBCardTest {
    OfferTourPage offerTourPage;

    @BeforeAll
    static void allureSetup() {
        SelenideLogger.addListener("allure", new AllureSelenide().
                screenshots(true).savePageSource(false));
    }

    @BeforeEach
    void browserSetUp() {
        open(System.getProperty("sut.url"));
        offerTourPage = new OfferTourPage();
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @AfterAll
    static void tearDownAllure() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Сохранение платежа по действующей карте в БД в одобренных со страницы оплаты")
    void shouldApprovePaymentsWithApprovedCardOnPaymentPageTest() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.getAnyNotification();
        assertEquals("APPROVED", DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Сохранение платежа по недействующей карте в БД в отклоненных со страницы оплаты")
    void shouldDeclinePaymentsWithDeclinedCardOnPaymentPageTest() {
        var paymentPage = offerTourPage.payByCard();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
        paymentPage.getAnyNotification();
        assertEquals("DECLINED", DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Не сохранять номер карты в БД при заказе со страницы оплаты")
    void shouldNotSaveCreditIdOnPaymentPageTest() throws InterruptedException {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.getAnyNotification();
        assertEquals("null", DBUtils.getCreditId());
    }
}