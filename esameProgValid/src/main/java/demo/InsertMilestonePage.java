/*
package demo;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InsertMilestonePage extends PageObject {

    @FindBy(tagName = "h1")
    private WebElement msgInsertMilestone;

    @FindBy(id = "nameMilestone")
    private WebElement nameMilestone;

    @FindBy(id = "descriptionMilestone")
    private WebElement descriptionMilestone;

    @FindBy(id = "state")
    private WebElement statusMilestone;

    @FindBy(id = "workPackages")
    private WebElement workPackageMilestone;

    @FindBy(id = "startDate")
    private WebElement startDateMilestone;

    @FindBy(id = "endDate")
    private WebElement endDateMilestone;

    @FindBy(xpath = "//button[text()='Create Milestone']")
    private WebElement createMilestoneButton;

    @FindBy(linkText = "Go Back")
    private WebElement backMilestoneButton;

    public InsertMilestonePage(WebDriver driver) {
        super(driver);
    }

    // Metodo per confermare il titolo della pagina
    public String confirmHeader() {
        return msgInsertMilestone.getText();
    }

    // Metodo per inserire dati nel form
    public void fillMilestoneForm(String name, String description, String status, String workPackage, String startDate, String endDate) {
        nameMilestone.sendKeys(name);
        descriptionMilestone.sendKeys(description);
        statusMilestone.sendKeys(status);
        workPackageMilestone.sendKeys(workPackage);
        startDateMilestone.sendKeys(startDate);
        endDateMilestone.sendKeys(endDate);
    }

    // Metodo per cliccare sul bottone "Create Milestone"
    public ScientificManagerPage createMilestone() {
        createMilestoneButton.click();
        return new ScientificManagerPage(driver);
    }

    // Metodo per tornare indietro
    public ListProjectScientificManagerPage backMilestone() {
        backMilestoneButton.click();
        return new ListProjectScientificManagerPage(driver);

    }
}
 */

