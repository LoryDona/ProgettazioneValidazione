package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProjectListPage extends PageObject{
    @FindBy(xpath = "//table//tr")
    private List<WebElement> tableRows;

    @FindBy(xpath = "//table//tbody//td[1]")
    private WebElement firstRowFirstName;

    public ProjectListPage(WebDriver driver) {super(driver);}

    public int getTableRowCount() {
        return tableRows.size();
    }

    public String getFirstRowFirstName() {
        return getText(firstRowFirstName);
    }

}
