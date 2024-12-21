package code;

public class App {
    public static ApiResponse responseTravaux;
    public static ApiResponse responseEntraves;


    public static void main(String[] args) {
        HttpClientApi api = new HttpClientApi();

        String resourceIdTravaux = "cc41b532-f12d-40fb-9f55-eb58c9a2b12b"; // Remplacez par l'ID de la ressource

        responseTravaux = api.getData(resourceIdTravaux);

        String resourceIdEntraves = "a2bc8014-488c-495d-941b-e7ae1999d1bd"; // Remplacez par l'ID de la ressource

        responseEntraves = api.getData(resourceIdEntraves);

    }
}