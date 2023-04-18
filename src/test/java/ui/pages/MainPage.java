package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private final WebDriver webDriver;
    private final By accordionHeaders = By.className("accordion__heading");
    private final By accordionItems = By.xpath(".//div[@class='accordion__panel']/p");
    private final By orderButtonHeader = By.xpath(".//div[starts-with(@class, 'Header_Nav')]//button[starts-with(@class, 'Button_Button')]");
    private final By orderButtonBody = By.xpath(".//div[starts-with(@class, 'Home_RoadMap')]//button[starts-with(@class, 'Button_Button')]");
    private final By cookieAcceptButton = By.id("rcc-confirm-button");
    private final By yandexLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoYandex')]");
    private final By scooterLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoScooter')]");

    public MainPage(WebDriver driver) {
        this.webDriver = driver;
    }

    public void waitForLoadItem(int index) {
        new WebDriverWait(this.webDriver, 3)
                .until(ExpectedConditions.visibilityOf(this.webDriver.findElements(this.accordionItems).get(index)));
    }

    public void clickOnCookieAcceptButton() {
        this.webDriver.findElement(this.cookieAcceptButton).click();
    }

    public String getAccordionHeaderText(int index) {
        return this.webDriver.findElements(this.accordionHeaders).get(index).getText();
    }

    public String getAccordionItemText(int index) {
        return this.webDriver.findElements(this.accordionItems).get(index).getText();
    }
    public void clickAccordionHeader(int index) {
        this.webDriver.findElements(this.accordionHeaders).get(index).click();
    }

    public boolean isAccordionItemDisplayed(int index) {
        return this.webDriver.findElements(this.accordionItems).get(index).isDisplayed();
    }

    public void clickOrderButtonHeader() {
        this.webDriver.findElement(this.orderButtonHeader).click();
    }

    public void clickOrderButtonBody() {
        this.webDriver.findElement(this.orderButtonBody).click();
    }

    public String getYandexLogoLink() {
        return this.webDriver.findElement(this.yandexLogoLink).getAttribute("href");
    }

    public String getScooterLogoLink() {
        return this.webDriver.findElement(this.scooterLogoLink).getAttribute("href");
    }

    public boolean isYandexLogoLinkOpenedInNewTab() {
        String blanc = "_blank";
        String value = this.webDriver.findElement(this.yandexLogoLink).getAttribute("target");
        return blanc.equals(value);
    }
}
