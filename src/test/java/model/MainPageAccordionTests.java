package model;

import model.pages.MainPage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.hamcrest.MatcherAssert;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class MainPageAccordionTests {
    private WebDriver driver;
    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";
    private final int numberOfElement;
    private final String expectedHeaderText;
    private final String expectedItemText;

    public MainPageAccordionTests(int numberOfAccordionItem, String expectedHeaderText, String expectedItemText) {
        this.numberOfElement = numberOfAccordionItem;
        this.expectedHeaderText = expectedHeaderText;
        this.expectedItemText = expectedItemText;
    }

    @Parameterized.Parameters
    public static Object[][] setTestData() {
        return new Object[][] {
            {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
            {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
            {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
            {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
            {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
            {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
            {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
            {7, "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области." }, // bug here з -> в
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(mainPageUrl);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void checkAccordionIsOpened() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickOnCookieAcceptButton();

        mainPage.clickAccordionHeader(numberOfElement);
        mainPage.waitForLoadItem(numberOfElement);

        if (mainPage.isAccordionItemDisplayed(numberOfElement)) {
            MatcherAssert.assertThat("Problems with text in accordion header #" + numberOfElement,
                    expectedHeaderText,
                    equalTo(mainPage.getAccordionHeaderText(numberOfElement))
            );
            MatcherAssert.assertThat("Problems with text in accordion item #" + numberOfElement,
                    expectedItemText,
                    equalTo(mainPage.getAccordionItemText(numberOfElement))
            );
        }
        else {
            fail("Accordion header item #" + numberOfElement + "didn't load");
        }
    }
}
