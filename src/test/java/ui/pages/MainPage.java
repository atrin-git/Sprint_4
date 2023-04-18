package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private final WebDriver driver;
    private final By accordionHeaders = By.className("accordion__heading");
    private final By accordionItems = By.xpath(".//div[@class='accordion__panel']/p");
    private final By orderButtonHeader = By.xpath(".//div[starts-with(@class, 'Header_Nav')]//button[starts-with(@class, 'Button_Button')]");
    private final By orderButtonBody = By.xpath(".//div[starts-with(@class, 'Home_RoadMap')]//button[starts-with(@class, 'Button_Button')]");
    private final By cookieAcceptButton = By.id("rcc-confirm-button");
    private final By yandexLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoYandex')]");
    private final By scooterLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoScooter')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForLoadItem(int index) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(driver.findElements(accordionItems).get(index)));
    }

    public String getYandexLogoLink() {
        return driver.findElement(yandexLogoLink).getAttribute("href");
    }

    public String getScooterLogoLink() {
        return driver.findElement(scooterLogoLink).getAttribute("href");
    }

    public boolean isYandexLogoLinkOpenedInNewTab() {
        String blanc = "_blank";
        String value = driver.findElement(yandexLogoLink).getAttribute("target");
        return blanc.equals(value);
    }

    public void clickOnCookieAcceptButton() {
        driver.findElement(cookieAcceptButton).click();
    }

    public String getAccordionHeaderText(int index) {
        return driver.findElements(accordionHeaders).get(index).getText();
    }

    public String getAccordionItemText(int index) {
        return driver.findElements(accordionItems).get(index).getText();
    }
    public void clickAccordionHeader(int index) {
        driver.findElements(accordionHeaders).get(index).click();
    }

    public boolean isAccordionItemDisplayed(int index) {
        return driver.findElements(accordionItems).get(index).isDisplayed();
    }

    public void clickOrderButtonHeader() {
        driver.findElement(orderButtonHeader).click();
    }

    public void clickOrderButtonBody() {
        driver.findElement(orderButtonBody).click();
    }
}
