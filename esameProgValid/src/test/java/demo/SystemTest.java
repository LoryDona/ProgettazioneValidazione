package demo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SystemTest extends BaseTest {

    @Test
    public void testPasswordForgot() {
        // pagina principale
        driver.get("http://localhost:8080/");

        // interagisco con la pagina list.html
        LoginPage loginPage = new LoginPage(driver);

        // clicco per mostrare il campo per mandare la password
        loginPage.clickPassword();
        loginPage.writeMail("prova@prova");
        loginPage.submitMail(); //con questo click vado alla pagina result
        ResultPage resultPage = new ResultPage(driver);
        assertEquals("Result message expected:", "Invio a prova@prova avvenuto con successo", resultPage.getMessageText());
    }

    @Test
    public void testAddPerson() {
        // pagina principale
        driver.get("http://localhost:8080/");

        // interagisco con la pagina list.html
        LoginPage loginPage = new LoginPage(driver);

        // vado alla pagina "Create user"
        loginPage.clickAddNewPerson();

        // interagisco con la pagina "Add Person"
        AddPersonPage addPersonPage = new AddPersonPage(driver);
        addPersonPage.fillPersonDetails("a", "a","a@a","Administrator");
        addPersonPage.submitForm();
        loginPage.clickList();// vado alla pagina "list"

        PeopleListPage peopleListPage = new PeopleListPage(driver);
        assertEquals("People list message expected", "People list", peopleListPage.getHeadingText());
        assertEquals("Just two lines expected", 2, peopleListPage.getTableRowCount());
        assertEquals("First name should be 'a'", "a", peopleListPage.getFirstRowFirstName());
    }
}
