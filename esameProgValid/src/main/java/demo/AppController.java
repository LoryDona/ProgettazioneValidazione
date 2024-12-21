package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

@Controller
public class AppController {

    @Autowired
    private PersonRepository repository;

    @RequestMapping("/")
    public String index(){
        return "login";
    }

    // Metodo per gestire il login
    @PostMapping("/credenziali")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("role") String role,
                        @RequestParam("mail") String mail, Model model) {

        String[] parts = username.split(" ");
        String firstName = parts.length > 0 ? parts[0] : "";
        String lastName = parts.length > 1 ? parts[1] : "";

        for (Person p: repository.findAll()) {
            {
                // Esiste già l'utente, non possono esserci 2 username uguali
                if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                    return "userAlreadyExists";
                }
            }
        }

        if(role.equals("administrator")) {
            repository.save(new Administrator(firstName, lastName, password));
        }
        else if(role.equals("scientificManager"))
        {
            repository.save(new ScientificManager(firstName, lastName, password));
        }
        else
        {
            repository.save(new Researcher(firstName, lastName, password));
        }

        return "login";
    }

    // Metodo per gestire il login
    @PostMapping("/recupero")
    public String login(@RequestParam("emailRecupero") String emailRecupero, Model model) {
        System.out.println("Inviata mail a: "+emailRecupero);
        return "login"; // torna alla pagina di login
    }

    @RequestMapping("/list")
    public String list(Model model){
        List<Person> data = new LinkedList<>();
        for (Person p: repository.findAll()){
            data.add(p);
        }
        model.addAttribute("people", data);
        return "list";
    }

    @RequestMapping("/input")
    public String input(){
        return "input";
    }

    @RequestMapping("/read")
    public String read(
            @RequestParam(name="id", required=true) Long id,
            Model model) {
        Optional<Person> result = repository.findById(id);
        if (result.isPresent()){
            Person person = result.get();
            model.addAttribute("person", person);
            return "read";
        }
        else
            return "notfound";
    }

    @RequestMapping("/create")
    public String create(
            @RequestParam(name="firstname", required=true) String firstname,
            @RequestParam(name="lastname", required=true) String lastname,
            @RequestParam(name="password", required=true) String password,
            @RequestParam(name="role", required=true) String role) {
        repository.save(new Person(firstname,lastname, password, role));
        return "redirect:/list";
    }

    @RequestMapping("/edit")
    public String edit(
            @RequestParam(name="id", required=true) Long id,
            Model model) {
        Optional<Person> result = repository.findById(id);
        if (result.isPresent()) {
            Person person = result.get();
            model.addAttribute("person", person);
            return "edit";
        }
        else
            return "notfound";
    }

    @RequestMapping("/update")
    public String update(
            @RequestParam(name="id", required=true) Long id,
            @RequestParam(name="firstname", required=true) String firstname,
            @RequestParam(name="lastname", required=true) String lastname,
            @RequestParam(name="password", required=true) String password,
            Model model) {
        Optional<Person> result = repository.findById(id);
        if (result.isPresent()) {
            String role = result.get().getRole();
            repository.delete(result.get());
            Person person = new Person(firstname,lastname, password, role);
            repository.save(person);
            return "redirect:/list";
        }
        else
            return "notfound";
    }

   @RequestMapping("/delete")
    public String delete(
            @RequestParam(name="id", required=true) Long id) {
        Optional<Person> result = repository.findById(id);
        if (result.isPresent()){
            repository.delete(result.get());
            return "redirect:/list";
        }
        else
            return "notfound";
    }

    @RequestMapping("/mainPage")
    public String mainPage(@RequestParam(name="username", required=true) String username,
                               @RequestParam(name="password", required=true) String password, Model model) {

        Optional<Person> result = null;

        String[] parts = username.split(" ");
        String firstName = parts.length > 0 ? parts[0] : "";
        String lastName = parts.length > 1 ? parts[1] : "";

        for (Person p: repository.findAll()) {
            {
                // Esiste già l'utente, non possono esserci 2 username uguali

                if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)
                    && p.getPassword().equals(password)) {
                    result = repository.findById(p.getId());
                    break;
                }
            }
        }


        if(result != null && result.isPresent()) {
            if (result.get() instanceof Administrator) {
                model.addAttribute("person", (Administrator) result.get());

                return "pageAdministrator";
            } else if (result.get() instanceof ScientificManager) {
                model.addAttribute("person", (ScientificManager) result.get());

                return "pageScientificManager";
            } else {
                model.addAttribute("person", (Researcher) result.get());
                return "pageResearcher"; // Da implementare ancora
            }
        }

        return "_error";
    }

        @RequestMapping("/listProjects")
    public String listProjects(Model model)
    {

        List<Project> projects = Administrator.projects;
        model.addAttribute("projects", projects);

        return "projects";
    }

    @RequestMapping("/createUser")
    public String createUser(Model model)
    {

        return "createUser";
    }

    @RequestMapping("/createProjectData")
    public String createProjectData(@RequestParam(name="IDAdministrator", required=true) Long IDAdministrator,
                                    Model model)
    {

        model.addAttribute("administrator", repository.findById(IDAdministrator).get());

        model.addAttribute("scientificManager", repository.findByRole("scientificManager"));



        return "createProject";
    }

    @PostMapping("/createProject")
    public String createProject(
            @RequestParam(name = "nameProject", required = true) String nameProject,
            @RequestParam(name = "administratorName", required = true) String administratorName,
            @RequestParam(name = "administrator.getId()", required = true) Long IDAdministrator,
            @RequestParam(name = "scientificManager.getId()", required = true) Long scientificManagerId,
            @RequestParam(name = "state", required = true) String state,
            @RequestParam(name = "startDate", required = true) String startDate,
            @RequestParam(name = "endDate", required = true) String endDate,
            @RequestParam(name = "budget", required = true) Double budget,
            Model model) {
        // Implementazione del metodo
        Administrator admin = (Administrator) repository.findById(IDAdministrator).get();
        ScientificManager scientificManager = (ScientificManager) repository.findById(scientificManagerId).get();


        admin.addProject(nameProject, scientificManager, state, java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate), budget.intValue());

        model.addAttribute("person", (Administrator) admin);
        return "pageAdministrator";
    }

    @GetMapping("/listProjectScientificManager")
    public String listProjectScientificManager(
            @RequestParam("IDScientificManager") Long idScientificManager,
            Model model) {

        ScientificManager scientificManager = (ScientificManager) repository.findById(idScientificManager).get();
        List<Project> projectsScientificManager = new ArrayList<>();

        for (Project p : Administrator.projects) {
            if(p.getScientificManager().getId().equals(idScientificManager)) {
                projectsScientificManager.add(p);
            }
        }

        model.addAttribute("projects", projectsScientificManager);

        return "projects";
    }
}
