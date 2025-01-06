package demo;

import java.util.Date;
import java.util.List;

public class Task {

    private String nameTask;
    private WorkPackage workPackageAssociation;

    private List<Researcher> researchers;

    private String state;

    private Date startDate;

    private Date endDate;

    public Task(String nameTask, WorkPackage workPackage, List<Researcher> researchers, String state, Date startDate, Date endDate)
    {
        this.nameTask = nameTask;
        this.workPackageAssociation = workPackage;
        this.researchers = researchers;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setWorkPackageAssociation(WorkPackage workPackageAssociation) {
        this.workPackageAssociation = workPackageAssociation;
    }

    public List<Researcher> getResearchers() {
        return researchers;
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

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public WorkPackage getWorkPackageAssociation() {
        return workPackageAssociation;
    }

    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }
}
