package demo;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    public void addWorkPackage(String nameWorkPackage, Date startDate, Date endDate, String description) {
        works = new ArrayList<>();
        works.add(new WorkPackage(nameWorkPackage, startDate, endDate, description));
    }
    public static List<WorkPackage> getWorkPackageList() {
        return works;
    }

}
