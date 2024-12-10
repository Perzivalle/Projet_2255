import javax.swing.*;
import java.awt.*;
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
        mainPanel.add(createPageMenuOptionsResident(), "MenuResident");
        mainPanel.add(createPageMenuOptionsIntervenant(), "MenuIntervenant");

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

        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnRetour = new JButton("Retour");
        JButton btnInscription = new JButton("S'inscrire");
        JLabel messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        panelFooter.add(btnRetour);
        panelFooter.add(btnInscription);
        panelFooter.add(messageLabel);

        btnRetour.addActionListener(e -> cardLayout.show(mainPanel, "Accueil"));

        btnInscription.addActionListener(e -> {
            String courriel = inputCourriel.getText();
            String motDePasse = new String(inputMdp.getPassword());
            String typeCompte = (String) comboBoxTypeCompte.getSelectedItem();

            if ("Résident".equals(typeCompte)) {
                comptesResident.add(new String[]{courriel, motDePasse});
                sauvegarderComptesCSV(RESIDENTS_FILE, comptesResident);
                JOptionPane.showMessageDialog(frame, "Compte Résident créé avec succès !");
            } else {
                comptesIntervenant.add(new String[]{courriel, motDePasse});
                sauvegarderComptesCSV(INTERVENANTS_FILE, comptesIntervenant);
                JOptionPane.showMessageDialog(frame, "Compte Intervenant créé avec succès !");
            }

            cardLayout.show(mainPanel, "Accueil");
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

        JPanel panelTypeCompte = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelTypeCompte = new JLabel("Type de compte : ");
        String[] typesCompte = {"Résident", "Intervenant"};
        JComboBox<String> comboBoxTypeCompte = new JComboBox<>(typesCompte);
        panelTypeCompte.add(labelTypeCompte);
        panelTypeCompte.add(comboBoxTypeCompte);
        panelBody.add(panelTypeCompte);

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

        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
            String typeCompte = (String) comboBoxTypeCompte.getSelectedItem();

            boolean isValid = false;

            if ("Résident".equals(typeCompte)) {
                isValid = comptesResident.stream().anyMatch(compte -> compte[0].equals(courriel) && compte[1].equals(motDePasse));
                if (isValid) cardLayout.show(mainPanel, "MenuResident");
            } else {
                isValid = comptesIntervenant.stream().anyMatch(compte -> compte[0].equals(courriel) && compte[1].equals(motDePasse));
                if (isValid) cardLayout.show(mainPanel, "MenuIntervenant");
            }

            if (!isValid) {
                messageLabel.setText("Courriel ou mot de passe invalide.");
            }
        });

        panelConnexion.add(panelBody, BorderLayout.CENTER);
        panelConnexion.add(panelFooter, BorderLayout.SOUTH);

        return panelConnexion;
    }

    private JPanel createPageMenuOptionsResident() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Menu Avancé - Résident", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 10, 10));

        JButton btnConsulterTravaux = new JButton("Consulter les travaux en cours");
        btnConsulterTravaux.addActionListener(e -> new ConsulterTravauxGUI());
        buttonPanel.add(btnConsulterTravaux);

        buttonPanel.add(new JButton("Modifier ses horaires de préférence"));
        buttonPanel.add(new JButton("Voir ses notifications"));
        buttonPanel.add(new JButton("Consulter les travaux à venir (3 prochains mois)"));
        buttonPanel.add(new JButton("Consulter les entraves"));
        buttonPanel.add(new JButton("Soumettre une requête de travail"));
        buttonPanel.add(new JButton("Faire le suivi d'une requête de travail"));

        JButton btnDeconnexion = new JButton("Se Déconnecter");
        btnDeconnexion.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(frame, "Êtes-vous sûr de vouloir vous déconnecter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                cardLayout.show(mainPanel, "Accueil");
            }
        });
        buttonPanel.add(btnDeconnexion);

        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPageMenuOptionsIntervenant() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Menu Avancé - Intervenant", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.add(new JButton("Consulter les requêtes de travail"));
        buttonPanel.add(new JButton("Soumettre/Soustraire sa candidature"));
        buttonPanel.add(new JButton("Faire le suivi de sa candidature"));
        buttonPanel.add(new JButton("Soumettre un projet"));
        buttonPanel.add(new JButton("Modifier le statut d'un projet"));

        JButton btnDeconnexion = new JButton("Se Déconnecter");
        btnDeconnexion.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(frame, "Êtes-vous sûr de vouloir vous déconnecter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                cardLayout.show(mainPanel, "Accueil");
            }
        });
        buttonPanel.add(btnDeconnexion);

        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
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
