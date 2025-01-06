package demo;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TaskTest {

    private Task task;
    private WorkPackage mockWorkPackage;
    private Researcher mockResearcher1;
    private Researcher mockResearcher2;
    private Date startDate;
    private Date endDate;

    @BeforeEach
    void setUp() {
        // Mock delle dipendenze
        mockWorkPackage = mock(WorkPackage.class);
        mockResearcher1 = mock(Researcher.class);
        mockResearcher2 = mock(Researcher.class);

        // Date per il test
        startDate = new Date();
        endDate = new Date(startDate.getTime() + 86400000); // +1 giorno

        // Creazione dell'istanza di Task
        task = new Task("Task 1", mockWorkPackage, Arrays.asList(mockResearcher1, mockResearcher2), "in_progress", startDate, endDate);
    }

    @Test
    @DisplayName("Test del costruttore con parametri")
    void testConstructorWithParameters() {
        // Arrange
        String name = "Task 2";
        String state = "completed";
        Date newStartDate = new Date(startDate.getTime() + 100000);
        Date newEndDate = new Date(endDate.getTime() + 100000);
        List<Researcher> researchers = Arrays.asList(mockResearcher1);

        // Act
        Task newTask = new Task(name, mockWorkPackage, researchers, state, newStartDate, newEndDate);

        // Assert
        assertThat(newTask.getNameTask()).isEqualTo(name);
        assertThat(newTask.getWorkPackageAssociation()).isEqualTo(mockWorkPackage);
        assertThat(newTask.getResearchers()).containsExactly(mockResearcher1);
        assertThat(newTask.getState()).isEqualTo(state);
        assertThat(newTask.getStartDate()).isEqualTo(newStartDate);
        assertThat(newTask.getEndDate()).isEqualTo(newEndDate);
    }

    @Test
    @DisplayName("Test del costruttore di default")
    void testDefaultConstructor() {
        // Arrange & Act
        Task defaultTask = new Task(null, null, null, null, null, null);

        // Assert
        assertThat(defaultTask.getNameTask()).isNull();
        assertThat(defaultTask.getWorkPackageAssociation()).isNull();
        assertThat(defaultTask.getResearchers()).isNull();
        assertThat(defaultTask.getState()).isNull();
        assertThat(defaultTask.getStartDate()).isNull();
        assertThat(defaultTask.getEndDate()).isNull();
    }

    @Test
    @DisplayName("Test dei getter e setter per nameTask")
    void testGetSetNameTask() {
        String newName = "Updated Task Name";

        task.setNameTask(newName);

        assertThat(task.getNameTask()).isEqualTo(newName);
    }

    @Test
    @DisplayName("Test dei getter e setter per workPackageAssociation")
    void testGetSetWorkPackageAssociation() {
        WorkPackage newWorkPackage = mock(WorkPackage.class);

        task.setWorkPackageAssociation(newWorkPackage);

        assertThat(task.getWorkPackageAssociation()).isEqualTo(newWorkPackage);
    }

    @Test
    @DisplayName("Test dei getter e setter per researchers")
    void testGetSetResearchers() {
        Researcher mockResearcher3 = mock(Researcher.class);
        List<Researcher> newResearchers = Arrays.asList(mockResearcher3);

        task.setResearchers(newResearchers);

        assertThat(task.getResearchers()).containsExactly(mockResearcher3);
    }

    @Test
    @DisplayName("Test dei getter e setter per state")
    void testGetSetState() {
        String newState = "completed";

        task.setState(newState);

        assertThat(task.getState()).isEqualTo(newState);
    }

    @Test
    @DisplayName("Test dei getter e setter per startDate")
    void testGetSetStartDate() {
        Date newStartDate = new Date(startDate.getTime() + 200000);

        task.setStartDate(newStartDate);

        assertThat(task.getStartDate()).isEqualTo(newStartDate);
    }

    @Test
    @DisplayName("Test dei getter e setter per endDate")
    void testGetSetEndDate() {
        Date newEndDate = new Date(endDate.getTime() + 200000);

        task.setEndDate(newEndDate);

        assertThat(task.getEndDate()).isEqualTo(newEndDate);
    }

    @Test
    @DisplayName("Test dell'associazione e disassociazione di WorkPackage")
    void testAssociateWorkPackage() {
        WorkPackage newWorkPackage = mock(WorkPackage.class);

        task.setWorkPackageAssociation(newWorkPackage);

        assertThat(task.getWorkPackageAssociation()).isEqualTo(newWorkPackage);
    }

    @Test
    @DisplayName("Test dell'associazione e disassociazione dei Researchers")
    void testAssociateResearchers() {
        Researcher mockResearcher3 = mock(Researcher.class);
        Researcher mockResearcher4 = mock(Researcher.class);
        List<Researcher> newResearchers = Arrays.asList(mockResearcher3, mockResearcher4);

        task.setResearchers(newResearchers);

        assertThat(task.getResearchers()).containsExactly(mockResearcher3, mockResearcher4);
    }

    @Test
    @DisplayName("Test dell'associazione e disassociazione dello State")
    void testAssociateState() {
        String newState = "not_started";

        task.setState(newState);

        assertThat(task.getState()).isEqualTo(newState);
    }

    @Test
    @DisplayName("Test dell'associazione e disassociazione delle Date")
    void testAssociateDates() {
        Date newStartDate = new Date(startDate.getTime() + 300000);
        Date newEndDate = new Date(endDate.getTime() + 300000);

        task.setStartDate(newStartDate);
        task.setEndDate(newEndDate);

        assertThat(task.getStartDate()).isEqualTo(newStartDate);
        assertThat(task.getEndDate()).isEqualTo(newEndDate);
    }
}
