package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdministratorPage extends PageObject{

    @FindBy(linkText = "Create project")
    private WebElement createProject;

    @FindBy(linkText = "List projects")
    private WebElement listprojects;


    public AdministratorPage(WebDriver driver) {super(driver);}

    public ProjectPage clickCreateProject() {
        click(createProject);
        return new ProjectPage(driver);// navigo alla pagina per creare il progetto
    }

    public ProjectListPage clickListProjects() {
        click(listprojects);
        return new ProjectListPage(driver);// navigo alla pagina per creare il progetto
    }
}
