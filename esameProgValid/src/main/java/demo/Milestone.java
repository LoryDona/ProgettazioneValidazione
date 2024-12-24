package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Milestone {

    public enum StatusMilestone {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED,
        LATE
    }

    public List<Researcher> researchers;

    private Project projectAssociation;

    private String nameMilestone;
    private String descriptionMilestone;

    private List<Task> tasks = new ArrayList<Task>();
    private List<WorkPackage> workpackages = new ArrayList<>();

    private Date startDate;
    private Date endDate;

    private StatusMilestone statusMilestone;


    public Milestone(String nameMilestone, Date startDate, Date endDate,  String descriptionMilestone) {
        this.nameMilestone = nameMilestone;
        this.descriptionMilestone = descriptionMilestone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusMilestone = StatusMilestone.NOT_STARTED;
    }



    public StatusMilestone getStatusMilestone() { return statusMilestone; }

    public void setStatusMilestone(StatusMilestone statusMilestone) { this.statusMilestone = statusMilestone; }

    public void setProjectAssociation(Project projectAssociation) {
        this.projectAssociation = projectAssociation;
    }

    public Project getProjectAssociation() {
        return projectAssociation;
    }

    public void addTask(Task task)
    {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addWorkPackage(WorkPackage workPackage) { workpackages.add(workPackage); }

    public List<WorkPackage> getWorkpackages() { return workpackages; }

    public void setDescription(String descriptionMilestone)
    {
        this.descriptionMilestone = descriptionMilestone;
    }

    public String getDescription() {
        return descriptionMilestone;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setNameMilestone(String nameMilestone) {
        this.nameMilestone = nameMilestone;
    }

    public String getNameMilestone() {
        return nameMilestone;
    }
}
