package model;

import io.github.bonigarcia.wdm.WebDriverManager;
import model.pages.MainPage;
import model.pages.OrderPage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(Parameterized.class)
public class OrderPageTests {

    private WebDriver webDriver;
    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";
    private final String name, surname, address, metro, phone, date, term, color, comment;
    private final String expectedOrderSuccessText = "Заказ оформлен";

    public OrderPageTests(
        String name,
        String surname,
        String address,
        String metro,
        String phone,
        String date,
        String term,
        String color,
        String comment
    ) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.term = term;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] setDataForOrder() {
        return new Object[][] {
            {"Клава", "Птичкина", "Москва, ул. Дорожная, д. 12, кв. 34", "Сокол", "81234567890", "01.05.2023", "четверо суток", "чёрный жемчуг", "Коммент!"},
            {"Иван ", "Петров", "Москва, ул. Скобелевская, д. 26, кв. 1", "Улица Скобелевская", "89876543210", "21.05.2023", "трое суток", "серая безысходность", "Привезите в первой половине дня"},
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();     // здесь тест падает на подтверждении оформления заказа
        //webDriver = new SafariDriver();   // здесь тест проходит успешно
        webDriver.get(mainPageUrl);
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }

    @Test
    public void orderWithHeaderButtonWhenSuccess() {
        MainPage mainPage = new MainPage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);

        mainPage.clickOnCookieAcceptButton();
        mainPage.clickOrderButtonHeader();
        makeOrder(orderPage);

        MatcherAssert.assertThat(
            "Problem with creating a new order",
            orderPage.getNewOrderSuccessMessage(),
            containsString(expectedOrderSuccessText)
        );
    }

    @Test
    public void orderWithBodyButtonWhenSuccess() {
        MainPage mainPage = new MainPage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);

        mainPage.clickOnCookieAcceptButton();
        mainPage.clickOrderButtonBody();
        makeOrder(orderPage);

        MatcherAssert.assertThat(
            "Problem with creating a new order",
            orderPage.getNewOrderSuccessMessage(),
            containsString(expectedOrderSuccessText)
        );
    }

    private void makeOrder(OrderPage orderPage) {
        orderPage.waitForLoadForm();

        orderPage.setName(name);
        orderPage.setSurname(surname);
        orderPage.setAddress(address);
        orderPage.setMetro(metro);
        orderPage.setPhone(phone);

        orderPage.clickNextButton();

        orderPage.setDate(date);
        orderPage.setTerm(term);
        orderPage.setColor(color);
        orderPage.setComment(comment);

        orderPage.makeOrder();
    }
}
