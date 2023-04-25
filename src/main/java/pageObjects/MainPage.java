package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Класс, описывающий главную страницу, её компоненты и методы взаимодействия
 */
public class MainPage {
    /** Веб-драйвер */
    private final WebDriver webDriver;

    /** Заголовок для раскрывающегося блока */
    private final By accordionHeaders = By.className("accordion__heading");

    /** Абзац в раскрывающемся блоке */
    private final By accordionItems = By.xpath(".//div[@class='accordion__panel']/p");

    /** Кнопка оформления заказа в шапке сайта */
    private final By orderButtonHeader = By.xpath(".//div[starts-with(@class, 'Header_Nav')]//button[starts-with(@class, 'Button_Button')]");

    /** Кнопка оформления заказа в теле сайта */
    private final By orderButtonBody = By.xpath(".//div[starts-with(@class, 'Home_RoadMap')]//button[starts-with(@class, 'Button_Button')]");

    /** Кнопка "Принять куки" */
    private final By cookieAcceptButton = By.id("rcc-confirm-button");

    /** Ссылка-логотип Яндекс в шапке */
    private final By yandexLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoYandex')]");

    /** Ссылка-логотип Самокат в шапке */
    private final By scooterLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoScooter')]");

    /**
     * Конструктор класса MainPage. Создает новый экземпляр образа главной страницы
     * @param driver веб-драйвер
     */
    public MainPage(WebDriver driver) {
        this.webDriver = driver;
    }

    /**
     * Метод для ожидания загрузки элемента аккордеона
     * @param index порядковый номер элемента аккордеона
     */
    public void waitForLoadItem(int index) {
        new WebDriverWait(this.webDriver, 3)
                .until(ExpectedConditions.visibilityOf(this.webDriver.findElements(this.accordionItems).get(index)));
    }

    /**
     * Метод для нажатия на кнопку "Принять куки"
     */
    public void clickOnCookieAcceptButton() {
        this.webDriver.findElement(this.cookieAcceptButton).click();
    }

    /**
     * Метод для получения текста на заголовке блока в аккордеоне
     * @param index порядковый номер элемента аккордеона
     * @return текст из заголовка
     */
    public String getAccordionHeaderText(int index) {
        return this.webDriver.findElements(this.accordionHeaders).get(index).getText();
    }

    /**
     * Методя для получения текста из раскрывающегося блока в аккордеоне
     * @param index порядковый номер элемента аккордеона
     * @return текст из раскрывающегося блока
     */
    public String getAccordionItemText(int index) {
        return this.webDriver.findElements(this.accordionItems).get(index).getText();
    }

    /**
     * Метод для нажатия на заголовок блока в аккордеоне
     * @param index порядковый номер элемента аккордеона
     */
    public void clickAccordionHeader(int index) {
        this.webDriver.findElements(this.accordionHeaders).get(index).click();
    }

    /**
     * Метод для проверки раскрытия блока аккордеона
     * @param index порядковый номер элемента аккордеона
     * @return флаг раскрытия
     */
    public boolean isAccordionItemDisplayed(int index) {
        return this.webDriver.findElements(this.accordionItems).get(index).isDisplayed();
    }

    /**
     * Метод для нажатия на кнопку оформления заказа в шапке
     */
    public void clickOrderButtonHeader() {
        this.webDriver.findElement(this.orderButtonHeader).click();
    }

    /**
     * Метод для нажатия на кнопку оформления заказа в теле сайта
     */
    public void clickOrderButtonBody() {
        this.webDriver.findElement(this.orderButtonBody).click();
    }

    /**
     * Метод для получения ссылки из логотипа "Яндекс"
     * @return ссылка url
     */
    public String getYandexLogoLink() {
        return this.webDriver.findElement(this.yandexLogoLink).getAttribute("href");
    }

    /**
     * Метод для получения ссылки из логотипа "Самокат"
     * @return ссылка url
     */
    public String getScooterLogoLink() {
        return this.webDriver.findElement(this.scooterLogoLink).getAttribute("href");
    }

    /**
     * Метод для проверки открытия ссылки "Яндекс" в новой вкладке
     * @return флаг открытия
     */
    public boolean isYandexLogoLinkOpenedInNewTab() {
        String blanc = "_blank";
        String value = this.webDriver.findElement(this.yandexLogoLink).getAttribute("target");
        return blanc.equals(value);
    }
}
