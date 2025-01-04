package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PostponeMilestonePage extends PageObject {

    // Titolo della pagina
    @FindBy(tagName = "h1")
    private WebElement header;

    // Campo readonly per il nome della milestone
    @FindBy(id = "milestoneName")
    private WebElement milestoneNameField;

    // Campo per inserire la nuova data di fine
    @FindBy(id = "newEndDate")
    private WebElement newEndDateField;

    // Bottone per aggiornare la data di fine
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement updateEndDateButton;

    public PostponeMilestonePage(WebDriver driver) {
        super(driver);
    }

    public void fillPostForm(String endDate) {
        setEndDate(endDate);
    }
    // Metodo per confermare che il titolo della pagina è corretto
    public String getHeader() {
        return header.getText();
    }

    // Metodo per ottenere il nome della milestone (readonly)
    public String getMilestoneName() {
        return milestoneNameField.getAttribute("value");
    }

    // Metodo per impostare una nuova data di fine
    public void setNewEndDate(String newEndDate) {
        newEndDateField.clear();
        newEndDateField.sendKeys(newEndDate);
    }

    // Metodo per impostare la data di fine
    public void setEndDate(String newEndDate) {
        WebElement endDateInput = driver.findElement(By.id("newEndDate"));
        endDateInput.clear();
        endDateInput.sendKeys(newEndDate);
    }

    // Metodo per cliccare il bottone "Update End Date"
    public ScientificManagerPage updateEndDate() {
        updateEndDateButton.click();
        return new ScientificManagerPage(driver);
    }


}

