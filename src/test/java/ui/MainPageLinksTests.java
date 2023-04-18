package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import ui.pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

public class MainPageLinksTests {
    private WebDriver driver;
    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";
    private final String yandexUrl = "//yandex.ru";
    private final String scooterUrl = "//qa-scooter.praktikum-services.ru";

    public MainPageLinksTests() { }

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
    public void checkYandexLinkIsCorrect() {
        MainPage mainPage = new MainPage(driver);

        assertTrue(
            "Yandex Logo Link doesn't go to " + yandexUrl,
            mainPage.getYandexLogoLink().contains(yandexUrl)
        );

        assertTrue(
            "Yandex Logo Link doesn't open in new tab",
            mainPage.isYandexLogoLinkOpenedInNewTab()
        );
    }

    @Test
    public void checkScooterLinkIsCorrect() {
        MainPage mainPage = new MainPage(driver);

        assertTrue(
            "Scooter Logo Link doesn't go to " + scooterUrl,
            mainPage.getScooterLogoLink().contains(scooterUrl)
        );
    }
}
