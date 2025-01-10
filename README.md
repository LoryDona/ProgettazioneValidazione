# 🖥️ Sistema di Gestione dei Report Accademici🖥️ 
> Progetto per il corso di Progettazione e Validazione di Sistemi Software 2024/2025 dell'Università degli Studi di Verona.

## Indice
1. [🛠️ Scaricare il progetto](#scaricare-il-progetto)
2. [:black_nib:Requisiti](#requisiti)
3. [🎯 Scenari](#scenari)
4. [📃 Classi](#classi)
5. [📋 Test](#test)
6. [👨‍💻 Autori](#autori)
---


## <a id="scaricare-il-progetto"></a> Scaricare il progetto
Per scaricare il progetto è necessario clonare la repository tramite il comando
`git clone https://github.com/FarinaChristian/ProgettazioneValidazione.git`.
Dopodichè è necessario eseguire il comando `./gradlew build` per scaricare le dipendenze e compilare il progetto. Per eseguire il progetto è necessario eseguire il comando `./gradlew bootRun`, che avvierà il server sulla porta `8080`.

---
## <a id="requisiti"></a> Requisiti

#### Registrazione di un utente

L'applicazione deve dare la possibilità agli utenti di registrarsi.

#### Autenticazione

Gli utenti registrati devono avere la possibilità di autenticarsi per usufruire del sistema.

#### Aggiunta di Progetti

Il sistema deve consentire all'amministrativo di aggiungere nuovi progetti.

#### Salvataggio del Report

Il sistema deve dare la possibilità di salvare su file i report in stato di bozza (salvataggio in un file .txt nella cartella del progetto).

#### Conferma dell'Invio dei Report

Il sistema deve fornire un sistema di notifiche e avvisi per informare gli utenti di eventuali errori o problemi di rete nell'invio dei report.

#### Aggiunta di Work Package

Il sistema deve consentire al responsabile scientifico di aggiungere nuovi work package.

#### Aggiunta di una Milestone

Il sistema deve consentire al responsabile scientifico di aggiungere nuove milestone.

#### Posticipazione delle Milestone

Il sistema deve consentire al responsabile scientifico di posticipare le milestone in caso di necessità.

#### Aggiunta di Task

Il sistema deve consentire al responsabile scientifico di aggiungere nuovi task.

#### Nota bene:

- Solo un amministrativo può creare un progetto
- Un progetto può essere creato solo in presenza di almeno un responsabile scientifico con un numero di ore sufficiente, è quindi necessario entrare nella pagina del responsabile scientifico e impostare un numero sufficiente di ore
- Un report può essere messo in bozza solo in presenza di almeno un progetto
- Un report può essere creato (nel senso che viene salvato su file) solo se controfirmato da un amministrativo e se completo di tutti i dati necessari. Per controfirmare è necessario entrare come amministratore e controfirmare il report nell'apposita pagina.

## <a id="scenari"></a> Scenari

#### 1) 💼 Creazione di Progetti e Allocazione del Responsabile

**Assunzioni iniziali:** Un amministrativo può creare un nuovo progetto e assegnare un responsabile scientifico che avrà accesso alla gestione delle componenti interne (come work package e task).

**Normale:** Aggiungere una funzione di inserimento di nuovi progetti accessibile solo all’amministrativo, con un’interfaccia per selezionare un responsabile scientifico dalla lista di utenti disponibili.

**Cosa può andare storto:** Un responsabile scientifico nominato potrebbe essere già impegnato con altri progetti e quindi non essere disponibile (nel senso che il numero di ore che ha a disposizione sono già esaurite). In questo caso il sistema provvederà a segnalare l’impossibilità di allocare ulteriori ore (e quindi progetti).

**Altre attività:**

**Stato del sistema al completamento:** Al responsabile scientifico sarà assegnato il progetto appena creato dall’amministrativo.

---

#### 2)📋 Aggiunta di Task e Assegnazione ai Ricercatori

**Assunzioni iniziali:** Il responsabile scientifico può aggiungere nuovi task ai work package esistenti e assegnarli ai ricercatori disponibili.

**Normale:** Implementare un’interfaccia per la creazione dei task, permettendo la selezione di uno o più ricercatori a cui assegnare ogni task. Ogni task sarà visibile solo ai ricercatori coinvolti e al responsabile.

**Cosa può andare storto**: Dato che la creazione di un nuovo task richiede la presenza di almeno un ricercatore associato al progetto, potrebbe succedere che nessun ricercatore sia stato ancora aggiunto. Il sistema dovrà segnalare tale errore; il responsabile scientifico provvederà quindi ad associare almeno un ricercatore al progetto in questione.

**Altre attività:** Il responsabile scientifico può aggiungere anche i work package.

**Stato del sistema al completamento:** I work package esistenti sono stati aggiunti nuovi task. I nuovi task sono stati assegnati ai ricercatori disponibili.

---

#### 3) 📦 Aggiunta di Work Package

**Assunzioni iniziali:** Un responsabile scientifico deve avere la possibilità di aggiungere nuovi work package. Non è richiesta l'approvazione dell'amministrativo per questa operazione. La creazione di un nuovo work package non richiede la presenza dei ricercatori.

**Normale:** Creare una funzione che permette l’aggiunta di nuovi work package, supportata magari da un’interfaccia grafica che elenchi tutti i work package già creati precedentemente.

**Cosa può andare storto:** Il work package che si tenta di aggiungere è già presente nella lista, il sistema provvederà quindi a segnalare l’errore con un’opportuna finestra, avvertendo l’utente che il nome del work package è già presente nella lista.

**Altre attività:**

**Stato del sistema al completamento:** Il work package è stato aggiunto.

---

#### 4) 📅 Aggiunta delle Milestone di Progetto

**Assunzioni iniziali:** Il responsabile scientifico può aggiungere le milestone del progetto. La creazione di una milestone richiede la presenza di almeno un work package associato al progetto.

**Normale:** Introdurre un’interfaccia per la visualizzazione delle milestone con opzioni di aggiunta.

**Cosa può andare storto:** Una milestone potrebbe essere messa in una data in cui, per diverse ragioni, non può essere registrata (eventuali collisioni con altre milestone oppure giorni festivi). Il sistema deve implementare un sistema di verifica della disponibilità dei giorni e notificare l’eventuale errore all’utente. Dato che la creazione di una milestone richiede la presenza di almeno un work package associato al progetto, sarà necessario implementare anche un messaggio d’errore che avverta l’assenza di work package se tale situazione si verifica.

**Altre attività:**

**Stato del sistema al completamento:** La milestone è stata aggiunta.

---

#### 5) ⚠️ Notifiche per Problemi di Invio del Report

**Assunzioni iniziali:** Gli utenti riceveranno notifiche in caso di errori o problemi di rete durante l’invio di report all’amministrativo.

**Normale:** Aggiungere una funzionalità per rilevare problemi di connessione o errori nell’invio dei report e inviare notifiche agli utenti interessati, proponendo un’opzione di reinvio. Nel sistema d’esempio si potrebbe simulare un errore di rete generando un numero casuale che fa fallire l’ipotetico invio (genero un numero da 0 a 1, se maggiore di 0,5 invio, altrimenti è un fallimento).

**Cosa può andare storto:** Il problema continua a notificare problemi di rete, e quindi l’invio fallisce. Dopo un numero fissato di fallimenti consecutivi, il sistema dovrebbe notificare all’utente di controllare lo stato della rete poiché il problema potrebbe dipendere da un problema fisico della rete (cavi staccati o qualche nodo problematico all’interno dell’infrastruttura).

**Altre attività:**

**Stato del sistema al completamento:** Il report risulta inviato oppure il messaggio che suggerisce di controllare la rete viene visualizzato.

---

#### 6) 📊 Creazione dei report

**Assunzioni iniziali:** Il sistema deve generare report e garantire la conservazione dei report firmati per almeno 10 anni.

**Normale:** Gli utenti autorizzati(Amministratori, responsabile scientifico, ricercatori) possono creare i report dalla dashboard, inserendo queste 3 informazioni:

•   Risultati ottenuti

•   Rendicontazione delle ore lavorative

•   Attività svolte durante il periodo di riferimento

Inoltre è possibile salvare il report in uno stato di bozza temporanea. Il report, dopo la creazione, deve essere firmato dal creatore e avere una controfirma da un amministratore per essere convalidato e approvato.

**Cosa può andare storto:** Inserimento errato o incompleto dei dati da parte degli utenti (ricercatori, responsabili scientifici o amministrativi). Il sistema deve garantire che alcuni campi siano obbligatori.

**Altre attività:**

**Stato del sistema al completamento:** Il report risulta salvato su file, oppure si riceve un messaggio di errore che suggerisce come risolvere il problema (es. "Il report deve essere prima controfirmato").

---

#### 7) 🔒 Autenticazione Recupero credenziali da parte di un utente

**Assunzioni iniziali:** Se l'utente non è autenticato, il sistema deve reindirizzarlo alla pagina di login. La gestione delle credenziali di accesso è delegata all'amministrativo.

**Normale:** L’utente accede al sistema inserendo il proprio username e la propria password. L'utente deve essere stato precedentemente registrato.

**Cosa può andare storto:** L'utente dimentica la password, per risolvere dovrà accedere alla pagina di login e selezionare  "Recupera Password", verrà quindi inviata una mail all’utente in cui si fornirà una password provvisoria che poi andrà cambiata. Se l'utente dovesse inserire dei dati errati, il sistema negherà l'accesso all'utente.

**Altre attività:**

**Stato del sistema al completamento**: La nuova password è stata inviata.

---

#### 8) ⏳ Posticipazione delle Milestone di Progetto

**Assunzioni iniziali:** Il responsabile scientifico può posticipare le milestone del progetto in caso di ritardo.

**Normale**: Introdurre un’interfaccia per la posticipazione delle milestone con opzioni di modifica per la data.

**Cosa può andare storto:** Una milestone potrebbe essere posticipata in una data in cui, per diverse ragioni, non può essere messa (eventuali collisioni con altre milestone oppure giorni festivi). Il sistema deve implementare un sistema di verifica della disponibilità dei giorni e notificare l’eventuale errore all’utente. Essendo un metodo di posticipa e non di modifica della data, sarà necessario implementare anche un messaggio d'errore che avverta l'anticipo dell'End Date se tale situazione si verifica. 

**Altre attività:**

**Stato del sistema al completamento**: La milestone è stata posticipata con successo.

---

#### 9) ✍️ Registrazione Nuovo Utente

**Assunzioni iniziali:** Il sistema deve consentire agli utenti di registrarsi creando un profilo personale che, a seconda del ruolo di quest'ultimi, fornirà  determinati privilegi.

**Normale**: L’utente si registra al sistema inserendo il proprio nome, password, indirizzo email e il ruolo, poi potrà entrare nel sistema.

**Cosa** **può** **andare** **storto**:  L'utente che si sta tentando di creare esiste già, il sistema dovrà quindi gestire tale errore avvertendo l'utente che sta tentando di creare un profilo già esistente.

**Altre** **attività**:

**Stato** **del** **sistema** **al** **completamento**: Il nuovo utente è stato registrato nel sistema.

---

## <a id="classi"></a> Classi

Di seguito sono riportate le classi più significative utilizzate nel progetto.

**PersonRepository**: repository degli utenti, contiene oggetti di tipo Person

**ReportRepository**: repository dei report, contiene oggetti di tipo Report

**AppController**: il controller utilizzato nel pattern MVC

**Person**: è la super classe delle classi Administrator, ScientificManager e Researcher

**Project**: rappresenta un progetto, gli amministratori hanno un ArrayList statico di oggetti Project. Ogni progetto ha un ArrayList di oggetti WorkPackage e un ArrayList di  oggetti Milestone

**WorkPackage**: classe che rappresenta un WorkPackage, contiene un ArrayList di oggetti Task

**ServingWebContentApplication**: classe da avviare per far partire il progetto

## <a id="test"></a> Test

#### Unit test eseguiti nella cartella test\java\demo

Di seguito è riportato l'elenco delle classi usate per gli unit test:

- **AdministratorTest**: test della classe Administrator
- **MilestoneTest**: test della classe Milestone
- **PersonRepositoryTests**: test per il repository degli utenti (classe Person)
- **ProjectTest**: test per la classe Project
- **ReportTest**: test per la classe dei Report
- **ResearcherTest**: test per la classe che identifica il ricercatore, ovvero la classe Researcher
- **ScientificManagerTest**: test per la classe del responsabile scientifico (ScientificManager)
- **TaskTest**: test per la classe Task
- **WorkPackageTest**: test per la classe WorkPackage

#### Test con Page Object pattern eseguiti nella classe SystemTest nella cartella test\java\demo

- **testPasswordForgot**: verifica il funzionamento del metodoper il recupero della password, la mail di recupero è prova@prova (scenario 7).

- **testAddPerson**: verifica il funzionamento della pagina  e delle funzioni per la registrazione di un nuovo utente (scenario 9).

- **testAddProject**: dati due utenti già registrati, verifica il funzionamento delle funzioni per la creazione di un nuovo progetto e l'allocazione a un responsabile scientifico (scenario 1).

- **testSendReport**: dati i rispettivi utenti e un report già memorizzato, verifica che la funzione che simula un errore di rete durante l'invio del report funzioni, il risultato può essere l'invio corretto del report, o un errore che ne specifica il fallimento (scenario 5).

- **createWorkPackage**: dati due utenti e un progetto associato al responsabile scientifico, verifica la creazione di un work package (scenario 3).

- **createTask**: dati tre utenti (amministratore, responsabile e ricercatore) e un progetto associato al responsabile scientifico, verifica la creazione di un task e la sua associazione ad un ricercatore  (scenario 2).

- **createMilestone**: dati due utenti e un progetto associato al responsabile scientifico, verifica la corretta creazione di una milestone associata al progetto (scenario 4).

- **testCreateReport**: verifica il corretto funzionamento della pagina e delle funzioni per la creazione di un report bozza (scenario 6).

- **postponeMilestone**: dati due utenti e un progetto associato al responsabile scientifico, verifica la posticipazione di una milestone associata al progetto aggiunto (scenario 8).

  Ogni classe Page Object contiene nel nome il suffisso "Page". Tutte queste classi ereditano dalla classe PageObject. La classe SystemTest eredita dalla classe BaseTest.
  
  #### Dati inseriti
  
  In ogni test eseguito con page object abbiamo inserito i dati riportati di seguito:
  
  ```
  Administartor a = new Administrator("a","a","a");
  
  ScientificManager b=new ScientificManager("b","b","b");
  
  Researcher c = new Researcher("c","c","c");
  
  Project p = new Project("p", b, "In Pianificazione", dateFormat.parse("2025-01-01"), dateFormat.parse("2025-12-31"), 1);
  
  Report r = new Report("r", "res", "1","a","b", "p",false,true);
  
  WorkPackage w = new WorkPackage("provaWP", "provaWP", "", "2025-01-01", "2025-01-02");
  
  Milestone m = new Milestone("provaMil", "dMil", "Da Iniziare", "provaWP", "2025-01-01","2025-06-10",lista_dei_ workpackage);
  
  Task t =new Task("provaT", "c", lista_ricercatori,"In Pianificazione", "2025-01-01","2025-01-02");
  ```
  
  Ogni qualvolta era richiesto di inserire un determinato oggetto, sono stati inseriti i dati appena riportati. Prima di ogni test, viene eseguita una pulizia dei due repository.

## <a id="autori"></a> Autori

- [Marjo Shytermeja VR527312](https://github.com/Marjo1996)
- [Christian Farina VR501577](https://github.com/FarinaChristian)
- [Lorenzo Junior Donatiello VR508539](https://github.com/LoryDona)
