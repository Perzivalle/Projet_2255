import code.App;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.Scanner;

public class consulterTravaux {
    public consulterTravaux() {
    }

    public static void consulter() {
        if (App.responseTravaux != null && App.responseTravaux.getStatusCode() == 200) {
            String jsonResponseTravaux = App.responseTravaux.getBody();
            listeTravaux(jsonResponseTravaux);
            Scanner scanner = new Scanner(System.in);
            String choix = "";

            while(!choix.equals("1") && !choix.equals("2")) {
                System.out.println("1) Filtrer par travail ");
                System.out.println("2) Revenir page principale");
                choix = scanner.nextLine();
                if (choix.equals("1")) {
                    filtreTravail(jsonResponseTravaux);
                } else if (choix.equals("2")) {
                    Resident.menu();
                }
            }

        } else {
            System.out.println("Erreur de données d'entraves");
        }
    }

    public static void listeTravaux(String jsonResponseTravaux) {
        Gson gson = new Gson();
        JsonObject jsonObject = (JsonObject)gson.fromJson(jsonResponseTravaux, JsonObject.class);
        JsonArray records = jsonObject.getAsJsonObject("result").getAsJsonArray("records");
        Iterator var4 = records.iterator();

        while(var4.hasNext()) {
            JsonElement record = (JsonElement)var4.next();
            JsonObject obj = record.getAsJsonObject();
            String id = obj.has("id") && !obj.get("id").isJsonNull() ? obj.get("id").getAsString() : "Indisponible";
            String boroughId = obj.has("boroughid") && !obj.get("boroughid").isJsonNull() ? obj.get("boroughid").getAsString() : "Indisponible";
            String currentStatus = obj.has("currentstatus") && !obj.get("currentstatus").isJsonNull() ? obj.get("currentstatus").getAsString() : "Indisponible";
            String reasonCategory = obj.has("reason_category") && !obj.get("reason_category").isJsonNull() ? obj.get("reason_category").getAsString() : "Indisponible";
            String submitterCategory = obj.has("submittercategory") && !obj.get("submittercategory").isJsonNull() ? obj.get("submittercategory").getAsString() : "Indisponible";
            String organizationName = obj.has("organizationname") && !obj.get("organizationname").isJsonNull() ? obj.get("organizationname").getAsString() : "Indisponible";
            System.out.println(id);
            System.out.println(boroughId);
            System.out.println(currentStatus);
            System.out.println(reasonCategory);
            System.out.println(submitterCategory);
            System.out.println(organizationName);
        }

    }

    public static void filtreTravail(String jsonResponseTravaux) {
        Gson gson = new Gson();
        JsonObject jsonObject = (JsonObject)gson.fromJson(jsonResponseTravaux, JsonObject.class);
        JsonArray records = jsonObject.getAsJsonObject("result").getAsJsonArray("records");
        Scanner scanner = new Scanner(System.in);
        String choixId = "";

        label81:
        while(true) {
            System.out.println("Veuillez tapez le id ou 'Retour' pour retourner au menu");
            choixId = scanner.next();
            if (choixId.equals("Retour")) {
                Resident.menu();
                return;
            }

            boolean valide = false;
            Iterator var7 = records.iterator();

            while(true) {
                JsonObject obj;
                String id;
                do {
                    if (!var7.hasNext()) {
                        if (!valide) {
                            System.out.println("id invalide");
                        }
                        continue label81;
                    }

                    JsonElement record = (JsonElement)var7.next();
                    obj = record.getAsJsonObject();
                    id = obj.has("id") && !obj.get("id").isJsonNull() ? obj.get("id").getAsString() : "Indisponible";
                } while(!id.equals(choixId));

                String boroughId = obj.has("boroughid") && !obj.get("boroughid").isJsonNull() ? obj.get("boroughid").getAsString() : "Indisponible";
                String currentStatus = obj.has("currentstatus") && !obj.get("currentstatus").isJsonNull() ? obj.get("currentstatus").getAsString() : "Indisponible";
                String reasonCategory = obj.has("reason_category") && !obj.get("reason_category").isJsonNull() ? obj.get("reason_category").getAsString() : "Indisponible";
                String submitterCategory = obj.has("submittercategory") && !obj.get("submittercategory").isJsonNull() ? obj.get("submittercategory").getAsString() : "Indisponible";
                String organizationName = obj.has("organizationname") && !obj.get("organizationname").isJsonNull() ? obj.get("organizationname").getAsString() : "Indisponible";
                System.out.println(id);
                System.out.println(boroughId);
                System.out.println(currentStatus);
                System.out.println(reasonCategory);
                System.out.println(submitterCategory);
                System.out.println(organizationName);
                valide = true;
            }
        }
    }
}
