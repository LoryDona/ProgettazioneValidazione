package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class CreateTaskPage extends PageObject {

    @FindBy(tagName = "h1")
    private WebElement msgCreateUser;

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

    @FindBy(xpath = "//button[text()='Crea Task']")
    private WebElement createTaskButton;

    public CreateTaskPage(WebDriver driver) {
        super(driver);
    }

    // Metodo per confermare il titolo della pagina
    public String confirmHeader() {
        return msgCreateUser.getText();
    }

    public void fillTaskForm(String taskName, String researcherIds, String status, String startDate, String endDate) {
        // Imposta il nome del task
        setTaskName(taskName);

        // Seleziona i ricercatori
        Select dropdown = new Select(researcherSelect);
        dropdown.selectByVisibleText(researcherIds);

        // Imposta lo stato
        setStatus(status);

        // Imposta la data di inizio
        setStartDate(startDate);

        // Imposta la data di fine
        setEndDate(endDate);
    }

    // Metodo per inserire dati nel form
    public void fillMilestoneForm(String taskName, String researcherIds,  String status, String startDate, String endDate) {
        nameTask.sendKeys(taskName);
        researcherSelect.sendKeys(researcherIds);
        statusTask.sendKeys(status);
        startDateTask.sendKeys(startDate);
        endDateTask.sendKeys(endDate);
    }
    // Metodo per impostare il nome del task
    public void setTaskName(String taskName) {
        WebElement nameTask = driver.findElement(By.id("nameTask"));
        nameTask.clear();
        nameTask.sendKeys(taskName);
    }

    // Metodo per selezionare i ricercatori
    public void selectResearchers(String[] researcherIds) {
        for (String id : researcherIds) {
            WebElement researcherOption = driver.findElement(By.xpath("//select[@id='Researcher']/option[@value='" + id + "']"));
            researcherOption.click();
        }
    }

    // Metodo per impostare lo stato del progetto
    public void setStatus(String status) {
        WebElement stateDropdown = driver.findElement(By.id("state"));
        stateDropdown.sendKeys(status);
    }

    // Metodo per impostare la data di inizio
    public void setStartDate(String startDate) {
        WebElement startDateInput = driver.findElement(By.id("startDate"));
        startDateInput.clear();
        startDateInput.sendKeys(startDate);
    }

    // Metodo per impostare la data di fine
    public void setEndDate(String endDate) {
        WebElement endDateInput = driver.findElement(By.id("endDate"));
        endDateInput.clear();
        endDateInput.sendKeys(endDate);
    }


    // Metodo per creare il task
    public ScientificManagerPage createTask() {
        createTaskButton.click();
        return new ScientificManagerPage(driver);
    }
}
