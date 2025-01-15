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

        // interagisco con la pagina di login
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

        // interagisco con la pagina di login
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
        loginPage.login("b b","b");//entro come responsabile scientifico
        loginPage.clickAccediManager();

        ScientificManagerPage manager = new ScientificManagerPage(driver);
        manager.clickSeeReports();
        ReportListPage reports=new ReportListPage(driver);
        reports.writEmail("prova@prova");
        reports.clickSend();
        ResultPage resultPage = new ResultPage(driver);
        String actualMessage = resultPage.getMessageText();
        //può restituirmi due messaggi a seconda di come è andata la simulazione dell'errore di rete
        boolean isValidMessage = actualMessage.equals("Report inviato a prova@prova") || actualMessage.equals("Errore nell'invio");
        assertTrue("Result message expected to be 'Report inviato a prova@prova' or 'Errore', but was: " + actualMessage, isValidMessage);
    }

    @Test
    public void createWorkPackage() {
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
        // pagina principale
        driver.get("http://localhost:8080/");

        //faccio il login come Scientific Manager
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("b b","b");
        // vado alla pagina dello scientific Manager
        loginPage.clickAccedi();
        ScientificManagerPage manager = new ScientificManagerPage(driver);
        manager.listProjects();

        //vado alla  lista progetti dello scientific manager
        ListProjectScientificManagerPage listProjects=new ListProjectScientificManagerPage(driver);
        listProjects.createWorkPackage();

        //vado alla pagine per creare work package
        CreateWorkPackagePage createWorkPackage=new CreateWorkPackagePage(driver);
        createWorkPackage.fillWorkPackageForm("provaWP", "provaWP", "", "2025-01-01", "2025-01-02");
        createWorkPackage.createWorkPackage();
        //Controllo dalla lista che sia effettivamente presente il nuovo wp
        manager = new ScientificManagerPage(driver);
        assertEquals("Just two lines expected", 2, manager.getTableRowCountWp());
        assertEquals("First name should be 'provaWP'", "provaWP", manager.getFirstRowFirstNameWp());
    }

    @Test
    public void createTask() {
        //creo i due utenti
        Administrator a=new Administrator("a","a","a");
        ScientificManager b=new ScientificManager("b","b","b");
        Researcher c = new Researcher("c","c","c");
        b.setFree_hours(2);
        c.setFree_hours(2);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse("2025-01-01");
            Date endDate = dateFormat.parse("2025-12-31");
            // Aggiunta di un progetto
            a.addProject("p", b, "In Pianificazione", startDate, endDate, 1);
            //    repository.save(a);
            //    b.addWorkPackage("wp", startDate, endDate, "dWP");
        } catch (Exception e) {e.printStackTrace();}
        repository.save(a);
        repository.save(b);
        repository.save(c);
        // pagina principale
        driver.get("http://localhost:8080/");

        //faccio il login come Scientific Manager
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("b b","b");
        // vado alla pagina dello scientific Manager
        loginPage.clickAccedi();
        ScientificManagerPage manager = new ScientificManagerPage(driver);
        manager.listProjects();

//************************************************      SI PUO' MINIMIZZARE LA CREAZIONE WP
        //vado alla  lista progetti dello scientific manager
        ListProjectScientificManagerPage listProjects=new ListProjectScientificManagerPage(driver);
        listProjects.createWorkPackage();

        //vado alla pagine per creare work package
        CreateWorkPackagePage createWorkPackage=new CreateWorkPackagePage(driver);
        createWorkPackage.fillWorkPackageForm("provaWP", "provaWP", "", "2025-01-01", "2025-12-31");
        createWorkPackage.createWorkPackage();
        manager = new ScientificManagerPage(driver);
//*********************************************************************+

        //vado alla pagina per creare Task
        manager.createTaskForWorkPackage("provaWP");
        CreateTaskPage createTask = new CreateTaskPage(driver);
        createTask.fillTaskForm("provaT", "c", "In Pianificazione", "2025-01-01","2025-01-02");
        createTask.createTask();


        manager = new ScientificManagerPage(driver);
        assertEquals("Just two lines expected", 2, manager.getTableRowCountT());
        assertEquals("First name should be 'provaT'", "provaT", manager.getFirstRowFirstNameT());
    }

    @Test
    public void createMilestone() {
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
        //    repository.save(a);
        //    b.addWorkPackage("wp", startDate, endDate, "dWP");
        } catch (Exception e) {e.printStackTrace();}
        repository.save(a);
        repository.save(b);
        // pagina principale
        driver.get("http://localhost:8080/");

        //faccio il login come Scientific Manager
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("b b","b");
        // vado alla pagina dello scientific Manager
        loginPage.clickAccedi();
        ScientificManagerPage manager = new ScientificManagerPage(driver);
        manager.listProjects();

