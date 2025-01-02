package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReportListPage extends PageObject{

    @FindBy(xpath = "//button[text()='send']")
    private WebElement sendButton;
    @FindBy(name = "email")
    private WebElement emailRecupero;

    @FindBy(linkText = " Go to the login page ")
    private WebElement backLoginButton;

    public ReportListPage(WebDriver driver) {
        super(driver);
    }

    public void writEmail(String e) {
        typeText(emailRecupero, e);
    }

    public ResultPage clickSend() {
        click(sendButton);
        return new ResultPage(driver);// navigo alla pagina dove vedo la lista delle persone
    }

    // Metodo per tornare al Login
    public LoginPage backLogin() {
        backLoginButton.click();
        return new LoginPage(driver);
    }
}
