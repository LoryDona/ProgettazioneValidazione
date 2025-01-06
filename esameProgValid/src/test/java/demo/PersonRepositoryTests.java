package demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("Test di salvataggio e recupero di una persona")
    public void testSaveAndFindById() {
        Person person = new Person("Mario", "Rossi", "password123", "user");
        Person savedPerson = personRepository.save(person);

        Optional<Person> retrievedPersonOpt = personRepository.findById(savedPerson.getId());

        assert(retrievedPersonOpt).isPresent();
        Person retrievedPerson = retrievedPersonOpt.get();
        assertThat(retrievedPerson.getFirstName()).isEqualTo("Mario");
        assertThat(retrievedPerson.getLastName()).isEqualTo("Rossi");
        assertThat(retrievedPerson.getRole()).isEqualTo("user");
    }

    @Test
    @DisplayName("Test di ricerca per cognome")
    public void testFindByLastName() {

        Person person1 = new Person("Luca", "Bianchi", "pass1", "ADMIN");
        Person person2 = new Person("Anna", "Bianchi", "pass2", "USER");
        personRepository.save(person1);
        personRepository.save(person2);

        List<Person> bianchiList = personRepository.findByLastName("Bianchi");

        assertThat(bianchiList).hasSize(2)
                .extracting(Person::getFirstName)
                .containsExactlyInAnyOrder("Luca", "Anna");
    }

    @Test
    @DisplayName("Test di ricerca per ruolo")
    public void testFindByRole() {
        Person person1 = new Person("Giovanni", "Verdi", "pass3", "ADMIN");
        Person person2 = new Person("Elena", "Neri", "pass4", "USER");
        Person person3 = new Person("Marco", "Gialli", "pass5", "ADMIN");
        personRepository.save(person1);
        personRepository.save(person2);
        personRepository.save(person3);

        List<Person> adminList = personRepository.findByRole("ADMIN");

        assertThat(adminList).hasSize(2)
                .extracting(Person::getFirstName)
                .containsExactlyInAnyOrder("Giovanni", "Marco");
    }

    @Test
    @DisplayName("Test di aggiornamento di una persona")
    public void testUpdatePerson() {
        Person person = new Person("Laura", "Rossi", "initialPass", "USER");
        Person savedPerson = personRepository.save(person);

        savedPerson.setPassword("newPassword");
        Person updatedPerson = personRepository.save(savedPerson);

        Optional<Person> retrievedPersonOpt = personRepository.findById(updatedPerson.getId());
        assertThat(retrievedPersonOpt).isPresent();
        assertThat(retrievedPersonOpt.get().getPassword()).isEqualTo("newPassword");
    }

    @Test
    @DisplayName("Test di eliminazione di una persona")
    public void testDeletePerson() {
        Person person = new Person("Stefano", "Blu", "passwordBlu", "USER");
        Person savedPerson = personRepository.save(person);

        personRepository.delete(savedPerson);

        Optional<Person> retrievedPersonOpt = personRepository.findById(savedPerson.getId());
        assertThat(retrievedPersonOpt).isNotPresent();
    }

    @Test
    @DisplayName("Test di ricerca per ID non esistente")
    public void testFindByIdNotFound() {
        Optional<Person> retrievedPersonOpt = Optional.ofNullable(personRepository.findById(999L));

        assertThat(retrievedPersonOpt).isNotPresent();
    }
}
