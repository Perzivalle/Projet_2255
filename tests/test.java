import code.ApiResponse;
import code.HttpClientApi;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class test {
    public test() {
    }

    @BeforeAll
    public static void initialisation() {
        String simulation = "1\nrobert@hotmail.com\nAllo2024\n";
        System.setIn(new ByteArrayInputStream(simulation.getBytes()));
        Main.initialisationTest();
    }

    @Test
    public void testComptesResidents() {
        String courriel = "robert@hotmail.com";
        String motDePasse = "Allo2024";
        Assertions.assertTrue(Resident.comptesResident.containsKey(courriel) && ((String)Resident.comptesResident.get(courriel)).equals(motDePasse));
    }

    @Test
    public void testAjouterResident() {
        Resident.comptesResident.put("jeantalon@hotmail.com", "Metro2024");
        Assertions.assertTrue(Resident.comptesResident.containsKey("jeantalon@hotmail.com"));
    }

    @Test
    public void testConnexionInvalide() {
        String courriel = "robert@hotmail.com";
        String motDePasse = "Allo1111";
        Assertions.assertFalse(Resident.comptesResident.containsKey(courriel) && ((String)Resident.comptesResident.get(courriel)).equals(motDePasse));
    }

    @Test
    public void testCourrielValide() {
        String courriel = "lucas@intervenant.com";
        Assertions.assertTrue(Intervenant.comptesIntervenant.containsKey(courriel));
    }

    @Test
    public void testComptesIntervenants() {
        String courriel = "charles@intervenant.com";
        String motDePasse = "After2024";
        Assertions.assertTrue(Intervenant.comptesIntervenant.containsKey(courriel) && ((String)Intervenant.comptesIntervenant.get(courriel)).equals(motDePasse));
    }

    @Test
    public void testAPIValidité() {
        HttpClientApi api = new HttpClientApi();
        String resourceIdTravaux = "cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
        ApiResponse testApiReponse = api.getData(resourceIdTravaux);
        Assertions.assertEquals(200, testApiReponse.getStatusCode());
    }

    @Test
    public void testAPIBody() {
        HttpClientApi api = new HttpClientApi();
        String resourceIdTravaux = "cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
        ApiResponse testApiBody = api.getData(resourceIdTravaux);
        Assertions.assertNotNull(testApiBody.getBody());
    }

    @Test
    public void testAPIMessage() {
        HttpClientApi api = new HttpClientApi();
        String resourceIdTravaux = "cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
        ApiResponse testApiMessage = api.getData(resourceIdTravaux);
        Assertions.assertNotNull(testApiMessage.getMessage());
    }

    @Test
    public void testRequeteTravail() {
        soumettreTravaux.informationRequete.add("Éclairage");
        soumettreTravaux.informationRequete.add("2024");
        soumettreTravaux.informationRequete.add("11");
        soumettreTravaux.informationRequete.add("25");
        soumettreTravaux.informationRequete.add("Réparation éclairage rue");
        Assertions.assertEquals(5, soumettreTravaux.informationRequete.size());
    }
}
