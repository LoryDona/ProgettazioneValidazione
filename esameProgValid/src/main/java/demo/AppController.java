package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

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



    // Metodo per gestire il recupero della password
    @PostMapping("/recupero")
    public String login(@RequestParam("emailRecupero") String emailRecupero, Model model) {
        // Messaggio da mostrare nella pagina
        String message = "Invio a "+emailRecupero+" avvenuto con successo";
        model.addAttribute("message", message);
        // Restituisci la vista con il messaggio
        return "result";
    }

    @PostMapping("/createReport")
    public String createReport(
            @RequestParam("results") String results,
            @RequestParam("hours") String hours,
            @RequestParam("activities") String activities,
            Model model) {

        if (results.equals("") || hours.equals("") || activities.equals("")){
            model.addAttribute("message", "Nessun campo può essere vuoto");
        }
        else{
            try (FileWriter writer = new FileWriter("output.txt")) {
                writer.write("Risultati:\n"+results+"\nOre: "+hours+"\nActivities:\n"+activities);
            } catch (IOException e) {
                System.err.println("Errore durante il salvataggio della stringa: " + e.getMessage());
                model.addAttribute("message", "Errore nel salvataggio");
            }
            model.addAttribute("message", "Report Salvato");
        }

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
                model.addAttribute("listWorkPackage", ((ScientificManager) result.get()).getWorkPackges());
                model.addAttribute("listTasks", ((ScientificManager) result.get()).getTasks());

                return "pageScientificManager";
            } else {
                model.addAttribute("person", (Researcher) result.get());
                model.addAttribute("listTasks", ((Researcher) result.get()).getTasks());
                return "pageResearcher";
            }
        }

        return "_error";
    }

        @RequestMapping("/listProjects")
    public String listProjects(Model model)
    {

        List<Project> projects = Administrator.getProjects();
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
            @RequestParam(name = "workingHours", required = true) Double workingHours,
            Model model) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        for(Project p : Administrator.getProjects())
        {
            if(p.getNameProject().equals(nameProject))
            {
                return "duplicateProject";
            }
        }


        if(start.isAfter(end)) {
            return "errorDate";
        }

        Administrator admin = (Administrator) repository.findById(IDAdministrator).get();
        ScientificManager scientificManager = (ScientificManager) repository.findById(scientificManagerId).get();

        if(scientificManager.getFree_hours() >= workingHours) {

            scientificManager.setFree_hours(scientificManager.getFree_hours() - workingHours.intValue());

            repository.save(scientificManager);
            admin.addProject(nameProject, scientificManager, state, java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate), budget.intValue());

            model.addAttribute("person", (Administrator) admin);
            return "pageAdministrator";
        }
        else
        {
            model.addAttribute("freeHours", scientificManager.getFree_hours());
            return "noTime";
        }
    }

    @GetMapping("/listProjectScientificManager")
    public String listProjectScientificManager(
            @RequestParam("IDScientificManager") Long idScientificManager,
            Model model) {

        ScientificManager scientificManager = (ScientificManager) repository.findById(idScientificManager).get();
        List<Project> projectsScientificManager = new ArrayList<>();

        for (Project p : Administrator.getProjects()) {
            if (p.getScientificManager().getId().equals(idScientificManager)) {
                projectsScientificManager.add(p);
            }
        }



        model.addAttribute("projects", projectsScientificManager);



        return "projectsScientificManager";

    }

    @RequestMapping("/setHours")
    public String setHorus(@RequestParam(name="scientificManagerID", required=true) long scientificManagerID,
                           @RequestParam(name="freehours", required=true) int freehours,
                           Model model) {

        ScientificManager scientificManager = (ScientificManager) repository.findById(scientificManagerID);
        scientificManager.setFree_hours(freehours);

        repository.save(scientificManager);

        model.addAttribute("person", scientificManager);
        model.addAttribute("message", "Set hours correctly");
        model.addAttribute("listTasks", scientificManager.getTasks());


        return "pageScientificManager";
    }

    //***************SET HOURS PER RESEARCHER**********************************
    @RequestMapping("/setHourResearcher")
    public String setHor(@RequestParam(name="researchedID", required=true) long researcherID,
                           @RequestParam(name="freehours", required=true) int freehours,
                           Model model) {

        Researcher researcher = (Researcher) repository.findById(researcherID);
        researcher.setFree_hours(freehours);

        repository.save(researcher);

        model.addAttribute("person", researcher);
        model.addAttribute("message", "Set hours correctly");



        return "pageResearcher";
    }
    //**************++****************++**************************************

    @RequestMapping("/createWorkPackage")
    public String createWorkPackage(@RequestParam(name="NameProject", required=true) String projectName,
                           Model model) {

        Project project = Administrator.getProjects().stream()
                .filter(pro -> projectName.equals(pro.getNameProject()))
                .findFirst().get();

        model.addAttribute("project", project);

        if(project != null) {
            return "insertWorkPackage";
        }
        else {
            return "_error";
        }

    }


    @PostMapping("/CreateWorkPackagePage")
    public String createWorkPackage(
            @RequestParam("nameWorkPackage") String nameWorkPackage,
            @RequestParam("description") String description,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("nameProject") String nameProject,
            Model model
    ) {

        Optional<Project> optionalProject = Administrator.getProjects().stream()
                .filter(project -> nameProject.equals(project.getNameProject()))
                .findFirst();


        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        if(optionalProject.isPresent()) {

            if(start.isAfter(end))
            {
                return "errorDate";
            }


            Optional<WorkPackage> optionalWorkPackageDuplicate = optionalProject.get().getWorkPackeges().stream()
                    .filter(workPackage -> workPackage.getNameWorkPackage().equals(nameWorkPackage)).findFirst();

            if(optionalWorkPackageDuplicate.isPresent())
            {
                return "errorDuplicateWorkPacakge";
            }

            // Aggiungi il workPackage all'interno del task
            optionalProject.get().addPackage(new WorkPackage(optionalProject.get(), nameWorkPackage, Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant()), description));

            ScientificManager scientificManager  = Administrator.getProjects().stream()
                    .filter(project -> project.getNameProject().equals(nameProject))
                    .findFirst().get().getScientificManager();

            List<Project> projects = scientificManager.getProjects();
            repository.save(scientificManager);


            model.addAttribute("projects", projects);
            model.addAttribute("person", scientificManager);
            model.addAttribute("listWorkPackage", scientificManager.getWorkPackges());
            model.addAttribute("listTasks", scientificManager.getTasks());
       //     model.addAttribute("listMilestone", scientificManager.getMilestones());

            //model.addAttribute("username", scientificManager.getFirstName() + " " + scientificManager.getLastName());
            //model.addAttribute("password", scientificManager.getPassword());


            return "pageScientificManager";
        }
        else {
            return "_error";
        }
    }

    @GetMapping("/createTask")
    public String createTask(
            @RequestParam("NameWorkPackage") String nameWorkPackage,
            @RequestParam("ScientificManagerID") Long scientificManagerId,
            Model model) {

        ScientificManager scientificManager = ((ScientificManager)repository.findById(scientificManagerId).get());
        List<Person> researchers = repository.findByRole("researcher");

        WorkPackage workPackage = scientificManager.getWorkPackges().stream().
                filter(w -> w.getNameWorkPackage().equals(nameWorkPackage)).findFirst().get();


        model.addAttribute("workPackage", workPackage);
        model.addAttribute("scientificManager", scientificManager);
        model.addAttribute("researchers", researchers);

        return "insertTask";

    }

    @PostMapping("/addTask")
    public String addTask(
            @RequestParam("nameTask") String nameTask,
            @RequestParam("Researcher") List<Long> researcherIds,
            @RequestParam("state") String state,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("IDScientificManager") Long IDScientificManager,
            @RequestParam("WorkPackageName") String NameWorkPackage,
            Model model) {

        ScientificManager scientificManager = ((ScientificManager) repository.findById(IDScientificManager).get());

        WorkPackage workPackage = scientificManager.getWorkPackges().stream().
                filter(w -> w.getNameWorkPackage().equals(NameWorkPackage)).findFirst().get();

        List<Researcher> researchers = new ArrayList<>();

        for(Task t : workPackage.getTasks())
        {
            if(t.getNameTask().equals(nameTask))
            {
                return "errorDuplicateNameTask";
            }
        }

        for(Long id : researcherIds)
        {
            researchers.add((Researcher) repository.findById(id).get());
        }

        workPackage.addTask(new Task(nameTask, workPackage, researchers, state, Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));



        model.addAttribute("person", scientificManager);
        model.addAttribute("listWorkPackage", scientificManager.getWorkPackges());
        model.addAttribute("listTasks", scientificManager.getTasks());

        return "pageScientificManager";
    }

