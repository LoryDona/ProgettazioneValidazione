package demo;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class ScientificManagerTest {

    private ScientificManager scientificManager;
    private ScientificManager anotherScientificManager;
    private Administrator administrator;
    private WorkPackage mockWorkPackage1;
    private WorkPackage mockWorkPackage2;
    private Milestone mockMilestone1;
    private Milestone mockMilestone2;
    private Project mockProject1;
    private Project mockProject2;

    private MockedStatic<Administrator> mockedAdministrator;

    @BeforeEach
    void setUp() throws Exception {
        scientificManager = new ScientificManager("Luca", "Bianchi", "securePass");
        anotherScientificManager = new ScientificManager("Anna", "Rossi", "anotherPass");

        mockWorkPackage1 = mock(WorkPackage.class);
        mockWorkPackage2 = mock(WorkPackage.class);
        mockMilestone1 = mock(Milestone.class);
        mockMilestone2 = mock(Milestone.class);

        mockProject1 = mock(Project.class);
        mockProject2 = mock(Project.class);

        administrator = new Administrator("Giulia", "Verdi", "adminPass");

        resetStaticLists();

        mockedAdministrator = Mockito.mockStatic(Administrator.class);

        List<Project> projects = new ArrayList<>();
        projects.add(mockProject1);
        projects.add(mockProject2);

        mockedAdministrator.when(Administrator::getProjects).thenReturn(projects);

        when(mockProject1.getScientificManager()).thenReturn(scientificManager);
        when(mockProject2.getScientificManager()).thenReturn(scientificManager);

        when(mockProject1.getWorkPackeges()).thenReturn(List.of(mockWorkPackage1));
        when(mockProject2.getWorkPackeges()).thenReturn(List.of(mockWorkPackage2));

        when(mockWorkPackage1.getProjectAssociation()).thenReturn(mockProject1);
        when(mockWorkPackage2.getProjectAssociation()).thenReturn(mockProject2);


        when(mockWorkPackage1.getNameWorkPackage()).thenReturn("WP1");
        when(mockWorkPackage2.getNameWorkPackage()).thenReturn("WP2");

        // Impostazione manuale dei campi 'id' tramite Reflection
        setIdForScientificManager(scientificManager, 1L);
        setIdForScientificManager(anotherScientificManager, 2L);
    }

    @AfterEach
    void tearDown() {
        // Chiude il Mock statico dopo i test
        if (mockedAdministrator != null) {
            mockedAdministrator.close();
        }
    }

    private void resetStaticLists() throws Exception {
        // Resetta la lista statica 'projects' in Administrator
        Field projectsField = Administrator.class.getDeclaredField("projects");
        projectsField.setAccessible(true);
        List<Project> projects = (List<Project>) projectsField.get(null);
        projects.clear();

        // Resetta la lista statica 'works' in ScientificManager
        Field worksField = ScientificManager.class.getDeclaredField("works");
        worksField.setAccessible(true);
        List<WorkPackage> works = (List<WorkPackage>) worksField.get(null);
        works.clear();

        // Resetta la lista statica 'miles' in ScientificManager
        Field milesField = ScientificManager.class.getDeclaredField("miles");
        milesField.setAccessible(true);
        List<Milestone> miles = (List<Milestone>) milesField.get(null);
        miles.clear();
    }

    private void setIdForScientificManager(ScientificManager sm, Long id) throws Exception {
        Field idField = Person.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(sm, id);
    }

    @Test
    @DisplayName("Test del costruttore con parametri")
    void testConstructorWithParameters() {
        ScientificManager sm = new ScientificManager("Marco", "Neri", "password123");

        try {
            setIdForScientificManager(sm, 3L);
        } catch (Exception e) {
            fail("Failed to set id via reflection: " + e.getMessage());
        }

        // Assert
        assertThat(sm.getFirstName()).isEqualTo("Marco");
        assertThat(sm.getLastName()).isEqualTo("Neri");
        assertThat(sm.getPassword()).isEqualTo("password123");
        assertThat(sm.getRole()).isEqualTo("scientificManager");
        assertThat(sm.getId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("Test del costruttore di default")
    void testDefaultConstructor() {
        ScientificManager sm = new ScientificManager();

        assertThat(sm.getFirstName()).isNull();
        assertThat(sm.getLastName()).isNull();
        assertThat(sm.getPassword()).isNull();
        assertThat(sm.getRole()).isEqualTo("scientificManager");
        assertThat(sm.getId()).isNull();
    }

    @Test
    @DisplayName("Test dei getter e setter di free_hours")
    void testFreeHoursGetterSetter() {
        int hours = 20;

        scientificManager.setFree_hours(hours);

        assertThat(scientificManager.getFree_hours()).isEqualTo(hours);
    }


    @Test
    @DisplayName("Test della lista di WorkPackages inizialmente vuota")
    void testInitialWorkPackagesListIsEmpty() {
        // Arrange & Act
        List<WorkPackage> workPackages = ScientificManager.getWorkPackageList();

        // Assert
        assertThat(workPackages).isEmpty();
    }

    @Test
    @DisplayName("Test della lista di Milestones inizialmente vuota")
    void testInitialMilestonesListIsEmpty() throws Exception {
        // Accesso alla lista statica 'miles' tramite Reflection
        Field milesField = ScientificManager.class.getDeclaredField("miles");
        milesField.setAccessible(true);
        List<Milestone> miles = (List<Milestone>) milesField.get(null);

        assertThat(miles).isEmpty();
    }

    @Test
    @DisplayName("Test del recupero di WorkPackages associati a uno ScientificManager")
    void testGetWorkPackages() {
        // Creazione di WorkPackages e aggiunta ai progetti mockati
        WorkPackage wp1 = new WorkPackage(mockProject1, "WP1", new Date(), new Date(), "Descrizione WP1");
        WorkPackage wp2 = new WorkPackage(mockProject2, "WP2", new Date(), new Date(), "Descrizione WP2");
        when(mockProject1.getWorkPackeges()).thenReturn(List.of(wp1));
        when(mockProject2.getWorkPackeges()).thenReturn(List.of(wp2));

        // Aggiunta dei WorkPackages alla lista statica di ScientificManager
        ScientificManager.getWorkPackageList().add(wp1);
        ScientificManager.getWorkPackageList().add(wp2);

        List<WorkPackage> workPackages = scientificManager.getWorkPackges();

        assertThat(workPackages).hasSize(2);
        assertThat(workPackages).containsExactlyInAnyOrder(wp1, wp2);
    }

    @Test
    @DisplayName("Test del recupero di Milestones associati a uno ScientificManager")
    void testGetMilestones() throws Exception {
        Milestone mile1 = new Milestone(mockProject1, "Milestone 1", new Date(), new Date(), "Descrizione Mile1", "in_progress", List.of(mockWorkPackage1));
        Milestone mile2 = new Milestone(mockProject2, "Milestone 2", new Date(), new Date(), "Descrizione Mile2", "completed", List.of(mockWorkPackage2));
        when(mockProject1.getMilestones()).thenReturn(List.of(mile1));
        when(mockProject2.getMilestones()).thenReturn(List.of(mile2));

        Field milesField = ScientificManager.class.getDeclaredField("miles");
        milesField.setAccessible(true);
        List<Milestone> miles = (List<Milestone>) milesField.get(null);
        miles.add(mile1);
        miles.add(mile2);

        List<Milestone> milestones = scientificManager.getMilestones();

        assertThat(milestones).hasSize(2);
        assertThat(milestones).containsExactlyInAnyOrder(mile1, mile2);
    }

    @Test
    @DisplayName("Test del recupero di Tasks associati a uno ScientificManager")
    void testGetTasks() {
        Task task1 = new Task("Task 1", mockWorkPackage1, List.of(), "not_started", new Date(), new Date());
        Task task2 = new Task("Task 2", mockWorkPackage2, List.of(), "in_progress", new Date(), new Date());

        when(mockWorkPackage1.getTasks()).thenReturn(List.of(task1));
        when(mockWorkPackage2.getTasks()).thenReturn(List.of(task2));

        ScientificManager.getWorkPackageList().add(mockWorkPackage1);
        ScientificManager.getWorkPackageList().add(mockWorkPackage2);

        List<Task> tasks = scientificManager.getTasks();

        assertThat(tasks).hasSize(2);
        assertThat(tasks).containsExactlyInAnyOrder(task1, task2);
    }

    @Test
    @DisplayName("Test del recupero di Progetti associati a uno ScientificManager")
    void testGetProjects() {
        List<Project> projects = scientificManager.getProjects();

        assertThat(projects).hasSize(2);
        assertThat(projects).containsExactlyInAnyOrder(mockProject1, mockProject2);
    }

    @Test
    @DisplayName("Test del recupero di WorkPackages quando ScientificManager ha nessun progetto associato")
    void testGetWorkPackagesWithNoAssociatedProjects() {
        List<Project> projects = Administrator.getProjects();
        projects.clear();

        List<WorkPackage> workPackages = scientificManager.getWorkPackges();

        assertThat(workPackages).isEmpty();
    }

    @Test
    @DisplayName("Test del recupero di Milestones quando ScientificManager ha nessun progetto associato")
    void testGetMilestonesWithNoAssociatedProjects() throws Exception {
        List<Project> projects = Administrator.getProjects();
        projects.clear();

        List<Milestone> milestones = scientificManager.getMilestones();

        assertThat(milestones).isEmpty();
    }

    @Test
    @DisplayName("Test del recupero di Tasks quando ScientificManager ha nessun progetto associato")
    void testGetTasksWithNoAssociatedProjects() {
        List<Project> projects = Administrator.getProjects();
        projects.clear();

        List<Task> tasks = scientificManager.getTasks();

        assertThat(tasks).isEmpty();
    }
}