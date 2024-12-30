package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResultPage extends PageObject{

    @FindBy(tagName = "p")
    private WebElement messaggio;

    public ResultPage(WebDriver driver) {super(driver);}

    public String getMessageText() {
        return getText(messaggio);
    }
}
