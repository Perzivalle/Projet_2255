import code.App;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale de l'application qui gère le menu principal et les pages associées.
 * Elle utilise un {@link CardLayout} pour naviguer entre les différentes sections.
 */
public class Main {

    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;

    private static final String RESIDENTS_FILE = "comptesResident.csv";
    private static final String INTERVENANTS_FILE = "comptesIntervenant.csv";

    private static final List<String[]> comptesResident = new ArrayList<>();
    private static final List<String[]> comptesIntervenant = new ArrayList<>();

    /**
     * Constructeur principal de la classe {@code Main} qui initialise l'interface graphique
     * et charge les données des comptes depuis les fichiers CSV.
     */
    public Main() {
        // Charger les comptes depuis les fichiers
        comptesResident.addAll(BaseDeDonnées.lireTous(RESIDENTS_FILE));
        comptesIntervenant.addAll(BaseDeDonnées.lireTous(INTERVENANTS_FILE));

        // Initialisation de la fenêtre principale
        frame = new JFrame("Menu Principal");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialisation du CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Ajouter les pages au CardLayout
        mainPanel.add(createPageAccueil(), "Accueil");
        mainPanel.add(createPageInscription(), "Inscription");
        mainPanel.add(createPageConnexion(), "Connexion");
        mainPanel.add(createPageMenuOptionsResident(), "MenuResident");
        mainPanel.add(createPageMenuOptionsIntervenant(), "MenuIntervenant");

        // Afficher la page d'accueil par défaut
        cardLayout.show(mainPanel, "Accueil");

        // Ajouter le panneau principal à la fenêtre
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * Crée la page d'accueil du menu principal.
     *
     * @return Un {@link JPanel} représentant la page d'accueil.
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
     * Crée la page d'inscription.
     *
     * @return Un {@link JPanel} représentant la page d'inscription.
     */
    private JPanel createPageInscription() {
        JPanel panelMain = new JPanel(new BorderLayout());

        JLabel titre = new JLabel("Page d'inscription", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        panelMain.add(titre, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Ligne : Type de compte
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(new JLabel("Type de compte :"), gbc);

        gbc.gridx = 1;
        JComboBox<String> comboBoxTypeCompte = new JComboBox<>(new String[]{"Résident", "Intervenant"});
        panelForm.add(comboBoxTypeCompte, gbc);

        // Champs communs (Courriel et Mot de passe)
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelForm.add(new JLabel("Adresse courriel :"), gbc);

        gbc.gridx = 1;
        JTextField inputEmail = new JTextField(20);
        panelForm.add(inputEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(new JLabel("Mot de passe :"), gbc);

        gbc.gridx = 1;
        JPasswordField inputMdp = new JPasswordField(20);
        panelForm.add(inputMdp, gbc);

        // Champs Résident
        JPanel panelResident = new JPanel(new GridBagLayout());

        GridBagConstraints gbcRes = new GridBagConstraints();
        gbcRes.insets = new Insets(5, 5, 5, 5);
        gbcRes.anchor = GridBagConstraints.WEST;

        gbcRes.gridx = 0;
        gbcRes.gridy = 0;
        panelResident.add(new JLabel("Nom complet :"), gbcRes);

        gbcRes.gridx = 1;
        JTextField inputNomComplet = new JTextField(20);
        panelResident.add(inputNomComplet, gbcRes);

        gbcRes.gridx = 0;
        gbcRes.gridy = 1;
        panelResident.add(new JLabel("Date de naissance (YYYY-MM-DD) :"), gbcRes);

        gbcRes.gridx = 1;
        JTextField inputDateNaissance = new JTextField(20);
        panelResident.add(inputDateNaissance, gbcRes);

        gbcRes.gridx = 0;
        gbcRes.gridy = 2;
        panelResident.add(new JLabel("Adresse résidentielle :"), gbcRes);

        gbcRes.gridx = 1;
        JTextField inputAdresse = new JTextField(20);
        panelResident.add(inputAdresse, gbcRes);

        // Champs Intervenant
        JPanel panelIntervenant = new JPanel(new GridBagLayout());

        GridBagConstraints gbcInt = new GridBagConstraints();
        gbcInt.insets = new Insets(5, 5, 5, 5);
        gbcInt.anchor = GridBagConstraints.WEST;

        gbcInt.gridx = 0;
        gbcInt.gridy = 0;
        panelIntervenant.add(new JLabel("Nom complet :"), gbcInt);

        gbcInt.gridx = 1;
        JTextField inputNomIntervenant = new JTextField(20);
        panelIntervenant.add(inputNomIntervenant, gbcInt);

        gbcInt.gridx = 0;
        gbcInt.gridy = 1;
        panelIntervenant.add(new JLabel("Type d'intervenant :"), gbcInt);

        gbcInt.gridx = 1;
        JTextField inputTypeIntervenant = new JTextField(20);
        panelIntervenant.add(inputTypeIntervenant, gbcInt);

        gbcInt.gridx = 0;
        gbcInt.gridy = 2;
        panelIntervenant.add(new JLabel("Identifiant de la ville (8 chiffres) :"), gbcInt);

        gbcInt.gridx = 1;
        JTextField inputIdVille = new JTextField(20);
        panelIntervenant.add(inputIdVille, gbcInt);

        // Ajouter les panels dynamiques
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelForm.add(panelResident, gbc);
        panelForm.add(panelIntervenant, gbc);

        panelResident.setVisible(true);
        panelIntervenant.setVisible(false);

        // Gestion dynamique de la visibilité
        comboBoxTypeCompte.addActionListener(e -> {
            boolean isResident = "Résident".equals(comboBoxTypeCompte.getSelectedItem());
            panelResident.setVisible(isResident);
            panelIntervenant.setVisible(!isResident);
        });

        // Footer
        JPanel panelFooter = new JPanel(new FlowLayout());
        JButton btnInscription = new JButton("S'inscrire");
        JButton btnRetour = new JButton("Retour");
        panelFooter.add(btnRetour);
        panelFooter.add(btnInscription);

        btnRetour.addActionListener(e -> cardLayout.show(mainPanel, "Accueil"));

        btnInscription.addActionListener(e -> {
            if ("Résident".equals(comboBoxTypeCompte.getSelectedItem())) {
                String[] donneesResident = {inputEmail.getText(), new String(inputMdp.getPassword()), inputNomComplet.getText(), inputDateNaissance.getText(), inputAdresse.getText()};
                comptesResident.add(donneesResident);
                BaseDeDonnées.réécrire(RESIDENTS_FILE, comptesResident);
            } else if ("Intervenant".equals(comboBoxTypeCompte.getSelectedItem())) {
                String[] donneesIntervenant = {inputEmail.getText(), new String(inputMdp.getPassword()), inputNomIntervenant.getText(), inputTypeIntervenant.getText(), inputIdVille.getText()};
                comptesIntervenant.add(donneesIntervenant);
                BaseDeDonnées.réécrire(INTERVENANTS_FILE, comptesIntervenant);
            }

            JOptionPane.showMessageDialog(frame, "Inscription effectuée avec succès !");
            cardLayout.show(mainPanel, "Accueil");
        });

        panelMain.add(panelForm, BorderLayout.CENTER);
        panelMain.add(panelFooter, BorderLayout.SOUTH);

        return panelMain;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
        App.main(args);
    }
}
