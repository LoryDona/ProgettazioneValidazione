package demo;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

// pageobject della pagina iniziale list.html
public class LoginPage extends PageObject {

    //definisco gli elementi della pagina, (presi dal test di esempio fornito)
    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement pass;

    @FindBy(xpath = "//button[text()='Accedi']")
    private WebElement accedi;

    @FindBy(linkText = "Create user")
    private WebElement addNewPersonLink;

    @FindBy(linkText = "List users")
    private WebElement listaLink;

    @FindBy(linkText = "Password dimenticata?")
    private WebElement password;

    @FindBy(name = "emailRecupero")
    private WebElement emailRecupero;

    @FindBy(xpath = "//button[text()='Recupera Password']")
    private WebElement submitButton;


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    //metodo per inserire password e username
    public void login(String u,String p) {
        typeText(username, u);
        typeText(pass, p);
    }

    public AdministratorPage clickAccedi() {
        click(accedi);
        return new AdministratorPage(driver);// navigo alla pagina dove vedo la lista delle persone
    }

    public ScientificManagerPage clickAccediManager() {
        click(accedi);
        return new ScientificManagerPage(driver);
    }
    public ResearcherPage clickAccediResearcher() {
        click(accedi);
        return new ResearcherPage(driver);
    }

    public LoginPage clickPassword() {
        click(password);
        return new LoginPage(driver);// navigo alla pagina dove vedo la lista delle persone
    }

    public void writeMail(String mail) {
        typeText(emailRecupero, mail);
    }

    public ResultPage submitMail() {
        click(submitButton);
        return new ResultPage(driver);//ritorna la pagina iniziale
    }

    public AddPersonPage clickAddNewPerson() {
        click(addNewPersonLink);
        return new AddPersonPage(driver);// navigo alla pagina dove aggiungo persone
    }

    public PeopleListPage clickList() {
        click(listaLink);
        return new PeopleListPage(driver);// navigo alla pagina dove vedo la lista delle persone
    }

}
