package demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
public class Administrator extends Person{

    private static List<Project> projects = new ArrayList<Project>();
    public Administrator(String firstName, String lastName, String password)
    {
        super(firstName, lastName, password, "administrator");
    }

    public Administrator(){
        super(null, null, null, "administrator");
    };

    // l'amministratore aggiunge un progetto nuovo con associato il responsabile scientifico
    public void addProject(String nameProject, ScientificManager scientificManager, String state, Date startDate, Date endDate, int budget)
    {
        //projects = new ArrayList<>();
        projects.add(new Project(nameProject, this, scientificManager, state, startDate, endDate, budget));
    }

    public static List<Project> getProjects() {
        return projects;
    }
}
