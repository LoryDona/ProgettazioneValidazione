package demo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;


public class Project {

    Administrator administrator;
    private String nameProject;
    private ScientificManager scientificManager;

    public String state;
    public Date startDate;
    public Date endDate;
    public int budget;

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

}
