package demo;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
public class ScientificManager extends Person{

    private static List<WorkPackage> works = new ArrayList<WorkPackage>();

    private int free_hours;

    public ScientificManager(String firstName, String lastName, String password)
    {
        super(firstName, lastName, password, "scientificManager");
    }

    public ScientificManager(){};

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


    public void addWorkPackage(String nameWorkPackage, Date startDate, Date endDate, String description) {;

        Project project = Administrator.getProjects().stream().
                filter(p -> p.getWorkPackeges().stream().
                        filter(w->w.getNameWorkPackage().equals(nameWorkPackage)).findFirst().get().equals(p.getNameProject())).findFirst().get();

        works = new ArrayList<>();
        works.add(new WorkPackage(project, nameWorkPackage, startDate, endDate, description));
    }
    public static List<WorkPackage> getWorkPackageList() {
        return works;
    }

}