//************************************************      SI PUO' MINIMIZZARE LA CREAZIONE WP
        //vado alla  lista progetti dello scientific manager
        ListProjectScientificManagerPage listProjects=new ListProjectScientificManagerPage(driver);
        listProjects.createWorkPackage();

        //vado alla pagine per creare work package
        CreateWorkPackagePage createWorkPackage=new CreateWorkPackagePage(driver);
        createWorkPackage.fillWorkPackageForm("provaWP", "provaWP", "", "2025-01-01", "2025-01-02");
        createWorkPackage.createWorkPackage();
        manager = new ScientificManagerPage(driver);
        manager.listProjects();
//*********************************************************************+

        //vado alla  lista progetti dello scientific manager
        listProjects=new ListProjectScientificManagerPage(driver);
        listProjects.createMilestonePage();

        //vado alla pagine per creare Milestone
        CreateMilestonePage createMilestone=new CreateMilestonePage(driver);
        createMilestone.fillMilestoneForm("provaMil", "dMil", "Da Iniziare", "provaWP", "2025-01-01","2025-12-31");
        createMilestone.createMilestone();
        //Controllo dalla lista che sia effettivamente presente il nuovo Mil
        manager = new ScientificManagerPage(driver);
        assertEquals("Just two lines expected", 2, manager.getTableRowCountMil());
        assertEquals("First name should be 'provaMil'", "provaMil", manager.getFirstRowFirstNameMil());
    }

    @Test
    public void testCreateReport(){
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

        driver.get("http://localhost:8080/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("b b","b");//entro come responsabile scientifico
        loginPage.clickAccediManager();

        ScientificManagerPage manager = new ScientificManagerPage(driver);
        manager.clickCreateReports();
        CreateReportPage createReportPage=new CreateReportPage(driver);
        createReportPage.fillReportDetails("t","r","1","a","p");
        createReportPage.clickBozzaReport();
        MemoriseReportPage result= new MemoriseReportPage(driver);
        assertEquals("Result message expected:", "Report in bozza", result.getMessageText());

    }

    @Test
    public void postponeMilestone() {
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
            //    repository.save(a);
            //    b.addWorkPackage("wp", startDate, endDate, "dWP");
        } catch (Exception e) {e.printStackTrace();}
        repository.save(a);
        repository.save(b);
        // pagina principale
        driver.get("http://localhost:8080/");

        //faccio il login come Scientific Manager
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("b b","b");
        // vado alla pagina dello scientific Manager
        loginPage.clickAccedi();
        ScientificManagerPage manager = new ScientificManagerPage(driver);
        manager.listProjects();

//************************************************      SI PUO' MINIMIZZARE LA CREAZIONE WP
        //vado alla  lista progetti dello scientific manager
        ListProjectScientificManagerPage listProjects=new ListProjectScientificManagerPage(driver);
        listProjects.createWorkPackage();

        //vado alla pagine per creare work package
        CreateWorkPackagePage createWorkPackage=new CreateWorkPackagePage(driver);
        createWorkPackage.fillWorkPackageForm("provaWP", "provaWP", "", "2025-01-01", "2025-01-02");
        createWorkPackage.createWorkPackage();
        manager = new ScientificManagerPage(driver);
        manager.listProjects();
//*********************************************************************+
//************************************************      SI PUO' MINIMIZZARE LA CREAZIONE MIL
        //vado alla  lista progetti dello scientific manager
        listProjects=new ListProjectScientificManagerPage(driver);
        listProjects.createMilestonePage();

        //vado alla pagine per creare Milestone
        CreateMilestonePage createMilestone=new CreateMilestonePage(driver);
        createMilestone.fillMilestoneForm("provaMil", "dMil", "Da Iniziare", "provaWP", "2025-01-01","2025-06-10");
        createMilestone.createMilestone();
        manager = new ScientificManagerPage(driver);
//***********************************************************************************

        //vado alla pagina per posporre Milestone
        manager.postponeMilestone("provaMil");
     //   manager.postMilestone();
        PostponeMilestonePage postponeMilestone=new PostponeMilestonePage(driver);
        postponeMilestone.fillPostForm("2025-10-10");
        postponeMilestone.updateEndDate();

        manager = new ScientificManagerPage(driver);
        assertEquals("Just two lines expected", 2, manager.getTableRowCountMil());
        assertEquals("New End Date should be '10-10-2025'", "10-10-2025", manager.getFirstRowDateMil());

    }
}
