package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateReportPage extends PageObject{

    @FindBy(tagName = "h1")
    private WebElement msgInsertReport;

    @FindBy(linkText = "Torna al login")
    private WebElement backLoginButton;

    @FindBy(name = "title")
    private WebElement titleInput;

    @FindBy(name = "results")
    private WebElement resultsTextArea;

    @FindBy(name = "hours")
    private WebElement hoursInput;

    @FindBy(name = "activities")
    private WebElement activitiesTextArea;

    @FindBy(name = "progetto")
    private WebElement projectSelect;

    @FindBy(name = "firma")
    private WebElement signatureInput;

    @FindBy(name = "submitAction")
    private WebElement bozzaButton;

    public CreateReportPage(WebDriver driver) {
        super(driver);
    }

    // Metodo per impostare il titolo del report
    public void setTitle(String title) {
        titleInput.clear();
        titleInput.sendKeys(title);
    }

    // Metodo per impostare i risultati ottenuti
    public void setResults(String results) {
        resultsTextArea.clear();
        resultsTextArea.sendKeys(results);
    }

    // Metodo per impostare le ore lavorative
    public void setHours(int hours) {
        hoursInput.clear();
        hoursInput.sendKeys(String.valueOf(hours));
    }

    // Metodo per impostare le attività svolte
    public void setActivities(String activities) {
        activitiesTextArea.clear();
        activitiesTextArea.sendKeys(activities);
    }

    // Metodo per selezionare un progetto
    public void selectProject(String project) {
        projectSelect.sendKeys(project);
    }

    // Metodo per impostare la firma
    public void setSignature(String signature) {
        signatureInput.clear();
        signatureInput.sendKeys(signature);
    }

    public MemoriseReportPage bozzaReport() {
        bozzaButton.click();
        return new MemoriseReportPage(driver);
    }


    // Metodo per tornare al Login
    public LoginPage backLogin() {
        backLoginButton.click();
        return new LoginPage(driver);
    }
}
