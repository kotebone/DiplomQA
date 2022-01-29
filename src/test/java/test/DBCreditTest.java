package test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverConditions;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DBUtils;
import data.DataHelper;
import io.github.bonigarcia.wdm.webdriver.WebDriverBrowser;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import page.OfferTourPage;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBCreditTest {
    OfferTourPage offerTourPage;

    @BeforeAll
    static void allureSetup() {
        SelenideLogger.addListener("allure", new AllureSelenide().
                screenshots(true).savePageSource(false));
    }

    @BeforeEach
    void browserSetUp() {
        open("http://localhost:8080/");
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
    @DisplayName("Сохранение платежа по действующей карте в БД в одобренных со страницы кредита")
    void shouldApprovePaymentsWithApprovedCardOnCreditPageTest() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.getAnyNotification();
        assertEquals("APPROVED", DBUtils.getCreditStatus());
    }
}