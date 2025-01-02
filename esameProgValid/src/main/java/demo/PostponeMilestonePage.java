package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PostponeMilestonePage extends PageObject{

    @FindBy(tagName = "h1")
    private WebElement msgPostponeMilestone;

    @FindBy(id = "milestoneName")
    private WebElement milestoneName;

    @FindBy(id = "newEndDate")
    private WebElement newEndDate;

    @FindBy(xpath = "//button[text()='Update End Date']")
    private WebElement postponeMilestoneButton;

    public PostponeMilestonePage(WebDriver driver) {
        super(driver);
    }

    public String confirmHeader() {
        return msgPostponeMilestone.getText();
    }

    public void setNewEndDate(String date) {
        newEndDate.clear();
        newEndDate.sendKeys(date);
    }

    public String getMilestoneName() {
        return milestoneName.getAttribute("value"); // Recupera il valore del campo readonly
    }
    // Metodo per cliccare sul bottone "Update End Date"
    public ScientificManagerPage postponeMilestone() {
        postponeMilestoneButton.click();
        return new ScientificManagerPage(driver);
    }
}
