
import code.App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

import java.util.Scanner;
public class Main extends JFrame {
    public static void main(String[] args) {
        //App.main(args);
        //choixUtilisateur();
        initialisation();
    }

    public static void initialisation(){
        // Permet de créer la fenêtre de l'ouverture de l'application
        JFrame jframe = Frame.nouveauFrame("MaVille");

        // Insertion du en tête où on met le titre du panel
        JPanel panelInitialisation = new JPanel();
        panelInitialisation.setLayout(new BorderLayout());
        JLabel bienvenue = new JLabel("Bienvenue à MaVille!", SwingConstants.CENTER);
        bienvenue.setFont(bienvenue.getFont().deriveFont(30f));
        panelInitialisation.add(bienvenue, BorderLayout.NORTH);

        //Insertion du panel où l'utilisateur choisi une option
        JPanel panelChoix = new JPanel();
        panelChoix.setLayout(new BoxLayout(panelChoix, BoxLayout.Y_AXIS));
        JLabel choix = new JLabel("Je suis un: ", SwingConstants.CENTER);
        choix.setFont(choix.getFont().deriveFont(20f));
        choix.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelChoix.add(choix);

        //Création des boutons pour cliquer sur une option
        JButton resident = new JButton("Resident");
        resident.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelChoix.add(resident);
        JButton intervenant = new JButton("Intervenant");
        intervenant.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelChoix.add(intervenant);
        panelInitialisation.add(panelChoix, BorderLayout.CENTER);

        //Création d'un pied de page pour la création d'un compte
        JPanel panelPied = new JPanel();
        panelPied.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel nouveauUtilisateur = new JLabel("Vous êtes nouveau? ");
        nouveauUtilisateur.setFont(choix.getFont().deriveFont(20f));
        panelPied.add(nouveauUtilisateur);

        JButton creerCompte = new JButton("Créer un compte maintenant!");
        panelPied.add(creerCompte);
        panelInitialisation.add(panelPied, BorderLayout.SOUTH);

        jframe.add(panelInitialisation);

        resident.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               jframe.dispose();
               Resident.connexion();
           }
        });





        jframe.setVisible(true);
    }
    /*public static void initialisationTest(){
        App.main(new String[0]);
    }*/
}