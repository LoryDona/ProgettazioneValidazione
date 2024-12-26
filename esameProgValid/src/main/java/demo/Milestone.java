package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Milestone {
    /*
        public enum StatusMilestone {
            NOT_STARTED,
            IN_PROGRESS,
            COMPLETED,
            LATE
        }
    */
    public List<Researcher> researchers;
    ScientificManager scientificManager;
    private Project projectAssociation;

    private String state;
    private String nameMilestone;
    private String descriptionMilestone;

    public List<WorkPackage> workpackages = new ArrayList<WorkPackage>();

    private Date startDate;
    private Date endDate;

    //   private StatusMilestone statusMilestone;


    public Milestone(String nameMilestone, Date startDate, Date endDate, String descriptionMilestone, String state, List<String> workPackages) {
        this.nameMilestone = nameMilestone;
        this.descriptionMilestone = descriptionMilestone;
        this.startDate = startDate;
        this.endDate = endDate;
    }



    //  public StatusMilestone getStatusMilestone() { return statusMilestone; }
    //  public void setStatusMilestone(StatusMilestone statusMilestone) { this.statusMilestone = statusMilestone; }
    public String getState() { return state; }

    public void setProjectAssociation(Project projectAssociation) {
        this.projectAssociation = projectAssociation;
    }

    public Project getProjectAssociation() {
        return projectAssociation;
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

    public void setStartDate(Date endDate) {
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
