package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

public class CreateTaskPage extends PageObject {

    @FindBy(tagName = "h1")
    private WebElement msgInsertTask;

    @FindBy(id = "nameTask")
    private WebElement nameTask;

    @FindBy(id = "Researcher")
    private WebElement researcherSelect;

    @FindBy(id = "state")
    private WebElement statusTask;

    @FindBy(id = "startDate")
    private WebElement startDateTask;

    @FindBy(id = "endDate")
    private WebElement endDateTask;

    @FindBy(xpath = "//button[text()='Create Task']")
    private WebElement createTaskButton;

    public CreateTaskPage(WebDriver driver) {
        super(driver);
    }

    // Metodo per confermare il titolo della pagina
    public String confirmHeader() {
        return msgInsertTask.getText();
    }

    public void setTaskName(String taskName) {
        nameTask.clear();
        nameTask.sendKeys(taskName);
    }

    // Metodo per selezionare ricercatori
    public void selectResearchers(String[] researcherIds) {
        for (String id : researcherIds) {
            WebElement researcherOption = driver.findElement(By.xpath("//option[@value='" + id + "']"));
            researcherOption.click();
        }
    }

    public void setStatus(String status) {
        statusTask.sendKeys(status);
    }

    public void setStartDate(String startDate) {
        startDateTask.clear();
        startDateTask.sendKeys(startDate);
    }

    public void setEndDate(String endDate) {
        endDateTask.clear();
        endDateTask.sendKeys(endDate);
    }

    // Metodo per creare il task
    public ScientificManagerPage createTask() {
        createTaskButton.click();
        return new ScientificManagerPage(driver);
    }
}
