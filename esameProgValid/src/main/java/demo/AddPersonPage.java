package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

// pageobject per la pagina input.html
public class AddPersonPage extends PageObject {

    //definisco gli elementi della pagina, (presi dal test di esempio fornito)
    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(name = "mail")
    private WebElement mail;

    @FindBy(name = "role")
    private WebElement role;

    @FindBy(xpath = "//button[text()='Crea']")
    private WebElement submitButton;

    public AddPersonPage(WebDriver driver) {
        super(driver);
    }

    public String getHeadingText() {
        return getText(heading);
    }

    public void fillPersonDetails(String user, String pass, String ma, String rol) {
        typeText(username, user);
        typeText(password, pass);
        typeText(mail, ma);
        Select dropdown = new Select(role);
        dropdown.selectByVisibleText(rol);
    }

    public LoginPage submitForm() {
        click(submitButton);
        return new LoginPage(driver);//ritorna la pagina iniziale
    }
}
