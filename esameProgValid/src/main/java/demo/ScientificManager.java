package demo;
import jakarta.persistence.Entity;
@Entity
public class ScientificManager extends Person{
    public ScientificManager(String firstName, String lastName, String password)
    {
        super(firstName, lastName, password, "scientificManager");
    }

    public ScientificManager(){};
}
