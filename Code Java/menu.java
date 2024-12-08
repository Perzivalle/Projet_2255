import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class menu {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private static final String RESIDENTS_FILE = "comptesResident.csv";
    private static final String INTERVENANTS_FILE = "comptesIntervenant.csv";

    private static final List<String[]> comptesResident = new ArrayList<>();
    private static final List<String[]> comptesIntervenant = new ArrayList<>();

    public menu() {
        // Charger les comptes depuis les fichiers
        chargerComptesCSV(RESIDENTS_FILE, comptesResident);
        chargerComptesCSV(INTERVENANTS_FILE, comptesIntervenant);

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

        // Afficher la page d'accueil par défaut
        cardLayout.show(mainPanel, "Accueil");

        // Ajouter le panneau principal à la fenêtre
        frame.add(mainPanel);
        frame.setVisible(true);
    }

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

    private JPanel createPageInscription() {
        JPanel panelMain = new JPanel(new BorderLayout());

        JLabel titre = new JLabel("Page d'inscription", SwingConstants.CENTER);
        titre.setFont(titre.getFont().deriveFont(30f));
        panelMain.add(titre, BorderLayout.NORTH);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));

        JPanel panelTypeCompte = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelTypeCompte = new JLabel("Type de compte : ");
        String[] typesCompte = {"Résident", "Intervenant"};
        JComboBox<String> comboBoxTypeCompte = new JComboBox<>(typesCompte);
        panelTypeCompte.add(labelTypeCompte);
        panelTypeCompte.add(comboBoxTypeCompte);
        panelForm.add(panelTypeCompte);

        JPanel panelCourriel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelCourriel = new JLabel("Adresse courriel : ");
        JTextField inputCourriel = new JTextField(20);
        panelCourriel.add(labelCourriel);
        panelCourriel.add(inputCourriel);
        panelForm.add(panelCourriel);

        JPanel panelMdp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelMdp = new JLabel("Mot de passe : ");
        JPasswordField inputMdp = new JPasswordField(20);
        panelMdp.add(labelMdp);
        panelMdp.add(inputMdp);
        panelForm.add(panelMdp);

        JPanel panelResidentDetails = new JPanel();
        panelResidentDetails.setLayout(new BoxLayout(panelResidentDetails, BoxLayout.Y_AXIS));

        JPanel panelNom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelNom = new JLabel("Nom complet : ");
        JTextField inputNom = new JTextField(20);
        panelNom.add(labelNom);
        panelNom.add(inputNom);
        panelResidentDetails.add(panelNom);

        JPanel panelDateNaissance = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelDateNaissance = new JLabel("Date de naissance : ");
        JTextField inputDateNaissance = new JTextField(20);
        panelDateNaissance.add(labelDateNaissance);
        panelDateNaissance.add(inputDateNaissance);
        panelResidentDetails.add(panelDateNaissance);

        JPanel panelAdresse = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelAdresse = new JLabel("Adresse résidentielle : ");
        JTextField inputAdresse = new JTextField(20);
        panelAdresse.add(labelAdresse);
        panelAdresse.add(inputAdresse);
        panelResidentDetails.add(panelAdresse);

        JPanel panelIntervenantDetails = new JPanel();
        panelIntervenantDetails.setLayout(new BoxLayout(panelIntervenantDetails, BoxLayout.Y_AXIS));

        JPanel panelTypeIntervenant = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelTypeIntervenant = new JLabel("Type d'intervenant : ");
        JTextField inputTypeIntervenant = new JTextField(20);
        panelTypeIntervenant.add(labelTypeIntervenant);
        panelTypeIntervenant.add(inputTypeIntervenant);
        panelIntervenantDetails.add(panelTypeIntervenant);

        JPanel panelVille = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelVille = new JLabel("Identifiant de la ville (8 chiffres) : ");
        JTextField inputVille = new JTextField(20);
        panelVille.add(labelVille);
        panelVille.add(inputVille);
        panelIntervenantDetails.add(panelVille);

        panelForm.add(panelResidentDetails);
        panelForm.add(panelIntervenantDetails);

        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnRetour = new JButton("Retour");
        JButton btnInscription = new JButton("S'inscrire");
        JLabel messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        panelFooter.add(btnRetour);
        panelFooter.add(btnInscription);
        panelFooter.add(messageLabel);

        comboBoxTypeCompte.addActionListener(e -> {
            String typeCompte = (String) comboBoxTypeCompte.getSelectedItem();
            panelResidentDetails.setVisible("Résident".equals(typeCompte));
            panelIntervenantDetails.setVisible("Intervenant".equals(typeCompte));
        });

        panelResidentDetails.setVisible(true);
        panelIntervenantDetails.setVisible(false);

        btnRetour.addActionListener(e -> cardLayout.show(mainPanel, "Accueil"));

        btnInscription.addActionListener(e -> {
            String courriel = inputCourriel.getText();
            String motDePasse = new String(inputMdp.getPassword());
            String typeCompte = (String) comboBoxTypeCompte.getSelectedItem();

            if (typeCompte.equals("Résident")) {
                String nom = inputNom.getText();
                String dateNaissance = inputDateNaissance.getText();
                String adresse = inputAdresse.getText();

                if (courriel.isEmpty() || motDePasse.isEmpty() || nom.isEmpty() || dateNaissance.isEmpty() || adresse.isEmpty()) {
                    messageLabel.setText("Tous les champs sont obligatoires.");
                    return;
                }

                for (String[] compte : comptesResident) {
                    if (compte[0].equals(courriel)) {
                        messageLabel.setText("Ce courriel est déjà utilisé.");
                        return;
                    }
                }

                comptesResident.add(new String[]{courriel, motDePasse, nom, dateNaissance, adresse});
                sauvegarderComptesCSV(RESIDENTS_FILE, comptesResident);
                JOptionPane.showMessageDialog(frame, "Compte Résident créé avec succès !");
                cardLayout.show(mainPanel, "Accueil");

            } else if (typeCompte.equals("Intervenant")) {
                String typeIntervenant = inputTypeIntervenant.getText();
                String ville = inputVille.getText();

                if (courriel.isEmpty() || motDePasse.isEmpty() || typeIntervenant.isEmpty() || ville.isEmpty() || !ville.matches("\\d{8}")) {
                    messageLabel.setText("Tous les champs sont obligatoires pour un intervenant.");
                    return;
                }

                for (String[] compte : comptesIntervenant) {
                    if (compte[0].equals(courriel)) {
                        messageLabel.setText("Ce courriel est déjà utilisé.");
                        return;
                    }
                }

                comptesIntervenant.add(new String[]{courriel, motDePasse, typeIntervenant, ville});
                sauvegarderComptesCSV(INTERVENANTS_FILE, comptesIntervenant);
                JOptionPane.showMessageDialog(frame, "Compte Intervenant créé avec succès !");
                cardLayout.show(mainPanel, "Accueil");
            }
        });

        panelMain.add(panelForm, BorderLayout.CENTER);
        panelMain.add(panelFooter, BorderLayout.SOUTH);

        return panelMain;
    }

    private JPanel createPageConnexion() {
        JPanel panelConnexion = new JPanel(new BorderLayout());

        JLabel titre = new JLabel("Page de connexion", SwingConstants.CENTER);
        titre.setFont(titre.getFont().deriveFont(30f));
        panelConnexion.add(titre, BorderLayout.NORTH);

        JPanel panelBody = new JPanel();
        panelBody.setLayout(new BoxLayout(panelBody, BoxLayout.Y_AXIS));

        JPanel panelCourriel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelCourriel = new JLabel("Adresse courriel : ");
        JTextField inputCourriel = new JTextField(20);
        panelCourriel.add(labelCourriel);
        panelCourriel.add(inputCourriel);
        panelBody.add(panelCourriel);

        JPanel panelMdp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelMdp = new JLabel("Mot de passe : ");
        JPasswordField inputMdp = new JPasswordField(20);
        panelMdp.add(labelMdp);
        panelMdp.add(inputMdp);
        panelBody.add(panelMdp);

        JPanel panelFooter = new JPanel(new FlowLayout());
        JButton btnRetour = new JButton("Retour");
        JButton btnConnexion = new JButton("Se connecter");
        JLabel messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        panelFooter.add(btnRetour);
        panelFooter.add(btnConnexion);
        panelFooter.add(messageLabel);

        btnRetour.addActionListener(e -> cardLayout.show(mainPanel, "Accueil"));

        btnConnexion.addActionListener(e -> {
            String courriel = inputCourriel.getText();
            String motDePasse = new String(inputMdp.getPassword());

            boolean isValid = comptesResident.stream()
                    .anyMatch(compte -> compte[0].equals(courriel) && compte[1].equals(motDePasse));

            if (isValid) {
                JOptionPane.showMessageDialog(frame, "Connexion réussie !");
                cardLayout.show(mainPanel, "Accueil");
            } else {
                messageLabel.setText("Courriel ou mot de passe invalide.");
            }
        });

        panelConnexion.add(panelBody, BorderLayout.CENTER);
        panelConnexion.add(panelFooter, BorderLayout.SOUTH);

        return panelConnexion;
    }

    private static void sauvegarderComptesCSV(String fichier, List<String[]> comptes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
            for (String[] compte : comptes) {
                writer.write(String.join(",", compte));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void chargerComptesCSV(String fichier, List<String[]> comptes) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(",");
                comptes.add(parts);
            }
        } catch (IOException ignored) {
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(menu::new);
    }
}

