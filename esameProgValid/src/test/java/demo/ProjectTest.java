package demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProjectTest {

    private Administrator mockAdministrator;
    private ScientificManager mockScientificManager;
    private WorkPackage mockWorkPackage1;
    private WorkPackage mockWorkPackage2;
    private Milestone mockMilestone1;
    private Milestone mockMilestone2;

    @BeforeEach
    void setUp() {
        // Creazione di mock delle dipendenze utilizzando Mockito
        mockAdministrator = mock(Administrator.class);
        mockScientificManager = mock(ScientificManager.class);
        mockWorkPackage1 = mock(WorkPackage.class);
        mockWorkPackage2 = mock(WorkPackage.class);
        mockMilestone1 = mock(Milestone.class);
        mockMilestone2 = mock(Milestone.class);
    }

    @Test
    @DisplayName("Test del costruttore con parametri")
    void testConstructorWithParameters() {
        String projectName = "Progetto Alpha";
        String state = "In corso";
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 1000000);
        int budget = 500000;

        Project project = new Project(projectName, mockAdministrator, mockScientificManager, state, startDate, endDate, budget);

        assertThat(project.getNameProject()).isEqualTo(projectName);
        assertThat(project.getAdministrator()).isSameAs(mockAdministrator);
        assertThat(project.getScientificManager()).isSameAs(mockScientificManager);
        assertThat(project.getState()).isEqualTo(state);
        assertThat(project.getStartDate()).isEqualTo(startDate);
        assertThat(project.getEndDate()).isEqualTo(endDate);
        assertThat(project.getBudget()).isEqualTo(budget);
        assertThat(project.getWorkPackeges()).isEmpty();
        assertThat(project.getMilestones()).isEmpty();
    }

    @Test
    @DisplayName("Test del costruttore senza parametri")
    void testDefaultConstructor() {
        Project project = new Project();

        assertThat(project.getNameProject()).isNull();
        assertThat(project.getAdministrator()).isNull();
        assertThat(project.getScientificManager()).isNull();
        assertThat(project.getState()).isNull();
        assertThat(project.getStartDate()).isNull();
        assertThat(project.getEndDate()).isNull();
        assertThat(project.getBudget()).isEqualTo(0);
        assertThat(project.getWorkPackeges()).isEmpty();
        assertThat(project.getMilestones()).isEmpty();
    }

    @Test
    @DisplayName("Test dei getter")
    void testGetters() {
        String projectName = "Progetto Beta";
        String state = "Pianificato";
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 2000000);
        int budget = 750000;

        Project project = new Project();
        project = new Project(projectName, mockAdministrator, mockScientificManager, state, startDate, endDate, budget);

        assertThat(project.getNameProject()).isEqualTo(projectName);
        assertThat(project.getAdministrator()).isSameAs(mockAdministrator);
        assertThat(project.getScientificManager()).isSameAs(mockScientificManager);
        assertThat(project.getState()).isEqualTo(state);
        assertThat(project.getStartDate()).isEqualTo(startDate);
        assertThat(project.getEndDate()).isEqualTo(endDate);
        assertThat(project.getBudget()).isEqualTo(budget);
    }

    @Test
    @DisplayName("Test dell'aggiunta e recupero di WorkPackage")
    void testAddAndGetWorkPackages() {
        Project project = new Project("Progetto Gamma", mockAdministrator, mockScientificManager, "In corso", new Date(), new Date(), 300000);

        project.addPackage(mockWorkPackage1);
        project.addPackage(mockWorkPackage2);

        List<WorkPackage> workPackages = project.getWorkPackeges();
        assertThat(workPackages).hasSize(2).containsExactly(mockWorkPackage1, mockWorkPackage2);
    }

    @Test
    @DisplayName("Test dell'aggiunta e recupero di Milestone")
    void testAddAndGetMilestones() {
        Project project = new Project("Progetto Delta", mockAdministrator, mockScientificManager, "Completato", new Date(), new Date(), 450000);

        project.addMilestone(mockMilestone1);
        project.addMilestone(mockMilestone2);

        List<Milestone> milestones = project.getMilestones();
        assertThat(milestones).hasSize(2).containsExactly(mockMilestone1, mockMilestone2);
    }

    @Test
    @DisplayName("Test che verifica che le liste di WorkPackage e Milestone siano inizialmente vuote")
    void testInitialListsAreEmpty() {
        Project project = new Project("Progetto Epsilon", mockAdministrator, mockScientificManager, "In corso", new Date(), new Date(), 600000);

        assertThat(project.getWorkPackeges()).isEmpty();
        assertThat(project.getMilestones()).isEmpty();
    }

}
