package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ResearcherPage extends PageObject{
    @FindBy(id = "workingHours")
    private WebElement freehours;

    @FindBy(tagName = "table")
    private WebElement taskTable; // La tabella che contiene i task

    @FindBy(linkText = "Create report")
    private WebElement CreateReports;

    @FindBy(linkText = "See reports")
    private WebElement seeReports;

    @FindBy(linkText = "Go to the login page")
    private WebElement backLoginButton;

    public ResearcherPage(WebDriver driver) {
        super(driver);
    }

    // Metodo per ottenere tutte le righe della tabella
    public List<WebElement> getTaskRows() {
        return taskTable.findElements(By.tagName("tr")); // Restituisce tutte le righe della tabella
    }
    // Metodo per ottenere il nome del task dalla prima colonna di una riga specifica
    public String getTaskNameFromRow(int rowIndex) {
        WebElement row = getTaskRows().get(rowIndex);
        return row.findElements(By.tagName("td")).get(0).getText(); // Ottiene il nome del task dalla prima colonna

    }
    // Verifica che la tabella contenga almeno un task
    public boolean isTaskTablePopulated() {
        return getTaskRows().size() > 1; // Verifica che ci siano righe oltre l'intestazione
    }


    //IMPOSTARE FREE HOURS
    public void setFreeHours(int hours) {
        freehours.clear();
        freehours.sendKeys(String.valueOf(hours));
        freehours.submit();
    }
    //Crea report
    public CreateReportPage clickCreateReports() {
        CreateReports.click();
        return new CreateReportPage(driver);
    }

    public ReportListPage clickSeeReports() {
        click(seeReports);
        return new ReportListPage(driver);//ritorna la pagina iniziale
    }
    // Metodo per tornare al Login
    public LoginPage backLogin() {
        backLoginButton.click();
        return new LoginPage(driver);
    }
}
