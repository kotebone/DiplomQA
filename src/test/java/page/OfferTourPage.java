package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class OfferTourPage {
    private SelenideElement payButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public PaymentPage payByCard() {
        payButton.click();
        return new PaymentPage();
    }

    public CreditPage payByCredit() {
        creditButton.click();
        return new CreditPage();
    }
}