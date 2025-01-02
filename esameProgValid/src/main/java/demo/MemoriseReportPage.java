package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MemoriseReportPage extends PageObject {
    @FindBy(tagName = "h1")
    private WebElement msgResult;

    @FindBy(linkText = "Go Back")
    private WebElement backReportButton;

    @FindBy(linkText = "Torna alla pagine iniziale")
    private WebElement backLoginButton;

    public MemoriseReportPage(WebDriver driver) {
        super(driver);
    }

    public CreateReportPage createReport() {
        backReportButton.click();
        return new CreateReportPage(driver);
    }

    // Metodo per tornare al Login
    public LoginPage backLogin() {
        backLoginButton.click();
        return new LoginPage(driver);
    }
}
