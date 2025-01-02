package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ScientificManagerPage extends PageObject{

    @FindBy(linkText = " List projects ")
    private WebElement  listProjectsButton;

    @FindBy(id = "workingHours")
    private WebElement freehours;


    @FindBy(linkText = "Create report")
    private WebElement CreateReports;

    @FindBy(linkText = "See reports")
    private WebElement seeReports;

    @FindBy(linkText = " Go to the login page ")
    private WebElement backLoginButton;

    public ScientificManagerPage(WebDriver driver) {
        super(driver);
    }
    // Metodo per vedere la lista progetti
    public ListProjectScientificManagerPage listProjects() {
        listProjectsButton.click();
        return new ListProjectScientificManagerPage(driver);
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
}
