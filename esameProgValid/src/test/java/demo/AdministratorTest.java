package demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AdministratorTest {

    private Administrator administrator;
    private ScientificManager mockScientificManager;

    @BeforeEach
    void setUp() throws Exception {
        administrator = new Administrator("Giuseppe", "Verdi", "securePass");

        mockScientificManager = mock(ScientificManager.class);

        Field projectsField = Administrator.class.getDeclaredField("projects");
        projectsField.setAccessible(true);
        List<Project> projects = (List<Project>) projectsField.get(null);
        projects.clear();
    }

    @Test
    @DisplayName("Test del costruttore con parametri")
    void testConstructorWithParameters() {
        Administrator admin = new Administrator("Maria", "Rossi", "password123");

        assertThat(admin.getFirstName()).isEqualTo("Maria");
        assertThat(admin.getLastName()).isEqualTo("Rossi");
        assertThat(admin.getPassword()).isEqualTo("password123");
        assertThat(admin.getRole()).isEqualTo("administrator");
    }

    @Test
    @DisplayName("Test del costruttore di default")
    void testDefaultConstructor() {
        Administrator admin = new Administrator();

        assertThat(admin.getFirstName()).isNull();
        assertThat(admin.getLastName()).isNull();
        assertThat(admin.getPassword()).isNull();
        assertThat(admin.getRole()).isEqualTo("administrator");
    }

    @Test
    @DisplayName("Test dell'aggiunta di un progetto")
    void testAddProject() {
        String projectName = "Progetto Nuovo";
        String state = "In corso";
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 1000000);
        int budget = 100000;

        administrator.addProject(projectName, mockScientificManager, state, startDate, endDate, budget);

        List<Project> projects = Administrator.getProjects();
        assertThat(projects).hasSize(1);
        Project addedProject = projects.get(0);
        assertThat(addedProject.getNameProject()).isEqualTo(projectName);
        assertThat(addedProject.getAdministrator()).isSameAs(administrator);
        assertThat(addedProject.getScientificManager()).isSameAs(mockScientificManager);
        assertThat(addedProject.getState()).isEqualTo(state);
        assertThat(addedProject.getStartDate()).isEqualTo(startDate);
        assertThat(addedProject.getEndDate()).isEqualTo(endDate);
        assertThat(addedProject.getBudget()).isEqualTo(budget);
    }

    @Test
    @DisplayName("Test dell'aggiunta multipla di progetti")
    void testAddMultipleProjects() {
        String projectName1 = "Progetto Uno";
        String projectName2 = "Progetto Due";
        String state1 = "Pianificato";
        String state2 = "Completato";
        Date startDate1 = new Date();
        Date endDate1 = new Date(startDate1.getTime() + 500000);
        Date startDate2 = new Date();
        Date endDate2 = new Date(startDate2.getTime() + 1000000);
        int budget1 = 200000;
        int budget2 = 300000;

        administrator.addProject(projectName1, mockScientificManager, state1, startDate1, endDate1, budget1);
        administrator.addProject(projectName2, mockScientificManager, state2, startDate2, endDate2, budget2);

        List<Project> projects = Administrator.getProjects();
        assertThat(projects).hasSize(2);

        Project project1 = projects.get(0);
        assertThat(project1.getNameProject()).isEqualTo(projectName1);
        assertThat(project1.getState()).isEqualTo(state1);
        assertThat(project1.getBudget()).isEqualTo(budget1);

        Project project2 = projects.get(1);
        assertThat(project2.getNameProject()).isEqualTo(projectName2);
        assertThat(project2.getState()).isEqualTo(state2);
        assertThat(project2.getBudget()).isEqualTo(budget2);
    }

    @Test
    @DisplayName("Test della lista di progetti inizialmente vuota")
    void testInitialProjectsListIsEmpty() {
        List<Project> projects = Administrator.getProjects();

        assertThat(projects).isEmpty();
    }

    @Test
    @DisplayName("Test della condivisione della lista di progetti tra più amministratori")
    void testProjectsListIsStatic() {
        // Arrange
        Administrator admin1 = new Administrator("Luca", "Bianchi", "passLuca");
        Administrator admin2 = new Administrator("Anna", "Verdi", "passAnna");
        ScientificManager mockSM1 = mock(ScientificManager.class);
        ScientificManager mockSM2 = mock(ScientificManager.class);

        admin1.addProject("Progetto Admin1", mockSM1, "In corso", new Date(), new Date(), 150000);
        admin2.addProject("Progetto Admin2", mockSM2, "Completato", new Date(), new Date(), 250000);

        List<Project> projects = Administrator.getProjects();
        assertThat(projects).hasSize(2);
        assertThat(projects).extracting(Project::getNameProject)
                .containsExactlyInAnyOrder("Progetto Admin1", "Progetto Admin2");
    }

}
