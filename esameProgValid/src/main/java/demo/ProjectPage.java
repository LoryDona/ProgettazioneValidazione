package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ProjectPage extends PageObject{
    @FindBy(name = "nameProject")
    private WebElement nameProject;

    @FindBy(id = "scientificManager")
    private WebElement scientificManagerSelect;

    @FindBy(id = "state")
    private WebElement stateSelect;

    @FindBy(id = "startDate")
    private WebElement startDateInput;

    @FindBy(id = "endDate")
    private WebElement endDateInput;

    @FindBy(id = "budget")
    private WebElement budgetInput;

    @FindBy(id = "workingHours")
    private WebElement workingHoursInput;

    @FindBy(xpath = "//button[text()='Crea Progetto']")
    private WebElement submitButton;

    public ProjectPage(WebDriver driver) {super(driver);}

    public void fillProjectDetails(String project, String manager, String state, String inizio,String fine,String soldi,String ore) {
        typeText(nameProject, project);
        Select dropdown = new Select(scientificManagerSelect);
        dropdown.selectByVisibleText(manager);
        Select dropdown2 = new Select(stateSelect);
        dropdown2.selectByVisibleText(state);
        startDateInput.sendKeys(inizio); // formato richiesto: YYYY-MM-DD
        endDateInput.sendKeys(fine); // formato richiesto: YYYY-MM-DD
        typeText(budgetInput, soldi);
        typeText(workingHoursInput, ore);
    }

    public AdministratorPage clickCreaprogetto() {
        click(submitButton);
        return new AdministratorPage(driver);// navigo alla pagina per creare il progetto
    }
}
