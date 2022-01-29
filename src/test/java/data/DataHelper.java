package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class DataHelper {
    private DataHelper() {
    }

    private static final String approvedCard = "4444 4444 4444 4441";
    private static final String declinedCard = "4444 4444 4444 4442";
    private static final Faker fakerEn = new Faker(new Locale("en-US"));

    @Value
    public static class PaymentInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String cardHolder;
        private String cvv;
    }

    public static PaymentInfo approvedPayment(int plusMonth) {
        return new PaymentInfo(approvedCard, expiredMonth(plusMonth), expiredYear(plusMonth),
                getRandomName(), getRandomCVV());
    }

    public static PaymentInfo declinedPayment(int plusMonth) {
        return new PaymentInfo(declinedCard, expiredMonth(plusMonth), expiredYear(plusMonth),
                getRandomName(), getRandomCVV());
    }

    private static LocalDate expiredDate(int plusMonth) {
        var expiredDate = LocalDate.now().plusMonths(plusMonth);
        return expiredDate;
    }

    private static String expiredMonth(int plusMonths) {
        var month = expiredDate(plusMonths).getMonthValue();
        if (month < 10) {
            String monthFormat = "0" + Integer.toString(month);
            return monthFormat;
        }
        return Integer.toString(month);
    }

    public static int randomPlusMonth() {
        Random random = new Random();
        int plusMonth = random.nextInt(58) + 1;
        return plusMonth;
    }

    private static String expiredYear(int plusMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        var year = expiredDate(plusMonth).format(formatter);
        return year;
    }

    private static String getRandomName(){
        String name = fakerEn.name().fullName();
        return name;
    }

    private static String getRandomCVV() {
        String cvv = fakerEn.numerify("###");
        return cvv;
    }

    public static String getRandomEnSymbols() {
        String rusSymbols = fakerEn.bothify("??????????????????");
        return rusSymbols;
    }

    public static String getRandomRusSymbols() {
        Faker fakerRu = new Faker(new Locale("ru"));
        String rusSymbols = fakerRu.name().firstName();
        return rusSymbols;
    }
}