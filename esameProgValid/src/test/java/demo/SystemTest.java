package demo;

import demo.PeopleListPage;
import demo.AddPersonPage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

//ho aggiunto la seguente riga in build.gradle:
//implementation group: 'org.seleniumhq.selenium', name: 'selenium-java', version: '4.24.0'
//FONTE: https://stackoverflow.com/questions/53427069/selenium-package-org-openqa-selenium-does-not-exist
public class SystemTest extends BaseTest {

    @Test
    public void testAddPerson() {
        // pagina principale
        driver.get("http://localhost:8080/");

        // interagisco con la pagina list.html
        PeopleListPage peopleListPage = new PeopleListPage(driver);
        assertEquals("People list message expected", "People list", peopleListPage.getHeadingText());

        // vado alla pagina "Add Person"
        peopleListPage.clickAddNewPerson();

        // interagisco con la pagina "Add Person"
        AddPersonPage addPersonPage = new AddPersonPage(driver);
        assertEquals("new record message expected", "Create a new record", addPersonPage.getHeadingText());
        addPersonPage.fillPersonDetails("mariano", "ceccato");
        addPersonPage.submitForm();

        // torno a input.html
        assertEquals("People list message expected", "People list", peopleListPage.getHeadingText());
        assertEquals("Just two lines expected", 2, peopleListPage.getTableRowCount());
        assertEquals("First name should be 'mariano'", "mariano", peopleListPage.getFirstRowFirstName());
    }
}
