package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class PageObject {
    protected WebDriver driver;
    private WebDriverWait wait;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // da selenium 4 in su
        PageFactory.initElements(driver, this); // individua l'elemento su pagina utilizzando il selettore annotato e crea un'istanza degli elementi Web
    }

    //definisco i metodi per interagire con le pagine

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click(); // condizione di click
    }

    protected void typeText(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).clear(); // l'elemento deve essere visibile
        element.sendKeys(text); // scrivo il testo
    }

    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText(); // recupero il testo
    }
}