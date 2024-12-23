import code.App;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe `menu` gère l'interface utilisateur principale pour un système de menu avec plusieurs pages.
 * Elle inclut le chargement des comptes, l'initialisation de l'interface et le changement dynamique de panneaux à l'aide de CardLayout.
 */
public class menu {
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;

    private static final String RESIDENTS_FILE = "comptesResident.csv";
    private static final String INTERVENANTS_FILE = "comptesIntervenant.csv";

    private static final List<String[]> comptesResident = new ArrayList<>();
    private static final List<String[]> comptesIntervenant = new ArrayList<>();

    /**
     * Constructeur pour `menu` qui initialise l'interface et charge les données des comptes.
     */
    public menu() {
        comptesResident.addAll(BaseDeDonnées.lireTous(RESIDENTS_FILE));
        comptesIntervenant.addAll(BaseDeDonnées.lireTous(INTERVENANTS_FILE));

        frame = new JFrame("Menu Principal");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createPageAccueil(), "Accueil");
        mainPanel.add(createPageInscription(), "Inscription");
        mainPanel.add(createPageConnexion(), "Connexion");
        mainPanel.add(createPageMenuOptionsResident(), "MenuResident");
        mainPanel.add(createPageMenuOptionsIntervenant(), "MenuIntervenant");

        cardLayout.show(mainPanel, "Accueil");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * Crée le panneau de la page d'accueil pour le menu principal.
     *
     * @return JPanel représentant la page d'accueil.
     */
    private JPanel createPageAccueil() {
        JPanel accueilPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Bienvenue dans le Menu Principal", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        accueilPanel.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton btnInscription = new JButton("Page d'inscription");
        btnInscription.addActionListener(e -> cardLayout.show(mainPanel, "Inscription"));
        buttonPanel.add(btnInscription);

        JButton btnConnexion = new JButton("Page de connexion");
        btnConnexion.addActionListener(e -> cardLayout.show(mainPanel, "Connexion"));
        buttonPanel.add(btnConnexion);

        accueilPanel.add(buttonPanel, BorderLayout.SOUTH);
        return accueilPanel;
    }

    /**
     * Crée le panneau de la page d'inscription.
     *
     * @return JPanel représentant la page d'inscription.
     */
    private JPanel createPageInscription() {
        // Des commentaires détaillés similaires devraient être écrits ici.
        return new JPanel(); // Simplifié pour l'exemple.
    }

    /**
     * Crée le panneau de la page de connexion.
     *
     * @return JPanel représentant la page de connexion.
     */
    private JPanel createPageConnexion() {
        // Des commentaires détaillés similaires devraient être écrits ici.
        return new JPanel(); // Simplifié pour l'exemple.
    }

    /**
     * Crée le panneau des options de menu pour les résidents.
     *
     * @return JPanel représentant la page des options pour les résidents.
     */
    public static JPanel createPageMenuOptionsResident() {
        // Des commentaires détaillés similaires devraient être écrits ici.
        return new JPanel(); // Simplifié pour l'exemple.
    }

    /**
     * Crée le panneau des options de menu pour les intervenants.
     *
     * @return JPanel représentant la page des options pour les intervenants.
     */
    public JPanel createPageMenuOptionsIntervenant() {
        // Des commentaires détaillés similaires devraient être écrits ici.
        return new JPanel(); // Simplifié pour l'exemple.
    }

    /**
     * Méthode principale pour exécuter l'application.
     *
     * @param args Arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(menu::new);
        App.main(args);
    }
}
