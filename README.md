## Progetto Corso Progettazione e Validazione di Sistemi Software 2024/2025
> Progetto per il corso di Ingegneria del Software dell'Università degli Studi di Verona.

---


## <a id="scaricare-il-progetto"></a> Scaricare il progetto
Per scaricare il progetto è necessario clonare la repository tramite il comando `git clone https://github.com/FarinaChristian/ProgettazioneValidazione.git.
Dopodiché è necessario eseguire il comando `./gradlew build` per scaricare le dipendenze e compilare il progetto.
Per eseguireil progetto è necessario eseguire il comando `./gradlew bootRun`, che avvierà il server sulla porta `8080`.

---
### Lista degli scenari

#### 1) Creazione di Progetti e Allocazione del Responsabile

**Assunzioni iniziali:** Un amministrativo può creare un nuovo progetto e assegnare un responsabile scientifico che avrà accesso alla gestione delle componenti interne (come work package e task).

**Normale:** Aggiungere una funzione di inserimento di nuovi progetti accessibile solo all’amministrativo, con un’interfaccia per selezionare un responsabile scientifico dalla lista di utenti disponibili.

**Cosa può andare storto:** Un responsabile scientifico nominato potrebbe essere già impegnato con altri progetti e quindi non essere disponibile (nel senso che il numero di ore che ha a disposizione sono già esaurite). In questo caso il sistema provvederà a segnalare l’impossibilità di allocare ulteriori ore (e quindi progetti).

**Altre attività:**

**Stato del sistema al completamento:** Al responsabile scientifico sarà assegnato il progetto appena creato dall’amministrativo.

#### 2) Aggiunta di Task e Assegnazione ai Ricercatori

**Assunzioni iniziali:** Il responsabile scientifico può aggiungere nuovi task ai work package esistenti e assegnarli ai ricercatori disponibili.

**Normale:** Implementare un’interfaccia per la creazione dei task, permettendo la selezione di uno o più ricercatori a cui assegnare ogni task. Ogni task sarà visibile solo ai ricercatori coinvolti e al responsabile.

**Cosa può andare storto**: Dato che la creazione di un nuovo task richiede la presenza di almeno un ricercatore associato al progetto, potrebbe succedere che nessun ricercatore sia stato ancora aggiunto. Il sistema dovrà segnalare tale errore; il responsabile scientifico provvederà quindi ad associare almeno un ricercatore al progetto in questione.

**Altre attività:** Il responsabile scientifico può aggiungere anche i work package.

**Stato del sistema al completamento:** I work package esistenti sono stati aggiunti nuovi task. I nuovi task sono stati assegnati ai ricercatori disponibili.

#### 3) Aggiunta di Work Package

**Assunzioni iniziali:** Un responsabile scientifico deve avere la possibilità di aggiungere nuovi work package. Non è richiesta l'approvazione dell'amministrativo per questa operazione. La creazione di un nuovo work package non richiede la presenza dei ricercatori.

**Normale:** Creare una funzione che permette l’aggiunta di nuovi work package, supportata magari da un’interfaccia grafica che elenchi tutti i work package già creati precedentemente.

**Cosa può andare storto:** Il work package che si tenta di aggiungere è già presente nella lista, il sistema provvederà quindi a segnalare l’errore con un’opportuna finestra, avvertendo l’utente che il nome del work package è già presente nella lista.

**Altre attività:**

**Stato del sistema al completamento:** Il work package è stato aggiunto.

#### 4) Aggiunta delle Milestone di Progetto

**Assunzioni iniziali:** Il responsabile scientifico può aggiungere le milestone del progetto. La creazione di una milestone richiede la presenza di almeno un ricercatore associato al progetto.

**Normale:** Introdurre un’interfaccia per la visualizzazione delle milestone con opzioni di aggiunta.

**Cosa può andare storto:** Una milestone potrebbe essere messa in una data in cui, per diverse ragioni, non può essere registrata (eventuali collisioni con altre milestone oppure giorni festivi). Il sistema deve implementare un sistema di verifica della disponibilità dei giorni e notificare l’eventuale errore all’utente. Dato che la creazione di una milestone richiede la presenza di almeno un ricercatore associato al progetto, sarà necessario implementare anche un messaggio d’errore che avverta l’assenza di ricercatori se tale situazione si verifica.

**Altre attività:**

**Stato del sistema al completamento:** La milestone è stata aggiunta.

#### 5) Notifiche per Problemi di Invio del Report

**Assunzioni iniziali:** Gli utenti riceveranno notifiche in caso di errori o problemi di rete durante l’invio di report all’amministrativo.

**Normale:** Aggiungere una funzionalità per rilevare problemi di connessione o errori nell’invio dei report e inviare notifiche agli utenti interessati, proponendo un’opzione di reinvio. Nel sistema d’esempio si potrebbe simulare un errore di rete generando un numero casuale che fa fallire l’ipotetico invio (genero un numero da 0 a 1, se maggiore di 0,5 invio, altrimenti è un fallimento).

**Cosa può andare storto:** Il problema continua a notificare problemi di rete, e quindi l’invio fallisce. Dopo un numero fissato di fallimenti consecutivi, il sistema dovrebbe notificare all’utente di controllare lo stato della rete poiché il problema potrebbe dipendere da un problema fisico della rete (cavi staccati o qualche nodo problematico all’interno dell’infrastruttura).

**Altre attività:**

**Stato del sistema al completamento:** Il report risulta inviato oppure il messaggio che suggerisce di controllare la rete viene visualizzato.

#### 6) Creazione dei report

**Assunzioni iniziali:** Generazione di Report: Il sistema deve generare report e garantire la conservazione dei report firmati per almeno 10 anni.

**Normale:** Gli utenti autorizzati(Amministratori, responsabile scientifico, ricercatori) possono creare i report dalla dashboard, inserendo queste 3 informazioni:

•   Risultati ottenuti

•   Rendicontazione delle ore lavorative

•   Attività svolte durante il periodo di riferimento

Inoltre è possibile salvare il report in uno stato di bozza temporanea. Il report, dopo la creazione deve essere firmato dal creatore e avere una controfirma da un amministratore per essere convalidato e approvato, inoltre si devono eseguire backup giornalieri per garantire l’integrità dei dati.

**Cosa può andare storto:** Inserimento errato o incompleto dei dati da parte degli utenti (ricercatori, responsabili scientifici o amministrativi). Il sistema deve garantire che alcuni campi siano obbligatori.

#### 7) Autenticazione Recupero credenziali da parte di un utente

**Assunzioni iniziali:** Se l'utente non è autenticato, il sistema deve reindirizzarlo alla pagina di login. La gestione delle credenziali di accesso è delegata all'amministrativo.

**Normale:** L’utente accede al sistema inserendo il proprio username e la propria password. L'utente deve essere stato precedentemente registrato.

**Cosa può andare storto:** L'utente dimentica la password, per risolvere dovrà accedere alla pagina di login e selezionare  "Recupera Password", verrà quindi inviata una mail all’utente in cui si fornirà una password provvisoria che poi andrà cambiata. Se l'utente dovesse inserire dei dati errati, il sistema negherà l'accesso all'utente.

#### 8) Posticipazione delle Milestone di Progetto

**Assunzioni iniziali:** Il responsabile scientifico può posticipare le milestone del progetto in caso di ritardo. La posticipa di una milestone richiede la continua presenza di almeno un ricercatore associato al progetto.

**Normale**: Introdurre un’interfaccia per la posticipazione delle milestone con opzioni di modifica per la data.

**Cosa può andare storto:** Una milestone potrebbe essere posticipata in una data in cui, per diverse ragioni, non può essere messa (eventuali collisioni con altre milestone oppure giorni festivi). Il sistema deve implementare un sistema di verifica della disponibilità dei giorni e notificare l’eventuale errore all’utente. Essendo un metodo di posticipa e non di modifica della data, sarà necessario implementare anche un messaggio d'errore che avverta l'anticipo dell'End Date se tale situazione si verifica. 

**Altre attività:**

**Stato del sistema al completamento**: La milestone è stata posticipata con successo.

#### 9) Registrazione Nuovo Utente

**Assunzioni iniziali:** Il sistema deve consentire agli utenti di registrarsi creando un profilo personale che, a seconda del ruolo di quest'ultimi, fornirà  determinati privilegi.

**Normale**: L’utente si registra al sistema inserendo il proprio nome, password, indirizzo email e il ruolo, poi potrà entrare nel sistema.

**Cosa** **può** **andare** **storto**:  L'utente che si sta tentando di creare esiste già, il sistema dovrà quindi gestire tale errore avvertendo l'utente che sta tentando di creare un profilo già esistente.

**Altre** **attività**:

**Stato** **del** **sistema** **al** **completamento**: Il nuovo utente è stato registrato nel sistema.
