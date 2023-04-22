package ui;

import pageObjects.MainPage;

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

/**
 * Тест для проверки работы и содержимого раскрывающегося блока "Вопросы о важном"
 */
@RunWith(Parameterized.class)
public class MainPageAccordionTests {
    /** Веб-драйвер */
    private WebDriver webDriver;

    /** URL тестируемой страницы */
    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";

    /** Порядковый номер элемента аккордеона */
    private final int numberOfElement;

    /** Ожидаемый текст в заголовке элемента аккордеона */
    private final String expectedHeaderText;

    /** Ожидаемый текст в раскрывающемся блоке элемента аккордеона */
    private final String expectedItemText;

    /**
     * Конструктор класса MainPageAccordionTests
     * @param numberOfAccordionItem Порядковый номер элемента аккордеона
     * @param expectedHeaderText Ожидаемый текст в заголовке элемента аккордеона
     * @param expectedItemText Ожидаемый текст в раскрывающемся блоке элемента аккордеона
     */
    public MainPageAccordionTests(int numberOfAccordionItem, String expectedHeaderText, String expectedItemText) {
        this.numberOfElement = numberOfAccordionItem;
        this.expectedHeaderText = expectedHeaderText;
        this.expectedItemText = expectedItemText;
    }

    /**
     * Параметризация теста
     * @return массив параметров
     */
    @Parameterized.Parameters(name = "Текст в блоке\"Вопросы о важном\". Проверяемый элемент: {1}")
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
        this.webDriver = new ChromeDriver();
        this.webDriver.get(this.mainPageUrl);
    }

    @After
    public void tearDown() {
        this.webDriver.quit();
    }

    /**
     * Тест для проверки работы аккордеона и для проверки текста в заголовках и в раскрывающихся блоках
     */
    @Test
    public void checkAccordionIsCorrect() {
        MainPage mainPage = new MainPage(this.webDriver);

        mainPage.clickOnCookieAcceptButton();

        mainPage.clickAccordionHeader(this.numberOfElement);
        mainPage.waitForLoadItem(this.numberOfElement);

        if (mainPage.isAccordionItemDisplayed(this.numberOfElement)) {
            MatcherAssert.assertThat("Problems with text in accordion header #" + this.numberOfElement,
                    this.expectedHeaderText,
                    equalTo(mainPage.getAccordionHeaderText(this.numberOfElement))
            );
            MatcherAssert.assertThat("Problems with text in accordion item #" + this.numberOfElement,
                    this.expectedItemText,
                    equalTo(mainPage.getAccordionItemText(this.numberOfElement))
            );
        }
        else {
            fail("Accordion header item #" + this.numberOfElement + "didn't load");
        }
    }
}
