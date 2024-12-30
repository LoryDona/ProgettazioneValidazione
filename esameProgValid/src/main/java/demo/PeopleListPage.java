package demo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PeopleListPage extends PageObject{

    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(xpath = "//table//tr")
    private List<WebElement> tableRows;

    @FindBy(xpath = "//table//tbody//td[2]")
    private WebElement firstRowFirstName;

    public PeopleListPage(WebDriver driver) {super(driver);}

    public String getHeadingText() {
        return getText(heading);
    }

    public int getTableRowCount() {
        return tableRows.size();
    }

    public String getFirstRowFirstName() {
        return getText(firstRowFirstName);
    }

}
