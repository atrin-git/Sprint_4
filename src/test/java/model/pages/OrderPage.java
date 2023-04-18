package model.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPage {
    private final WebDriver webDriver;
    private final By orderForm = By.xpath(".//div[starts-with(@class, 'Order_Form')]");
    private final By orderFormElements = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input");
    private final By metroList = By.className("select-search__select");
    private final By metroListItems = By.xpath(".//div[@class='select-search__select']//div[starts-with(@class,'Order_Text')]");
    private final By nextButton = By.xpath(".//div[starts-with(@class, 'Order_NextButton')]/button");
    private final By dateSelected = By.className("react-datepicker__day--selected");
    private final By dateInput = By.xpath(".//div[starts-with(@class, 'react-datepicker__input-container')]//input");
    private final By termDropdownRoot = By.className("Dropdown-root");
    private final By termDropdownOption = By.className("Dropdown-option");
    private final By colorLabels = By.xpath(".//div[starts-with(@class, 'Order_Checkboxes')]//label");
    private final By orderButton = By.xpath(".//div[starts-with(@class, 'Order_Buttons')]/button[not(contains(@class,'Button_Inverted'))]");
    private final By acceptOrderButton = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//button[not(contains(@class,'Button_Inverted'))]");
    private final By newOrderSuccessMessage = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//div[(starts-with(@class,'Order_ModalHeader'))]");
    private final Map<String,Integer> formElementsIds = new HashMap<>() {{
       put("name", 0);
       put("surname", 1);
       put("address", 2);
       put("metro", 3);
       put("phone", 4);
       put("comment", 3);
    }};

    public OrderPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void waitForLoadForm() {
        new WebDriverWait(webDriver, 3)
                .until(ExpectedConditions.visibilityOf(webDriver.findElement(orderForm)));
    }

    public void waitForElementLoad(By elementToLoad) {
        new WebDriverWait(webDriver, 3)
                .until(ExpectedConditions.visibilityOf(webDriver.findElement(elementToLoad)));

    }
    public void setName(String name) {
        webDriver.findElements(orderFormElements).get(formElementsIds.get("name")).sendKeys(name);
    }
    public void setSurname(String surname) {
        webDriver.findElements(orderFormElements).get(formElementsIds.get("surname")).sendKeys(surname);
    }
    public void setAddress(String address) {
        webDriver.findElements(orderFormElements).get(formElementsIds.get("address")).sendKeys(address);
    }
    public void setMetro(String metro) {
        webDriver.findElements(orderFormElements).get(formElementsIds.get("metro")).sendKeys(metro);
        waitForElementLoad(metroList);
        chooseElementFromDropdown(metroListItems, metro);
    }
    public void setPhone(String phone) {
        webDriver.findElements(orderFormElements).get(formElementsIds.get("phone")).sendKeys(phone);
    }

    public void chooseElementFromDropdown(By dropdownElements, String elementToChoose) {
        List<WebElement> elementsFiltered = webDriver.findElements(dropdownElements);
        for (WebElement element : elementsFiltered) {
            if (element.getText().equals(elementToChoose)) {
                element.click();
                break;
            }
        }
    }

    public void clickNextButton() {
        webDriver.findElement(nextButton).click();
    }

    public void setDate(String date) {
        webDriver.findElement(dateInput).sendKeys(date);
        waitForElementLoad(dateSelected);
        clickDateSelected();
    }

    private void clickDateSelected() {
        webDriver.findElement(dateSelected).click();
    }

    public void clickTermDropdown() {
        webDriver.findElement(termDropdownRoot).click();
    }

    public void setTerm(String termToChoose) {
        clickTermDropdown();
        chooseElementFromDropdown(termDropdownOption, termToChoose);
    }

    public void setColor(String colorToChoose) {
        chooseElementFromDropdown(colorLabels, colorToChoose);
    }

    public void setComment(String comment) {
        webDriver.findElements(orderFormElements).get(formElementsIds.get("comment")).sendKeys(comment);
    }

    private void clickOrderButton() {
        webDriver.findElement(orderButton).click();
    }

    private void clickAcceptOrderButton() {
        webDriver.findElement(acceptOrderButton).click();
    }

    public void makeOrder() {
        clickOrderButton();
        waitForElementLoad(acceptOrderButton);
        clickAcceptOrderButton();
    }

    public String getNewOrderSuccessMessage() {
        return webDriver.findElement(newOrderSuccessMessage).getText();
    }
}
