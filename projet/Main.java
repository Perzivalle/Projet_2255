import code.App;
import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        App.main(args);
        choixUtilisateur();
    }

    public static void choixUtilisateur() {
        Scanner scanner = new Scanner(System.in);
        String connexion = "";

        while(!connexion.equals("1") && !connexion.equals("0")) {
            System.out.println("Bienvenue dans MaVille !");
            System.out.println("Se connecter en tant que résident en insérant 1");
            System.out.println("Se connecter en tant qu'intervenant en insérant 0");
            connexion = scanner.next();
            if (connexion.equals("1")) {
                Resident resident = new Resident();
                Resident.connexion();
            } else if (connexion.equals("0")) {
                Intervenant intervenant = new Intervenant();
                Intervenant.connexion();
            } else {
                System.out.println("Veuillez insérer 1 ou 0");
            }
        }

    }

    public static void initialisationTest() {
        App.main(new String[0]);
    }
}
