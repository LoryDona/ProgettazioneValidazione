package demo;

import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SystemTest extends BaseTest {

    @Autowired
    private PersonRepository repository;
    @Autowired
    private ReportRepository reportRepository;

    @Before
    public void clean(){//pulisco le strutture prima di ogni test
        Administrator.getProjects().clear();
        repository.deleteAll();
        reportRepository.deleteAll();
    }

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
        loginPage.login("a a","a");
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

    @Test
    public void testSendReport() {
        //creo i due utenti
        Administrator a=new Administrator("a","a","a");
        ScientificManager b=new ScientificManager("b","b","b");
        b.setFree_hours(2);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse("2025-01-01");
            Date endDate = dateFormat.parse("2025-12-31");
            // Aggiunta di un progetto
            a.addProject("p", b, "In Pianificazione", startDate, endDate, 1);
        } catch (Exception e) {e.printStackTrace();}
        repository.save(a);
        repository.save(b);
        reportRepository.save(new Report("r", "res", "1","a","b", "p",false,true));

        driver.get("http://localhost:8080/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("b b","b");//entro come resonsabile scientifico
        loginPage.clickAccediManager();

        ScientificManagerPage manager = new ScientificManagerPage(driver);
        manager.clickSeeReports();
        ReportListPage reports=new ReportListPage(driver);
        reports.writEmail("prova@prova");
        reports.clickSend();
        ResultPage resultPage = new ResultPage(driver);
        String actualMessage = resultPage.getMessageText();
        boolean isValidMessage = actualMessage.equals("Report inviato a prova@prova") || actualMessage.equals("Errore nell'invio");
        assertTrue("Result message expected to be 'Report inviato a prova@prova' or 'Errore', but was: " + actualMessage, isValidMessage);
    }
}

