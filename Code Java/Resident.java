import jdk.management.jfr.FlightRecorderMXBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.HashMap;

public class Resident {
    private static String courriel;
    public static HashMap<String, String> comptesResident = new HashMap<>();
    static {
        comptesResident.put("robert@hotmail.com", "Allo2024");
        comptesResident.put("alexandre@hotmail.com", "Revoir2024");
        comptesResident.put("emilia@hotmail.com", "Tantot2024");
        comptesResident.put("david@hotmail.com", "Tard2024");
    }
    //utilisateur doit se connecter à son compte
    public static void connexion() {
        JFrame frameConnexionResident = Frame.nouveauFrame("MaVille: Connexion");
        frameConnexionResident.setVisible(true);
        // Panel pour insérer le titre du frame
        JPanel panelConnexionResident = new JPanel(new BorderLayout());

        JPanel panelHead = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titreConnexionResident = new JLabel("Page de connexion");
        titreConnexionResident.setFont(titreConnexionResident.getFont().deriveFont(30f));
        panelHead.add(titreConnexionResident);

        //Bouton pour revenir à la fenêtre précédente
        JPanel boutonRetour = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton retour = new JButton("Retour");
        boutonRetour.add(retour);
        panelHead.add(boutonRetour);
        panelConnexionResident.add(panelHead, BorderLayout.NORTH);

        JPanel panelBody = new JPanel();
        panelBody.setLayout(new BoxLayout(panelBody, BoxLayout.Y_AXIS));

        // Panel pour insérer le courriel du résident
        JPanel panelCourrielResident = new JPanel();
        panelCourrielResident.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel courrielResident = new JLabel("Courriel", SwingConstants.CENTER);
        courrielResident.setFont(courrielResident.getFont().deriveFont(25f));
        panelCourrielResident.add(courrielResident);
        JTextField inputCourrielResident = new JTextField(20);
        inputCourrielResident.setPreferredSize(new Dimension(300, 30));
        panelCourrielResident.add(inputCourrielResident);
        panelBody.add(panelCourrielResident);

        // Panel pour insérer le mot de passe du résident
        JPanel panelMdpResident = new JPanel();
        panelMdpResident.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel mdpResident = new JLabel("Mot de passe", SwingConstants.CENTER);
        mdpResident.setFont(mdpResident.getFont().deriveFont(25f));
        panelMdpResident.add(mdpResident);
        JPasswordField inputMdpResident = new JPasswordField(20);
        inputMdpResident.setPreferredSize(new Dimension(300, 30));
        panelMdpResident.add(inputMdpResident);
        panelBody.add(panelMdpResident);
        panelConnexionResident.add(panelBody, BorderLayout.CENTER);

        JPanel panelPied = new JPanel();
        panelPied.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton confirmerCompteResident = new JButton("Se connecter");
        panelPied.add(confirmerCompteResident);
        panelConnexionResident.add(panelPied,BorderLayout.SOUTH);

        JLabel messageLabel = new JLabel();
        messageLabel.setBounds(50,200,300,25);
        frameConnexionResident.add(messageLabel);

        confirmerCompteResident.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courriel = inputCourrielResident.getText();
                char[] mdp = inputMdpResident.getPassword();

                if (comptesResident.containsKey(courriel)){
                    String mdpEnregistrer = comptesResident.get(courriel);
                    if (validerMdp(mdp, mdpEnregistrer.toCharArray())){
                        frameConnexionResident.dispose();
                        Resident.menu();
                    } else {
                        messageLabel.setText("mdp invalide");
                    }
                }else {
                    messageLabel.setText("courriel invalide");
                }

            }
        });

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameConnexionResident.dispose();
                Main.initialisation();
            }
        });

        frameConnexionResident.add(panelConnexionResident);


    }

    private static boolean validerMdp(char[] mdp, char[] mdpEnregistrer){
        for (int i = 0; i < mdp.length; i++){
            if (mdp[i]!=mdpEnregistrer[i]){
                return false;
            }
        }
        return true;
    }


    // page principale avec toutes les différentes options
    public static void menu(){
        JFrame frameMenuResident = Frame.nouveauFrame("MaVille: Menu");
        frameMenuResident.setVisible(true);




        /*while (!optionMenu.equals("1") && !optionMenu.equals("2") && !optionMenu.equals("3") && !optionMenu.equals("4") && !optionMenu.equals("5")) {
            System.out.println("Veuillez choisir un numéro parmi les options");
            System.out.println("1) Rechercher des travaux");
            System.out.println("2) Consulter les travaux en cours ou à venir");
            System.out.println("3) Soumettre une requête de travail");
            System.out.println("4) Recevoir des notifications personnalisées");
            System.out.println("5) Consulter les entraves routières causées par les travaux en cours");
            System.out.println("6) Se déconnecter");

            optionMenu = scanner.next();
            if (optionMenu.equals("1")) {
                //rechercheTravaux();
            } else if (optionMenu.equals("2")) {
                consulterTravaux.consulter();
            } else if (optionMenu.equals("3")) {
                soumettreTravaux.soumettreRequete();
            } else if (optionMenu.equals("4")) {
                //recevoirNotification();
            } else if (optionMenu.equals("5")) {
                consulterEntraves.consulterEntraves();
            } else if (optionMenu.equals("6")) {
                connexion();
            } else {
                System.out.println("Veuillez entrer un numéro valide");
            }
        }*/

    }



















    /*public static void rechercheTravaux(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vous recherchez travaux");
    }

    public static void recevoirNotification(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vous recevez notification");
    }*/

}
