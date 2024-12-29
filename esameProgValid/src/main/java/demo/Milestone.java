package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Date;
import java.util.List;

public class Milestone {

    private String name;                      // Nome della Milestone
    private String description;               // Descrizione della Milestone
    private String state;                     // Stato della Milestone (es. not_started, in_progress, ecc.)
    private Date startDate;                   // Data di inizio
    private Date endDate;                     // Data di fine
    private List<WorkPackage> workPackages;   // Lista dei WorkPackage associati

    // Costruttore
    public Milestone(String name, Date startDate, Date endDate, String description, String state, List<WorkPackage> workPackages) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.state = state;
        this.workPackages = workPackages;
    }

    // Getter e Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<WorkPackage> getWorkPackages() {
        return workPackages;
    }

    public void setWorkPackages(List<WorkPackage> workPackages) {
        this.workPackages = workPackages;
    }
}