// ***********************************************************************************************************************************************
@RequestMapping("/createMilestone")
public String createMilestone(
        @RequestParam(name = "NameProject", required = true) String projectName,
        Model model) {

    // Recupera il progetto in base al nome
    Optional<Project> optionalProject = Administrator.getProjects().stream()
            .filter(project -> projectName.equals(project.getNameProject()))
            .findFirst();

    if (optionalProject.isPresent()) {
        Project project = optionalProject.get();
        // Aggiungi al modello il progetto e i WorkPackage associati
        model.addAttribute("project", project);
        model.addAttribute("listWorkPackage", project.getWorkPackeges());
        return "insertMilestone"; // Nome della vista Thymeleaf
    } else {
        return "errorPage"; // Se il progetto non viene trovato, mostra un errore
    }
}

    @PostMapping("/CreateMilestonePage")
    public String createMilestone(
            @RequestParam("nameMilestone") String nameMilestone,
            @RequestParam("descriptionMilestone") String descriptionMilestone,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam(name = "state", required = true) String state,
            @RequestParam("nameProject") String nameProject,
            @RequestParam(name = "workPackageNames", required = false) List<String> workPackageNames, // Riceve solo i nomi
            Model model) {

        // Parsing delle date
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);

        // Recupera il progetto in base al nome
        Optional<Project> optionalProject = Administrator.getProjects().stream()
                .filter(project -> nameProject.equals(project.getNameProject()))
                .findFirst();

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            // Controlla se la startDate è dopo l'endDate
            if (parsedStartDate.isAfter(parsedEndDate)) {
                return "errorDate";
            }

            // Controllo collisione con altre Milestone
            if (isCollisionWithOtherMilestones(project, parsedStartDate, parsedEndDate)) {
                return "errorCollisionMilestone";
            }

            // Controllo se l'endDate cade in un giorno festivo
            if (isHoliday(parsedEndDate)) {
                return "errorHolidayEndDate";
            }

            // Recupera i WorkPackage in base ai nomi selezionati
            List<WorkPackage> selectedWorkPackages = project.getWorkPackeges().stream()
                    .filter(wp -> workPackageNames.contains(wp.getNameWorkPackage()))
                    .collect(Collectors.toList());

            // Aggiungi la nuova Milestone al progetto
            Milestone newMilestone = new Milestone(
                    nameMilestone,
                    Date.from(parsedStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(parsedEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    descriptionMilestone,
                    state,
                    selectedWorkPackages
            );

            project.addMilestone(newMilestone);

            // Salva il progetto e aggiorna il modello
            repository.save(project.getScientificManager());
            model.addAttribute("projects", getProjectsForScientificManager(project.getScientificManager()));
            model.addAttribute("person", project.getScientificManager());
            model.addAttribute("listWorkPackage", project.getScientificManager().getWorkPackges());
            model.addAttribute("listTasks", project.getScientificManager().getTasks());

            return "pageScientificManager"; // Ritorna alla pagina principale
        } else {
            return "errorDate"; // Se il progetto non viene trovato, mostra un errore
        }
    }

    // Metodo di utilità: Verifica collisioni con altre Milestone
    private boolean isCollisionWithOtherMilestones(Project project, LocalDate startDate, LocalDate endDate) {
        for (Milestone existingMilestone : project.getMilestones()) {
            LocalDate existingStart = existingMilestone.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate existingEnd = existingMilestone.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if ((startDate.isBefore(existingEnd) && endDate.isAfter(existingStart)) ||
                    startDate.equals(existingStart) || endDate.equals(existingEnd)) {
                return true;
            }
        }
        return false;
    }

    // Metodo di utilità: Verifica se una data cade in un giorno festivo
    private boolean isHoliday(LocalDate date) {
        List<MonthDay> holidays = List.of(
                MonthDay.of(1, 1),  // Capodanno
                MonthDay.of(1, 6),  // Epifania
                MonthDay.of(4, 25), // Festa della Liberazione
                MonthDay.of(5, 1),  // Festa del Lavoro
                MonthDay.of(6, 2),  // Festa della Repubblica
                MonthDay.of(11, 1), // Ognissanti
                MonthDay.of(12, 8), // Immacolata
                MonthDay.of(12, 25) // Natale
        );
        return holidays.contains(MonthDay.from(date));
    }

    // Metodo di utilità: Recupera i progetti per un responsabile scientifico
    private List<Project> getProjectsForScientificManager(ScientificManager manager) {
        return Administrator.getProjects().stream()
                .filter(project -> project.getScientificManager().getId().equals(manager.getId()))
                .collect(Collectors.toList());
    }

    // ******************************************************************************************************************************************************************************

}