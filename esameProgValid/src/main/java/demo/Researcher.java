package demo;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Researcher extends Person{

    private int free_hours;

    public Researcher(String firstName, String lastName, String password) {
        super(firstName, lastName, password, "researcher");
    }

    public List<Task> getTasks()
    {
        List<Project> projects = Administrator.getProjects().stream().toList();
        List<WorkPackage> workPackages = new ArrayList<WorkPackage>();
        List<Task> tasks = new ArrayList<Task>();

        for(Project p : projects)
        {
            workPackages.addAll(p.getWorkPackeges());
        }

        for(WorkPackage w : workPackages)
        {
            for(Task t : w.getTasks())
            {
                for(Researcher r : t.getResearchers())
                {
                    if(r.getId().equals(this.getId()))
                    {
                        tasks.add(t);
                    }
                }
            }
        }

        return tasks;
    }

    public int getFree_hours() {
        return free_hours;
    }

    public void setFree_hours(int free_hours) {
        this.free_hours = free_hours;
    }

    public Researcher(){};
}
