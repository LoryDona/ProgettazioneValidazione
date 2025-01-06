package demo;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WorkPackageTest {

    private WorkPackage workPackage;
    private Project mockProject;
    private Milestone mockMilestone;
    private Task mockTask1;
    private Task mockTask2;
    private Date startDate;
    private Date endDate;

    @BeforeEach
    void setUp() {
        mockProject = mock(Project.class);
        mockMilestone = mock(Milestone.class);
        mockTask1 = mock(Task.class);
        mockTask2 = mock(Task.class);

        startDate = new Date();
        endDate = new Date(startDate.getTime() + 864000);

        workPackage = new WorkPackage(mockProject, "WP1", startDate, endDate, "Descrizione WP1");
    }

    @Test
    @DisplayName("Test del costruttore con parametri")
    void testConstructorWithParameters() {

        String name = "WP2";
        String description = "Descrizione WP2";
        Date start = new Date();
        Date end = new Date(start.getTime() + 86400000);
        Project project = mock(Project.class);

        WorkPackage wp = new WorkPackage(project, name, start, end, description);

        assertThat(wp.getNameWorkPackage()).isEqualTo(name);
        assertThat(wp.getDescription()).isEqualTo(description);
        assertThat(wp.getStartDate()).isEqualTo(start);
        assertThat(wp.getEndDate()).isEqualTo(end);
        assertThat(wp.getProjectAssociation()).isEqualTo(project);
        assertThat(wp.getTasks()).isEmpty();
        assertThat(wp.getMilestoneAssociation()).isNull();
    }

    @Test
    @DisplayName("Test dei getter e setter per nameWorkPackage")
    void testGetSetNameWorkPackage() {
        String newName = "WP Renamed";

        workPackage.setNameWorkPackage(newName);

        assertThat(workPackage.getNameWorkPackage()).isEqualTo(newName);
    }

    @Test
    @DisplayName("Test dei getter e setter per description")
    void testGetSetDescription() {
        String newDescription = "Nuova descrizione";

        workPackage.setDescription(newDescription);

        assertThat(workPackage.getDescription()).isEqualTo(newDescription);
    }

    @Test
    @DisplayName("Test dei getter e setter per startDate")
    void testGetSetStartDate() {
        Date newStartDate = new Date(startDate.getTime() + 100000);

        workPackage.setStartDate(newStartDate);

        assertThat(workPackage.getStartDate()).isEqualTo(newStartDate);
    }

    @Test
    @DisplayName("Test dei getter e setter per endDate")
    void testGetSetEndDate() {
        Date newEndDate = new Date(endDate.getTime() + 100000);

        workPackage.setEndDate(newEndDate);

        assertThat(workPackage.getEndDate()).isEqualTo(newEndDate);
    }

    @Test
    @DisplayName("Test dei getter e setter per projectAssociation")
    void testGetSetProjectAssociation() {
        Project newProject = mock(Project.class);

        workPackage.setProjectAssociation(newProject);

        assertThat(workPackage.getProjectAssociation()).isEqualTo(newProject);
    }

    @Test
    @DisplayName("Test dei getter e setter per milestoneAssociation")
    void testGetSetMilestoneAssociation() {
        Milestone newMilestone = mock(Milestone.class);

        workPackage.setMilestoneAssociation(newMilestone);

        assertThat(workPackage.getMilestoneAssociation()).isEqualTo(newMilestone);
    }

    @Test
    @DisplayName("Test del metodo addTask")
    void testAddTask() {
        workPackage.addTask(mockTask1);
        workPackage.addTask(mockTask2);

        List<Task> tasks = workPackage.getTasks();
        assertThat(tasks).hasSize(2);
        assertThat(tasks).containsExactly(mockTask1, mockTask2);
    }

    @Test
    @DisplayName("Test del metodo getTasks quando non ci sono task")
    void testGetTasksWhenEmpty() {
        List<Task> tasks = workPackage.getTasks();

        assertThat(tasks).isEmpty();
    }

}
