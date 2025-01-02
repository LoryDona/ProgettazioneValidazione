package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateWorkPackagePage extends PageObject{
    @FindBy(tagName = "h1")
    private WebElement msgInsertWorkPackage;

    @FindBy(id = "nameWorkPackage")
    private WebElement nameWorkPackage;

    @FindBy(id = "description")
    private WebElement descriptionWorkPackage;

    @FindBy(id = "startDate")
    private WebElement startDateWorkPackage;

    @FindBy(id = "endDate")
    private WebElement endDateWorkPackage;

    @FindBy(name = "nameProject")
    private WebElement nameProject; //campo nascosto

    @FindBy(xpath = "//button[text()='Create Work Package']")
    private WebElement createWorkPackageButton;

    public CreateWorkPackagePage(WebDriver driver) {
        super(driver);
    }

    // Metodo per confermare il titolo della pagina
    public String confirmHeader() {
        return msgInsertWorkPackage.getText();
    }

    // Metodo per inserire dati nel form
    public void fillWorkPackageForm(String name, String desc, String workPackage, String startDate, String endDate) {
        nameWorkPackage.sendKeys(name);
        descriptionWorkPackage.sendKeys(desc);
        startDateWorkPackage.sendKeys(startDate);
        endDateWorkPackage.sendKeys(endDate);
    }
    // Metodo per cliccare sul bottone "Create Work Package"
    public ScientificManagerPage createWorkPackage() {
        createWorkPackageButton.click();
        return new ScientificManagerPage(driver);
    }
    // Metodo per ottenere il valore del campo nascosto (se necessario)
    public String getProjectName() {
        return nameProject.getAttribute("value");
    }
}
