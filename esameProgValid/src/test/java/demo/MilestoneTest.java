package demo;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MilestoneTest {

    private Milestone milestone;
    private Project mockProject;
    private WorkPackage mockWorkPackage1;
    private WorkPackage mockWorkPackage2;
    private Date startDate;
    private Date endDate;
    private List<WorkPackage> workPackages;

    @BeforeEach
    void setUp() {
        mockProject = mock(Project.class);
        mockWorkPackage1 = mock(WorkPackage.class);
        mockWorkPackage2 = mock(WorkPackage.class);

        startDate = new Date();
        endDate = new Date(startDate.getTime() + 86400);

        workPackages = new ArrayList<>();
        workPackages.add(mockWorkPackage1);
        workPackages.add(mockWorkPackage2);

        milestone = new Milestone(mockProject, "Milestone 1", startDate, endDate, "Descrizione Milestone 1", "in_progress", workPackages);
    }

    @Test
    @DisplayName("Test del costruttore con parametri")
    void testConstructorWithParameters() {
        // Arrange
        String name = "Milestone 2";
        String description = "Descrizione Milestone 2";
        String state = "completed";
        Date newStartDate = new Date(startDate.getTime() + 100000);
        Date newEndDate = new Date(endDate.getTime() + 100000);
        Project newProject = mock(Project.class);
        List<WorkPackage> newWorkPackages = Arrays.asList(mockWorkPackage1);

        Milestone newMilestone = new Milestone(newProject, name, newStartDate, newEndDate, description, state, newWorkPackages);

        assertThat(newMilestone.getName()).isEqualTo(name);
        assertThat(newMilestone.getDescription()).isEqualTo(description);
        assertThat(newMilestone.getState()).isEqualTo(state);
        assertThat(newMilestone.getStartDate()).isEqualTo(newStartDate);
        assertThat(newMilestone.getEndDate()).isEqualTo(newEndDate);
        assertThat(newMilestone.getProjectAssociation()).isEqualTo(newProject);
        assertThat(newMilestone.getWorkPackages()).containsExactlyElementsOf(newWorkPackages);
    }

    @Test
    @DisplayName("Test dei getter e setter per name")
    void testGetSetName() {
        String newName = "Updated Milestone Name";

        milestone.setName(newName);

        assertThat(milestone.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("Test dei getter e setter per description")
    void testGetSetDescription() {
        String newDescription = "Nuova Descrizione Milestone";

        milestone.setDescription(newDescription);

        assertThat(milestone.getDescription()).isEqualTo(newDescription);
    }

    @Test
    @DisplayName("Test dei getter e setter per state")
    void testGetSetState() {
        String newState = "not_started";

        milestone.setState(newState);

        assertThat(milestone.getState()).isEqualTo(newState);
    }

    @Test
    @DisplayName("Test dei getter e setter per startDate")
    void testGetSetStartDate() {
        Date newStartDate = new Date(startDate.getTime() + 200000);

        milestone.setStartDate(newStartDate);

        assertThat(milestone.getStartDate()).isEqualTo(newStartDate);
    }

    @Test
    @DisplayName("Test dei getter e setter per endDate")
    void testGetSetEndDate() {
        Date newEndDate = new Date(endDate.getTime() + 200000);

        milestone.setEndDate(newEndDate);

        assertThat(milestone.getEndDate()).isEqualTo(newEndDate);
    }

    @Test
    @DisplayName("Test dei getter e setter per projectAssociation")
    void testGetSetProjectAssociation() {
        Project newProject = mock(Project.class);

        milestone.setProjectAssociation(newProject);

        assertThat(milestone.getProjectAssociation()).isEqualTo(newProject);
    }

    @Test
    @DisplayName("Test dei getter e setter per workPackages")
    void testGetSetWorkPackages() {
        WorkPackage newWorkPackage = mock(WorkPackage.class);
        List<WorkPackage> newWorkPackages = Arrays.asList(newWorkPackage);

        milestone.setWorkPackages(newWorkPackages);

        assertThat(milestone.getWorkPackages()).containsExactlyElementsOf(newWorkPackages);
    }

    @Test
    @DisplayName("Test del metodo addWork")
    void testAddWork() {
        WorkPackage newWorkPackage = mock(WorkPackage.class);

        milestone.addWork(newWorkPackage);

        assertThat(milestone.getWorkPackages()).contains(newWorkPackage);
    }


    @Test
    @DisplayName("Test del metodo addWork con WorkPackage nullo")
    void testAddWorkNull() {
        milestone.addWork(null);

        assertThat(milestone.getWorkPackages()).containsNull();
    }

    @Test
    @DisplayName("Test della lista di WorkPackages inizialmente impostata correttamente")
    void testInitialWorkPackages() {
        assertThat(milestone.getWorkPackages()).containsExactlyElementsOf(workPackages);
    }
}
