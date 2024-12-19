package demo;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

// pageobject della pagina iniziale list.html
public class PeopleListPage extends PageObject {

    //definisco gli elementi della pagina, (presi dal test di esempio fornito)
    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(linkText = "Add new person")
    private WebElement addNewPersonLink;

    @FindBy(xpath = "//table//tr")
    private List<WebElement> tableRows;

    @FindBy(xpath = "//table//tbody//td[2]")
    private WebElement firstRowFirstName;

    public PeopleListPage(WebDriver driver) {
        super(driver);
    }

    public String getHeadingText() {
        return getText(heading);
    }

    public AddPersonPage clickAddNewPerson() {
        click(addNewPersonLink);
        return new AddPersonPage(driver);// navigo alla pagina dove aggiungo persone
    }

    public int getTableRowCount() {
        return tableRows.size();
    }

    public String getFirstRowFirstName() {
        return getText(firstRowFirstName);
    }
}
