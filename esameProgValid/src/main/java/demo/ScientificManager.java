package demo;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
public class ScientificManager extends Person{

    private static List<WorkPackage> works = new ArrayList<WorkPackage>();
    private static List<Milestone> miles = new ArrayList<Milestone>();

    private int free_hours;

    public ScientificManager(String firstName, String lastName, String password)
    {
        super(firstName, lastName, password, "scientificManager");
    }

    public ScientificManager(){
        super(null,null,null,"scientificManager");
    };

    public void setFree_hours(int hours)
    {
        free_hours = hours;
    }

    public int getFree_hours()
    {
        return free_hours;
    }

    public List<WorkPackage> getWorkPackges()
    {
        List<Project> projectSscientificManager = Administrator.getProjects().stream().
                filter(p -> p.getScientificManager().getId().equals(this.getId())).toList();

        List<WorkPackage> workPackages = new ArrayList<WorkPackage>();

        for(Project p : projectSscientificManager)
        {
            workPackages.addAll(p.getWorkPackeges());
        }

        return workPackages;
    }

    public List<Milestone> getMilestones()
    {
        List<Project> projScientificManager = Administrator.getProjects().stream().
                filter(p -> p.getScientificManager().getId().equals(this.getId())).toList();
        List<Milestone> milestones = new ArrayList<Milestone>();

        for(Project p : projScientificManager){
            milestones.addAll(p.getMilestones());
        }
        return milestones;
    }


    public List<Task> getTasks()
    {

        List<WorkPackage> workPackages = this.getWorkPackges();
        List<Task> Tasks = new ArrayList<Task>();

        for(WorkPackage w : workPackages)
        {
            Tasks.addAll(w.getTasks());
        }

        return Tasks;
    }

    public List<Project> getProjects()
    {
        return Administrator.getProjects().stream().
                filter(p -> p.getScientificManager().getId().equals(this.getId())).toList();

    }
    public static List<WorkPackage> getWorkPackageList() {
        return works;
    }

}
