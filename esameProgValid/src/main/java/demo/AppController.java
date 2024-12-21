package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class AppController {
    private int contaFallimenti=0; //usato per controllare quante volte l'invio del report fallisce

    @Autowired
    private PersonRepository repository;

    @RequestMapping("/")
    public String index(){return "login";}

    // Metodo per gestire il login
    @PostMapping("/credenziali")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("ruolo") String ruolo,
                        @RequestParam("mail") String mail, Model model) {

        if ("mario".equals(username)  && "rossi".equals(password) && "amministrativo".equals(ruolo) && "mail@gmail.com".equals(mail))
            return "list"; // redirect alla list
        else if ("a".equals(username)  && "a".equals(password) && "a".equals(ruolo) && "a".equals(mail))
                return "invioReport"; //serve per testare la pagina dell'invio del report
        else {
            model.addAttribute("message", "Username o password errati");
            return "result";
        }
    }

    // Metodo per gestire il recupero della password
    @PostMapping("/recupero")
    public String login(@RequestParam("emailRecupero") String emailRecupero, Model model) {
        // Messaggio da mostrare nella pagina
        String message = "Invio a "+emailRecupero+" avvenuto con successo";
        model.addAttribute("message", message);
        // Restituisci la vista con il messaggio
        return "result";
    }

    @PostMapping("/submitReport")
    public String submitReport(
            @RequestParam("email") String email,
            @RequestParam("report") String reportName,
            Model model) {
        String message="";
        float randomValue=(float) Math.random();//simula un errore di rete, se minore di 0,5 c'è un errore
        if (randomValue > 0.5) {
            message = "Report "+reportName+" inviato a " + email;
            contaFallimenti=0;
        }
        else if (randomValue < 0.5 && contaFallimenti<3) {
            message = "Errore nell'invio";
            contaFallimenti++;
        }
        else{
            message = "Controllare lo stato della rete";
            contaFallimenti=0;
        }
        model.addAttribute("message", message);
        // Restituisci la vista con il messaggio
        return "result";
    }

    //---------------------------------------------------------------------------
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
            @RequestParam(name="lastname", required=true) String lastname) {
        repository.save(new Person(firstname,lastname));
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
            Model model) {
        Optional<Person> result = repository.findById(id);
        if (result.isPresent()) {
            repository.delete(result.get());
            Person person = new Person(firstname,lastname);
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

    @RequestMapping("/listProjects")
    public String listProjects(Model model)
    {

        List<Project> projects = Administrator.projects;
        model.addAttribute("projects", projects);

        return "projects";
    }

}
