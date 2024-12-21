import code.App;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.Scanner;

public class consulterEntraves {
    public consulterEntraves() {
    }

    public static void consulterEntraves() {
        if (App.responseEntraves != null && App.responseEntraves.getStatusCode() == 200) {
            String jsonResponseEntraves = App.responseEntraves.getBody();
            listeEntraves(jsonResponseEntraves);
            Scanner scanner = new Scanner(System.in);
            String choix = "";

            while(!choix.equals("1") && !choix.equals("2")) {
                System.out.println("1) Filtrer par travail ");
                System.out.println("2) Revenir page principale");
                choix = scanner.nextLine();
                if (choix.equals("1")) {
                    filtreTravail(jsonResponseEntraves);
                } else if (choix.equals("2")) {
                    Resident.menu();
                }
            }

        } else {
            System.out.println("Erreur de données d'entraves");
        }
    }

    public static void listeEntraves(String jsonResponseEntraves) {
        Gson gson = new Gson();
        JsonObject jsonObject = (JsonObject)gson.fromJson(jsonResponseEntraves, JsonObject.class);
        JsonArray records = jsonObject.getAsJsonObject("result").getAsJsonArray("records");
        Iterator var4 = records.iterator();

        while(var4.hasNext()) {
            JsonElement record = (JsonElement)var4.next();
            JsonObject obj = record.getAsJsonObject();
            String idRequest = obj.get("id_request").getAsString();
            String streetId = obj.get("streetid").getAsString();
            String shortName = obj.get("shortname").getAsString();
            String streetImpactType = obj.get("streetimpacttype").getAsString();
            System.out.println(idRequest);
            System.out.println(streetId);
            System.out.println(shortName);
            System.out.println(streetImpactType);
        }

    }

    public static void filtreTravail(String jsonResponseEntraves) {
        Gson gson = new Gson();
        JsonObject jsonObject = (JsonObject)gson.fromJson(jsonResponseEntraves, JsonObject.class);
        JsonArray records = jsonObject.getAsJsonObject("result").getAsJsonArray("records");
        Scanner scanner = new Scanner(System.in);
        String choixId = "";

        while(true) {
            System.out.println("Veuillez tapez le id_request ou 'Retour' pour retourner au menu");
            choixId = scanner.next();
            if (choixId.equals("Retour")) {
                Resident.menu();
            }

            boolean valide = false;
            Iterator var7 = records.iterator();

            while(var7.hasNext()) {
                JsonElement record = (JsonElement)var7.next();
                JsonObject obj = record.getAsJsonObject();
                String idRequest = obj.get("id_request").getAsString();
                if (idRequest.equals(choixId)) {
                    String streetId = obj.get("streetid").getAsString();
                    String shortName = obj.get("shortname").getAsString();
                    String streetImpactType = obj.get("streetimpacttype").getAsString();
                    System.out.println(idRequest);
                    System.out.println(streetId);
                    System.out.println(shortName);
                    System.out.println(streetImpactType);
                    valide = true;
                }
            }

            if (!valide) {
                System.out.println("id_request invalide");
            }
        }
    }
}
