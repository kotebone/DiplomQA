package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.OfferTourPage;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UITest {
    OfferTourPage offerTourPage;

    @BeforeAll
    static void allureSetup() {
        SelenideLogger.addListener("allure", new AllureSelenide().
                screenshots(true).savePageSource(false));
    }

    @BeforeEach
    void browserSetUp(){
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
    @DisplayName("Отправить корректно заполненную форму и одобрить операцию по действующей карте на странице оплаты")
    void shouldSuccessCorrectFormAndApprovedCardPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и одобрить операцию по действующей карте на странице оформления кредита")
    void shouldSuccessCorrectFormAndApprovedCardPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и отклонить операцию по заблокированной карте на странице оплаты")
    void shouldGetErrorCorrectFormAndDeclinedCardPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        paymentPage.unsuccessfulSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и отклонить операцию по заблокированной карте на странице оформления кредита")
    void shouldGetErrorCorrectFormAndDeclinedCardPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        creditPage.unsuccessfulSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
    }

    @Test
    @DisplayName("Успешное переключение со страницы оплаты на страницу оформления кредита")
    void shouldSwitchFromPayByCardToPayByCreditTest() {
        var paymentPage = offerTourPage.payByCard();
        var creditPage = paymentPage.payByCredit();
    }

    @Test
    @DisplayName("Успешное переключение со страницы оформления кредита на страницу оплаты")
    void shouldSwitchFromPayByCreditToPayByCardTest() {
        var creditPage = offerTourPage.payByCredit();
        var paymentPage = creditPage.payByCard();
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и одобрить операцию по активной карте со сроком истечения 1 месяц")
    void shouldSuccessCorrectFormAndOkDateCardPayByCard4Test() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(1);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и одобрить операцию по активной карте со сроком истечения в этом месяце")
    void shouldSuccessCorrectFormAndOkDateCardPayByCard5Test() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(0);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и получить ошибку при оформлении покупки по карте, истекшей в прошлом месяце")
    void shouldSuccessCorrectFormAndBadDateCardPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(-1);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и одобрить кредит по активной карте со сроком истечения 1 месяц")
    void shouldSuccessCorrectFormAndOkDateCardPayByCredit4Test() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(1);
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и одобрить кредит по активной карте со сроком истечения в этом месяце")
    void shouldSuccessCorrectFormAndOkDateCardPayByCredit5Test() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(0);
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и получить ошибку при оформлении кредита по карте, истекшей в прошлом месяце")
    void shouldSuccessCorrectFormAndBadDateCardPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(-1);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и одобрить операцию по активной карте с указанием 01 месяца")
    void shouldSuccessCorrectFormAndOkDateCardPayByCard01Test() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при оформлении покупки по активной карте с указанием 00 месяца")
    void shouldSuccessCorrectFormAndOkDateCardPayByCard00Test() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и одобрить кредит по активной карте с указанием 12 месяца")
    void shouldSuccessCorrectFormAndOkDateCardPayByCard12Test() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Отправить корректно форму и получить ошибку при оформлении покупки по активной карте с указанием 13 месяца")
    void shouldSuccessCorrectFormAndBadDateCardPayByCard13Test() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и одобрить кредит по активной карте с указанием 01 месяца")
    void shouldSuccessCorrectFormAndOkDateCardPayByCredit01Test() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при оформлении кредита по активной карте с указанием 00 месяца")
    void shouldSuccessCorrectFormAndOkDateCardPayByCredit00Test() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить корректно заполненную форму и одобрить кредит по активной карте с указанием 12 месяца")
    void shouldSuccessCorrectFormAndOkDateCardPayByCredit12Test() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при оформлении кредита по активной карте с указанием 13 месяца")
    void shouldSuccessCorrectFormAndBadDateCardPayByCredit13Test() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Получить ошибку при отправке пустой формы со страницы оплаты")
    void shouldGetErrorWhenSendClearFormPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.sendClearForm();
    }

    @Test
    @DisplayName("Получить ошибку при отправке пустой формы со страницы оформления кредита")
    void shouldGetErrorWhenSendClearFormPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.sendClearForm();
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при отправке пустого поля 'Номер карты' на странице оплаты")
    void shouldGetErrorWhenSendClearCardNumberPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo("", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при отправке пустого поля 'Месяц' на странице оплаты")
    void shouldGetErrorWhenSendClearMonthPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при отправке пустого поля 'Год' на странице оплаты")
    void shouldGetErrorWhenSendClearYearPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при отправке пустого поля 'Владелец' на странице оплаты")
    void shouldGetErrorWhenSendClearCardHolderPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "", approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при отправке пустого поля 'CVC/CVV' на странице оплаты")
    void shouldGetErrorWhenSendClearCVVPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "");
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при отправке пустого поля 'Номер карты' на странице оформления кредита")
    void shouldGetErrorWhenSendClearCardNumberPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo("", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при отправке пустого поля 'Месяц' на странице оформления кредита")
    void shouldGetErrorWhenSendClearMonthPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при отправке пустого поля 'Год' на странице оформления кредита")
    void shouldGetErrorWhenSendClearYearPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при отправке пустого поля 'Владелец' на странице оформления кредита")
    void shouldGetErrorWhenSendClearCardHolderPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "", approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Отправить заполненную форму и получить ошибку при отправке пустого поля 'CVC/CVV' на странице оформления кредита")
    void shouldGetErrorWhenSendClearCVVPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "");
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Не разрешать ввод латиницы в поле 'Номер карты' на странице оплаты")
    void shouldNotAllowEnSymbolsInCardNumberPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getCardNumberField().setValue(DataHelper.getRandomEnSymbols());
        assertEquals("", paymentPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод кириллицы в поле 'Номер карты' на странице оплаты")
    void shouldNotAllowRusSymbolsInCardNumberPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getCardNumberField().setValue(DataHelper.getRandomEnSymbols());
        assertEquals("", paymentPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод спецсимволов в поле 'Номер карты' на странице оплаты")
    void shouldNotAllowSpecialSymbolsInCardNumberPayByCardTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getCardNumberField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод латиницы в поле 'Номер карты' на странице оформления кредита")
    void shouldNotAllowEnSymbolsInCardNumberPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getCardNumberField().setValue(DataHelper.getRandomEnSymbols());
        assertEquals("", creditPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод кириллицы в поле 'Номер карты' на странице оформления кредита")
    void shouldNotAllowRusSymbolsInCardNumberPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getCardNumberField().setValue(DataHelper.getRandomRusSymbols());
        assertEquals("", creditPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод спецсимволов в поле 'Номер карты' на странице оформления кредита")
    void shouldNotAllowSpecialSymbolsInCardNumberPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getCardNumberField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод латиницы в поле 'Месяц' на странице оплаты")
    void shouldNotAllowEnSymbolsInMonthPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getMonthField().setValue(DataHelper.getRandomEnSymbols());
        assertEquals("", paymentPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод кириллицы в поле 'Месяц' на странице оплаты")
    void shouldNotAllowRusSymbolsInMonthPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getMonthField().setValue(DataHelper.getRandomEnSymbols());
        assertEquals("", paymentPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод спецсимволов в поле 'Месяц' на странице оплаты")
    void shouldNotAllowSpecialSymbolsInMonthPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getMonthField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", paymentPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод латиницы в поле 'Месяц' на странице оформления кредита")
    void shouldNotAllowEnSymbolsInMonthPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getMonthField().setValue(DataHelper.getRandomEnSymbols());
        assertEquals("", creditPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод кириллицы в поле 'Месяц' на странице оформления кредита")
    void shouldNotAllowRusSymbolsInMonthPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getMonthField().setValue(DataHelper.getRandomRusSymbols());
        assertEquals("", creditPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод спецсимволов в поле 'Месяц' на странице оформления кредита")
    void shouldNotAllowSpecialSymbolsInMonthPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getMonthField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод латиницы в поле 'Год' на странице оплаты")
    void shouldNotAllowEnSymbolsInYearPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getYearField().setValue(DataHelper.getRandomEnSymbols());
        assertEquals("", paymentPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод кириллицы в поле 'Год' на странице оплаты")
    void shouldNotAllowRusSymbolsInYearPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getYearField().setValue(DataHelper.getRandomRusSymbols());
        assertEquals("", paymentPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод спецсимволов в поле 'Год' на странице оплаты")
    void shouldNotAllowSpecialSymbolsInYearPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getYearField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", paymentPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод латиницы в поле 'Год' на странице оформления кредита")
    void shouldNotAllowEnSymbolsInYearPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getYearField().setValue(DataHelper.getRandomEnSymbols());
        assertEquals("", creditPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод кириллицы в поле 'Год' на странице оформления кредита")
    void shouldNotAllowRusSymbolsInYearPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getYearField().setValue(DataHelper.getRandomRusSymbols());
        assertEquals("", creditPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод спецсимволов в поле 'Год' на странице оформления кредита")
    void shouldNotAllowSpecialSymbolsInYearPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getYearField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод цифр в поле 'Владелец' на странице оплаты")
    void shouldNotAllowNumbersInCardHolderPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getCardHolderField().setValue("0123456789");
        assertEquals("", paymentPage.getCardHolderField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод спецсимволов в поле 'Владелец' на странице оплаты")
    void shouldNotAllowSpecialSymbolsInCardHolderPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getCardHolderField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", paymentPage.getCardHolderField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод цифр в поле 'Владелец' на странице оформления кредита")
    void shouldNotAllowNumbersInCardHolderPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getCardHolderField().setValue("0123456789");
        assertEquals("", creditPage.getCardHolderField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод спецсимволов в поле 'Владелец' на странице оформления кредита")
    void shouldNotAllowSpecialSymbolsInCardHolderPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getCardHolderField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getCardHolderField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод кириллицы в поле 'CVC/CVV' на странице оплаты")
    void shouldNotAllowRusSymbolsInCVVPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getCvvNumberField().setValue(DataHelper.getRandomRusSymbols());
        assertEquals("", paymentPage.getCvvNumberField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод латиницы в поле 'CVC/CVV' на странице оплаты")
    void shouldNotAllowEnSymbolsInCVVPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getCvvNumberField().setValue(DataHelper.getRandomEnSymbols());
        assertEquals("", paymentPage.getCvvNumberField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод спецсимволов в поле 'CVC/CVV' на странице оплаты")
    void shouldNotAllowSpecialSymbolsInCVVPayByCardTest() {
        var paymentPage = offerTourPage.payByCard();
        paymentPage.getCvvNumberField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", paymentPage.getCvvNumberField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод кириллицы в поле 'CVC/CVV' на странице оформления кредита")
    void shouldNotAllowRusSymbolsInCVVPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getCvvNumberField().setValue(DataHelper.getRandomRusSymbols());
        assertEquals("", creditPage.getCvvNumberField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод латиницы в поле 'CVC/CVV' на странице оформления кредита")
    void shouldNotAllowEnSymbolsInCVVPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getCvvNumberField().setValue(DataHelper.getRandomEnSymbols());
        assertEquals("", creditPage.getCvvNumberField().getValue());
    }

    @Test
    @DisplayName("Не разрешать ввод спецсимволов в поле 'CVC/CVV' на странице оформления кредита")
    void shouldNotAllowSpecialSymbolsInCVVPayByCreditTest() {
        var creditPage = offerTourPage.payByCredit();
        creditPage.getCvvNumberField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getCvvNumberField().getValue());
    }
}
