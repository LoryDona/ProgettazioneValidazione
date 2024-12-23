package demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project {

    Administrator administrator;
    private String nameProject;
    private ScientificManager scientificManager;

    private String state;
    private Date startDate;
    private Date endDate;
    private int budget;

    private List<WorkPackage> workPackeges = new ArrayList<WorkPackage>();



    public Project(String nameProject, Administrator administrator, ScientificManager scientificManager, String state, Date startDate, Date endDate, int budget)
    {
        this.nameProject = nameProject;
        this.scientificManager = scientificManager;
        this.administrator = administrator;
    }

    public Project(){};

    public String getNameProject() {
        return nameProject;
    }


    public Administrator getAdministrator() {
        return administrator;
    }


    public ScientificManager getScientificManager() {
        return scientificManager;
    }


    public String getState() {
        return state;
    }


    public Date getStartDate() {
        return startDate;
    }


    public Date getEndDate() {
        return endDate;
    }


    public int getBudget() {
        return budget;
    }

    public void addPackage(WorkPackage workPackage)
    {
        workPackeges.add(workPackage);
    }

    public List<WorkPackage> getWorkPackeges() {
        return workPackeges;
    }
}
