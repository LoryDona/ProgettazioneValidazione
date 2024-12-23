package demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkPackage {
    private Project projectAssociation;

    private String nameWorkPackage;
    private List<Task> tasks = new ArrayList<Task>();
    private String description;

    private Date startDate;

    private Date endDate;

    public WorkPackage(String nameWorkPackage, Date startDate, Date endDate, String description)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.nameWorkPackage = nameWorkPackage;
    }

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

    public void setDescription(String descriptipn)
    {
        this.description = descriptipn;
    }

    public String getDescription() {
        return description;
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

    public void setNameWorkPackage(String nameWorkPackage) {
        this.nameWorkPackage = nameWorkPackage;
    }

    public String getNameWorkPackage() {
        return nameWorkPackage;
    }
}
