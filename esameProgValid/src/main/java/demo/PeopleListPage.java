package demo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PeopleListPage extends PageObject{

    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(linkText = " Go to the login page ")
    private WebElement backLoginButton;

    @FindBy(xpath = "//table//tr")
    private List<WebElement> tableRows;

    @FindBy(xpath = "//table//tbody//td[2]")
    private WebElement firstRowFirstName;

    @FindBy(xpath = "//table//tbody//td[5]")
    private WebElement show;

    @FindBy(xpath = "/html/body/table/tbody/tr/td[6]")
    private static WebElement del;


    public PeopleListPage(WebDriver driver) {super(driver);}

    public String getHeadingText() {
        return getText(heading);
    }

    public int getTableRowCount() {
        return tableRows.size();
    }

    public static PeopleListPage delete(){
        del.click();
        return null;
    }
    public String getFirstRowFirstName() {
        return getText(firstRowFirstName);
    }
    // Metodo per tornare al Login
    public LoginPage backLogin() {
        backLoginButton.click();
        return new LoginPage(driver);
    }


}
