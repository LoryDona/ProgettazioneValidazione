package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ScientificManagerPage extends PageObject{

    @FindBy(linkText = "List projects")
    private WebElement  listProjectsButton;

    @FindBy(id = "workingHours")
    private WebElement freehours;

    //Tabella per Work Package
    @FindBy(xpath = "//table[1]//tr")
    private List<WebElement> tableRowsWp;
    @FindBy(xpath = "/html/body/table[1]/tbody/tr/td[1]")
    private WebElement firstRowFirstNameWp;

    //Tabella per Task
    @FindBy(xpath = "//table[2]//tr")
    private List<WebElement> tableRowsT;
    @FindBy(xpath = "/html/body/table[2]/tbody/tr/td[1]")
    private WebElement firstRowFirstNameT;
    @FindBy(xpath = "/html/body/table[1]/tbody/tr/td[6]/a")
    private WebElement firstRowCreateTask;

    //Tabella per Milestone
    @FindBy(xpath = "//table[3]//tr")
    private List<WebElement> tableRowsMil;
    @FindBy(xpath = "/html/body/table[3]/tbody/tr/td[1]")
    private WebElement firstRowFirstNameMil;
    @FindBy(xpath = "/html/body/table[3]/tbody/tr/td[3]")
    private WebElement firstRowDateMil;
    @FindBy(xpath = "/html/body/table[3]/tbody/tr/td[4]")
    private WebElement postMil;

    @FindBy(linkText = "Create report")
    private WebElement CreateReports;

    @FindBy(linkText = "See reports")
    private WebElement seeReports;

    @FindBy(linkText = "Go to the login page")
    private WebElement backLoginButton;

    public ScientificManagerPage(WebDriver driver) {
        super(driver);
    }
    // Metodo per vedere la lista progetti
    public ListProjectScientificManagerPage listProjects() {
        listProjectsButton.click();
        return new ListProjectScientificManagerPage(driver);
    }

    //Tabella Work Package
    public int getTableRowCountWp() { return tableRowsWp.size(); }
    public String getFirstRowFirstNameWp() {
        return getText(firstRowFirstNameWp);
    }
    //Tabella Task
    public int getTableRowCountT() { return tableRowsT.size(); }
    public String getFirstRowFirstNameT() {
        return getText(firstRowFirstNameT);
    }
    //Tabella Milestone
    public int getTableRowCountMil() { return tableRowsMil.size(); }
    public String getFirstRowFirstNameMil() {
        return getText(firstRowFirstNameMil);
    }
    public String getFirstRowDateMil() {
        return getText(firstRowDateMil);
    }


    // Per i task
    public CreateTaskPage createTaskForWorkPackage(String workPackageName) {
        WebElement taskLink = driver.findElement(By.xpath("//td[text()='" + workPackageName + "']/following-sibling::td/a[text()='create task for this workPackage']"));
        taskLink.click();
        return new CreateTaskPage(driver);
    }


    public String getTaskState(String taskName) {
        WebElement stateElement = driver.findElement(By.xpath("//td[text()='" + taskName + "']/following-sibling::td[3]"));
        return stateElement.getText();
    }

    public PostponeMilestonePage postponeMilestone(String milestoneName) {
        WebElement postponeLink = driver.findElement(By.xpath("//td[text()='" + milestoneName + "']/following-sibling::td/a[text()='Postpone']"));
        postponeLink.click();
        return new PostponeMilestonePage(driver);
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

    public PostponeMilestonePage postMilestone() {
        postMil.click();
        return new PostponeMilestonePage(driver);
    }
}
