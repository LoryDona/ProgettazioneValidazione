package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ScientificManagerPage extends PageObject{

    @FindBy(linkText = "See reports")
    private WebElement seeReports;

    public ScientificManagerPage(WebDriver driver) {
        super(driver);
    }

    public ReportListPage clickSeeReports() {
        click(seeReports);
        return new ReportListPage(driver);//ritorna la pagina iniziale
    }
}
