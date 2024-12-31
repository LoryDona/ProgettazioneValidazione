package demo;

import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class SystemTest extends BaseTest {

    @Autowired
    private PersonRepository repository;

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

    @Test
    public void testAddProject() {
        //creo i due utenti
        repository.save(new Administrator("a","a","a"));
        ScientificManager b=new ScientificManager("b","b","b");
        b.setFree_hours(2);
        repository.save(b);
        // pagina principale
        driver.get("http://localhost:8080/");
        // faccio il login come amministratore
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("a","a");
        // vado alla pagina dell'amministratore
        loginPage.clickAccedi();

        // interagisco con la pagina dell'amministratore
        AdministratorPage admin = new AdministratorPage(driver);
        admin.clickCreateProject();

        //sono nella pagina per la creazione del progetto
        ProjectPage projectPage=new ProjectPage(driver);
        projectPage.fillProjectDetails("prova","b","In Pianificazione","2024-01-01","2024-01-02","1","1");
        projectPage.clickCreaprogetto();

        admin.clickListProjects();
        //sono nella pagina che mostra la lista dei progetti
        ProjectListPage projectListPage = new ProjectListPage(driver);
        assertEquals("Just two lines expected", 2, projectListPage.getTableRowCount());
        assertEquals("First name should be 'prova'", "prova", projectListPage.getFirstRowFirstName());
    }
}

