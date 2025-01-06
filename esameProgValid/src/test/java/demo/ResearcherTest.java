package demo;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class ResearcherTest {

    private Researcher researcher;
    private Researcher anotherResearcher;
    private Administrator administrator;
    private Task mockTask1;
    private Task mockTask2;
    private Project mockProject1;
    private Project mockProject2;
    private WorkPackage mockWorkPackage1;
    private WorkPackage mockWorkPackage2;

    private MockedStatic<Administrator> mockedAdministrator;

    @BeforeEach
    void setUp() throws Exception {
        // Creazione di istanze di Researcher
        researcher = new Researcher("Luca", "Bianchi", "securePass");
        anotherResearcher = new Researcher("Anna", "Rossi", "anotherPass");

        // Mock delle dipendenze
        mockTask1 = mock(Task.class);
        mockTask2 = mock(Task.class);
        mockProject1 = mock(Project.class);
        mockProject2 = mock(Project.class);
        mockWorkPackage1 = mock(WorkPackage.class);
        mockWorkPackage2 = mock(WorkPackage.class);

        // Creazione di un'istanza di Administrator (non mockata)
        administrator = new Administrator("Giulia", "Verdi", "adminPass");

        // Reset delle liste statiche tramite Reflection
        resetStaticLists();

        // Mock di Administrator.getProjects() usando Mockito-inline per mockare metodi statici
        mockedAdministrator = Mockito.mockStatic(Administrator.class);

        // Crea una lista di progetti
        List<Project> projects = new ArrayList<>();
        projects.add(mockProject1);
        projects.add(mockProject2);

        // Configurazione del mock di Administrator.getProjects()
        mockedAdministrator.when(Administrator::getProjects).thenReturn(projects);

        // Configurazione dei progetti mockati per ritornare correttamente i WorkPackages
        when(mockProject1.getWorkPackeges()).thenReturn(List.of(mockWorkPackage1));
        when(mockProject2.getWorkPackeges()).thenReturn(List.of(mockWorkPackage2));

        // Configurazione dei WorkPackages per associare Project
        when(mockWorkPackage1.getProjectAssociation()).thenReturn(mockProject1);
        when(mockWorkPackage2.getProjectAssociation()).thenReturn(mockProject2);

        // Configurazione dei Tasks per associare WorkPackage e Researchers
        when(mockTask1.getResearchers()).thenReturn(List.of(researcher));
        when(mockTask2.getResearchers()).thenReturn(List.of(anotherResearcher));

        // Impostazione manuale dei campi 'id' tramite Reflection
        setIdForResearcher(researcher, 1L);
        setIdForResearcher(anotherResearcher, 2L);
    }

    @AfterEach
    void tearDown() {
        // Chiude il Mock statico dopo i test
        if (mockedAdministrator != null) {
            mockedAdministrator.close();
        }
    }

    /**
     * Resetta le liste statiche 'projects' in Administrator.
     */
    private void resetStaticLists() throws Exception {
        // Resetta la lista statica 'projects' in Administrator
        Field projectsField = Administrator.class.getDeclaredField("projects");
        projectsField.setAccessible(true);
        List<Project> projects = (List<Project>) projectsField.get(null);
        projects.clear();
    }

    /**
     * Imposta manualmente il campo 'id' di una istanza di Researcher tramite Reflection.
     */
    private void setIdForResearcher(Researcher r, Long id) throws Exception {
        Field idField = Person.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(r, id);
    }

    @Test
    @DisplayName("Test del costruttore con parametri")
    void testConstructorWithParameters() throws Exception {
        // Arrange & Act
        Researcher r = new Researcher("Marco", "Neri", "password123");

        // Impostazione manuale dell'id
        setIdForResearcher(r, 3L);

        // Assert
        assertThat(r.getFirstName()).isEqualTo("Marco");
        assertThat(r.getLastName()).isEqualTo("Neri");
        assertThat(r.getPassword()).isEqualTo("password123");
        assertThat(r.getRole()).isEqualTo("researcher");
        assertThat(r.getId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("Test dei getter e setter di free_hours")
    void testFreeHoursGetterSetter() {
        int hours = 20;

        researcher.setFree_hours(hours);

        assertThat(researcher.getFree_hours()).isEqualTo(hours);
    }

    @Test
    @DisplayName("Test del recupero di Tasks associati a un Researcher con tasks")
    void testGetTasksWithAssignedTasks() {
        when(mockProject1.getWorkPackeges()).thenReturn(List.of(mockWorkPackage1));
        when(mockProject2.getWorkPackeges()).thenReturn(List.of(mockWorkPackage2));

        when(mockWorkPackage1.getTasks()).thenReturn(List.of(mockTask1));
        when(mockWorkPackage2.getTasks()).thenReturn(List.of(mockTask2));

        List<Task> tasks = researcher.getTasks();

        assertThat(tasks).hasSize(1);
        assertThat(tasks).containsExactly(mockTask1);
    }

    @Test
    @DisplayName("Test del recupero di Tasks associati a un Researcher senza tasks")
    void testGetTasksWithoutAssignedTasks() {
        when(mockProject1.getWorkPackeges()).thenReturn(List.of(mockWorkPackage1));
        when(mockProject2.getWorkPackeges()).thenReturn(List.of(mockWorkPackage2));

        when(mockTask1.getResearchers()).thenReturn(List.of());
        when(mockTask2.getResearchers()).thenReturn(List.of());

        when(mockWorkPackage1.getTasks()).thenReturn(List.of(mockTask1));
        when(mockWorkPackage2.getTasks()).thenReturn(List.of(mockTask2));

        List<Task> tasks = researcher.getTasks();

        assertThat(tasks).isEmpty();
    }

    @Test
    @DisplayName("Test del recupero di Tasks quando non ci sono progetti")
    void testGetTasksWithNoProjects() {
        mockedAdministrator.when(Administrator::getProjects).thenReturn(List.of());

        List<Task> tasks = researcher.getTasks();

        assertThat(tasks).isEmpty();
    }

    @Test
    @DisplayName("Test del recupero di Tasks con multipli progetti e workPackages")
    void testGetTasksWithMultipleProjectsAndWorkPackages() {
        WorkPackage wp1 = mock(WorkPackage.class);
        WorkPackage wp2 = mock(WorkPackage.class);
        WorkPackage wp3 = mock(WorkPackage.class);

        when(mockProject1.getWorkPackeges()).thenReturn(List.of(wp1, wp2));
        when(mockProject2.getWorkPackeges()).thenReturn(List.of(wp3));

        Task taskA = mock(Task.class);
        Task taskB = mock(Task.class);
        Task taskC = mock(Task.class);

        when(wp1.getTasks()).thenReturn(List.of(taskA));
        when(wp2.getTasks()).thenReturn(List.of(taskB));
        when(wp3.getTasks()).thenReturn(List.of(taskC));

        when(taskA.getResearchers()).thenReturn(List.of(researcher));
        when(taskB.getResearchers()).thenReturn(List.of(anotherResearcher));
        when(taskC.getResearchers()).thenReturn(List.of(researcher, anotherResearcher));

        List<Task> tasks = researcher.getTasks();

        assertThat(tasks).hasSize(2);
        assertThat(tasks).containsExactlyInAnyOrder(taskA, taskC);
    }

    @Test
    @DisplayName("Test dell'aggiunta e recupero di free_hours")
    void testAddAndRetrieveFreeHours() {
        int initialHours = 10;
        int additionalHours = 15;

        researcher.setFree_hours(initialHours);
        researcher.setFree_hours(additionalHours);

        assertThat(researcher.getFree_hours()).isEqualTo(additionalHours);
    }

    @Test
    @DisplayName("Test del recupero di Tasks quando il Researcher non è assegnato a nessun Task")
    void testGetTasksWithNoAssignments() {
        when(mockProject1.getWorkPackeges()).thenReturn(List.of(mockWorkPackage1));
        when(mockWorkPackage1.getTasks()).thenReturn(List.of(mockTask1, mockTask2));

        when(mockTask1.getResearchers()).thenReturn(List.of());
        when(mockTask2.getResearchers()).thenReturn(List.of());

        List<Task> tasks = researcher.getTasks();

        assertThat(tasks).isEmpty();
    }

    @Test
    @DisplayName("Test del recupero di Tasks con progetti senza WorkPackages")
    void testGetTasksWithProjectsWithoutWorkPackages() {
        when(mockProject1.getWorkPackeges()).thenReturn(List.of());
        when(mockProject2.getWorkPackeges()).thenReturn(List.of());

        List<Task> tasks = researcher.getTasks();

        assertThat(tasks).isEmpty();
    }
}
