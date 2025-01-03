package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ListProjectScientificManagerPage extends PageObject{

    @FindBy(xpath = "/html/body/table/tbody/tr/td[4]")
    private WebElement createWorkPackagePage;

    @FindBy(xpath = "/html/body/table/tbody/tr/td[5]")
    private WebElement createMilestonePage;

    @FindBy(linkText = "Go Back")
    private WebElement backScientifickManagerButton;

    @FindBy(linkText = "Go to the login page")
    private WebElement backLoginButton;

    public ListProjectScientificManagerPage(WebDriver driver) {
        super(driver);
    }

    //Metodo per creare WorkPackage
    public CreateWorkPackagePage createWorkPackage() {
        createWorkPackagePage.click();
        return new CreateWorkPackagePage(driver);
    }

    //Metodo per creare Milestone
    public CreateMilestonePage createMilestonePage() {
        createMilestonePage.click();
        return new CreateMilestonePage(driver);
    }

    // Metodo per tornare indietro
    public ScientificManagerPage backScientifickManager() {
        backScientifickManagerButton.click();
        return new ScientificManagerPage(driver);
    }

    // Metodo per tornare al Login
    public LoginPage backLogin() {
        backLoginButton.click();
        return new LoginPage(driver);
    }
}
