package demo;
import jakarta.persistence.Entity;
@Entity
public class ScientificManager extends Person{

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
}
