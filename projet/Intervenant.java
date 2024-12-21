import java.util.HashMap;
import java.util.Scanner;

public class Intervenant {
    private static String courriel;
    public static HashMap<String, String> comptesIntervenant = new HashMap();

    public Intervenant() {
    }

    public static void connexion() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Connexion");
            System.out.println("Veuillez insérer votre courriel");
            System.out.println("Taper 'Retour' pour revenir au choix d'utilisateur");
            courriel = scanner.next();
            if (comptesIntervenant.containsKey(courriel)) {
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
            if (((String)comptesIntervenant.get(courriel)).equals(passe)) {
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
            System.out.println("1) Gestion des chantiers");
            System.out.println("2) Soumettre un nouveau projet de travaux");
            System.out.println("3) Consulter la liste des requêtes de travail");
            System.out.println("4) Se déconnecter");
            optionMenu = scanner.next();
            if (optionMenu.equals("1")) {
                System.out.println("Vous etes sur la page gestion des chantiers");
            } else if (optionMenu.equals("2")) {
                System.out.println("Vous etes sur la page soumettre un nouveau projet de travaux");
            } else if (optionMenu.equals("3")) {
                consulterRequete.consulterRequete();
            } else if (optionMenu.equals("4")) {
                connexion();
            } else {
                System.out.println("Veuillez entrer un numéro valide");
            }
        }

    }

    static {
        comptesIntervenant.put("lucas@intervenant.com", "Hello2024");
        comptesIntervenant.put("georges@intervenant.com", "Goodbye2024");
        comptesIntervenant.put("charles@intervenant.com", "After2024");
        comptesIntervenant.put("rose@intervenant.com", "Late2024");
    }
}
