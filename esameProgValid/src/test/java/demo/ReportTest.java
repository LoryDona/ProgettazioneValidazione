package demo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReportTest {

    @Test
    @DisplayName("Test del costruttore con parametri")
    void testConstructorWithParameters() {
        // Arrange
        String title = "Report Titolo";
        String results = "Risultati dettagliati";
        String hours = "10";
        String activities = "Attività svolte";
        String firma = "Firma Digitale";
        String progetto = "Progetto XYZ";
        boolean isbozza = true;
        boolean controfirma = false;

        // Act
        Report report = new Report(title, results, hours, activities, firma, progetto, isbozza, controfirma);

        // Assert
        assertThat(report.getTitle()).isEqualTo(title);
        assertThat(report.getResults()).isEqualTo(results);
        assertThat(report.getHours()).isEqualTo(hours);
        assertThat(report.getActivities()).isEqualTo(activities);
        assertThat(report.getFirma()).isEqualTo(firma);
        assertThat(report.getProgetto()).isEqualTo(progetto);
        assertThat(report.getIsBozza()).isEqualTo(isbozza);
        assertThat(report.getControfirma()).isEqualTo(controfirma);
    }

    @Test
    @DisplayName("Test del costruttore di default")
    void testDefaultConstructor() {
        // Arrange & Act
        Report report = new Report();

        // Assert
        assertThat(report.getTitle()).isNull();
        assertThat(report.getResults()).isNull();
        assertThat(report.getHours()).isNull();
        assertThat(report.getActivities()).isNull();
        assertThat(report.getFirma()).isNull();
        assertThat(report.getProgetto()).isNull();
        assertThat(report.getIsBozza()).isFalse();
        assertThat(report.getControfirma()).isFalse();
    }

    @Test
    @DisplayName("Test dei getter e setter di isbozza")
    void testGetSetIsBozza() {
        // Arrange
        Report report = new Report();
        boolean initialBozza = false;
        boolean newBozza = true;

        // Act
        report.setIsBozza(initialBozza);
        boolean retrievedBozza1 = report.getIsBozza();
        report.setIsBozza(newBozza);
        boolean retrievedBozza2 = report.getIsBozza();

        // Assert
        assertThat(retrievedBozza1).isEqualTo(initialBozza);
        assertThat(retrievedBozza2).isEqualTo(newBozza);
    }

    @Test
    @DisplayName("Test del metodo toString")
    void testToString() {
        Report report = new Report("Titolo", "Risultati", "8", "Attività", "Firma", "Progetto", true, false);

        String expectedString = "Risultati:\nRisultati\nOre: 8\nActivities:\nAttività\nFirma: Firma\nProgetto: Progetto";
        String actualString = report.toString();

        assertThat(actualString).isEqualTo(expectedString);
    }


    private void setId(Report report, long id) {
        try {
            java.lang.reflect.Field idField = Report.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.setLong(report, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
