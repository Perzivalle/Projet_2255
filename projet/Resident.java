import java.util.HashMap;
import java.util.Scanner;

public class Resident {
    private static String courriel;
    public static HashMap<String, String> comptesResident = new HashMap();

    public Resident() {
    }

    public static void connexion() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Connexion");
            System.out.println("Veuillez insérer votre courriel");
            System.out.println("Taper 'Retour' pour revenir au choix d'utilisateur");
            courriel = scanner.next();
            if (comptesResident.containsKey(courriel)) {
                motDePasse();
                return;
            }

            if (courriel.equals("Retour")) {
                Main.choixUtilisateur();
                return;
            }

            System.out.println("Veuillez entrer un courriel valide");
        }
    }

    public static void motDePasse() {
        Scanner scanner = new Scanner(System.in);
        String passe = "";

        while(true) {
            System.out.println("Veuillez insérer votre mot de passe");
            System.out.println("Taper 'Retour' pour revenir à la connexion");
            System.out.println("Taper 'Oublier' si vous avez oublier votre mot de passe");
            passe = scanner.next();
            if (((String)comptesResident.get(courriel)).equals(passe)) {
                menu();
                return;
            }

            if (passe.equals("Retour")) {
                connexion();
                return;
            }

            if (passe.equals("Oublier")) {
                System.out.println("Veuillez insérer votre courriel pour changer votre mot de passe");
            } else {
                System.out.println("Veuillez entrer un mot de passe valide");
            }
        }
    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        String optionMenu = "";

        while(!optionMenu.equals("1") && !optionMenu.equals("2") && !optionMenu.equals("3") && !optionMenu.equals("4") && !optionMenu.equals("5")) {
            System.out.println("Veuillez choisir un numéro parmi les options");
            System.out.println("1) Rechercher des travaux");
            System.out.println("2) Consulter les travaux en cours ou à venir");
            System.out.println("3) Soumettre une requête de travail");
            System.out.println("4) Recevoir des notifications personnalisées");
            System.out.println("5) Consulter les entraves routières causées par les travaux en cours");
            System.out.println("6) Se déconnecter");
            optionMenu = scanner.next();
            if (optionMenu.equals("1")) {
                rechercheTravaux();
            } else if (optionMenu.equals("2")) {
                consulterTravaux.consulter();
            } else if (optionMenu.equals("3")) {
                soumettreTravaux.soumettreRequete();
            } else if (optionMenu.equals("4")) {
                recevoirNotification();
            } else if (optionMenu.equals("5")) {
                consulterEntraves.consulterEntraves();
            } else if (optionMenu.equals("6")) {
                connexion();
            } else {
                System.out.println("Veuillez entrer un numéro valide");
            }
        }

    }

    public static void rechercheTravaux() {
        new Scanner(System.in);
        System.out.println("vous recherchez travaux");
    }

    public static void recevoirNotification() {
        new Scanner(System.in);
        System.out.println("vous recevez notification");
    }

    static {
        comptesResident.put("robert@hotmail.com", "Allo2024");
        comptesResident.put("alexandre@hotmail.com", "Revoir2024");
        comptesResident.put("emilia@hotmail.com", "Tantot2024");
        comptesResident.put("david@hotmail.com", "Tard2024");
    }
}
