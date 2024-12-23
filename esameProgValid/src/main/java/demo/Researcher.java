package demo;
import jakarta.persistence.Entity;
@Entity
public class Researcher extends Person{

    public Researcher(String firstName, String lastName, String password) {
        super(firstName, lastName, password, "researcher");
    }

    public Researcher(){};
}
