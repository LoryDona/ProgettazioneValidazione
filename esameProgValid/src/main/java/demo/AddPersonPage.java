package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

// pageobject per la pagina input.html
public class AddPersonPage extends PageObject {

    //definisco gli elementi della pagina, (presi dal test di esempio fornito)
    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(name = "firstname")
    private WebElement firstNameField;

    @FindBy(name = "lastname")
    private WebElement lastNameField;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement submitButton;

    public AddPersonPage(WebDriver driver) {
        super(driver);
    }

    public String getHeadingText() {
        return getText(heading);
    }

    public void fillPersonDetails(String firstName, String lastName) {
        typeText(firstNameField, firstName);
        typeText(lastNameField, lastName);
    }

    public PeopleListPage submitForm() {
        click(submitButton);
        return new PeopleListPage(driver);//ritorna la pagina iniziale
    }
}
