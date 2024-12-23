/**
 * Classe SuiviRequete permettant de suivre les requêtes de travaux soumises par un résident.
 * Fournit des fonctionnalités pour afficher, gérer et fermer des requêtes.
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class SuiviRequete {

    /**
     * Fichier contenant les requêtes de travaux.
     */
    private static final String REQUETE_FILE = "requeteTravaux.csv";

    /**
     * Fichier contenant les candidatures associées aux requêtes.
     */
    private static final String CANDIDATURES_FILE = "candidatures.csv";

    /**
     * Fichier contenant les messages et décisions des candidatures.
     */
    private static final String MESSAGES_FILE = "messagesCandidatures.csv";

    private String emailResident; // Email du résident connecté
    private CardLayout cardLayout; // Pour naviguer dans le menu
    private JPanel mainPanel; // Panneau principal pour navigation
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<String[]> requetes;
    private static List<String[]> candidatures;
    private static List<String[]> messagesCandidatures;

    /**
     * Constructeur de la classe SuiviRequete.
     *
     * @param emailResident Email du résident connecté.
     * @param cardLayout CardLayout pour la navigation.
     * @param mainPanel Panneau principal contenant les pages de navigation.
     */
    public SuiviRequete(String emailResident, CardLayout cardLayout, JPanel mainPanel) {
        this.emailResident = emailResident;
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        frame = new JFrame("Suivi des Requêtes de Travaux");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Suivi des Requêtes de Travaux", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Titre", "Description", "Type", "Quartier", "Date Début", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Actualiser");
        JButton closeButton = new JButton("Fermer");
        JButton closeRequestButton = new JButton("Fermer la Requête");

        refreshButton.addActionListener(e -> loadRequetes());
        closeButton.addActionListener(e -> frame.dispose());
        closeRequestButton.addActionListener(e -> fermerRequete());

        buttonPanel.add(refreshButton);
        buttonPanel.add(closeRequestButton);
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        loadRequetes();

        // Ajouter un événement pour gérer le clic sur une ligne
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                handleRequeteSelection(table.getSelectedRow());
            }
        });
    }

    /**
     * Charge les requêtes, candidatures et messages dans les tableaux correspondants.
     */
    private void loadRequetes() {
        requetes = BaseDeDonnées.lireTous(REQUETE_FILE);
        candidatures = BaseDeDonnées.lireTous(CANDIDATURES_FILE);
        messagesCandidatures = BaseDeDonnées.lireTous(MESSAGES_FILE);

        tableModel.setRowCount(0); // Réinitialiser la table
        for (String[] requete : requetes) {
            String statut = getStatutRequete(requete[0], messagesCandidatures, candidatures);
            tableModel.addRow(new String[]{requete[0], requete[1], requete[2], requete[3], requete[4], statut});
        }
    }

    /**
     * Obtient le statut d'une requête donnée.
     *
     * @param titreRequete Le titre de la requête.
     * @param messagesCandidatures Liste des messages et décisions associés aux candidatures.
     * @param candidatures Liste des candidatures soumises.
     * @return Le statut de la requête.
     */
    public static String getStatutRequete(String titreRequete, List<String[]> messagesCandidatures, List<String[]> candidatures) {
        // Vérifier si une décision a déjà été prise
        for (String[] message : messagesCandidatures) {
            if (message[0].equals(titreRequete)) {
                return "Candidature " + message[1];
            }
        }
        // Si aucune décision, vérifier les candidatures
        return candidatures.stream()
                .anyMatch(c -> c.length > 0 && c[0].equals(titreRequete)) ? "Candidature soumise" : "Disponible";
    }

    /**
     * Gère la sélection d'une requête dans la table.
     *
     * @param selectedRow L'index de la ligne sélectionnée.
     */
    private void handleRequeteSelection(int selectedRow) {
        String titreRequete = table.getValueAt(selectedRow, 0).toString();
        String statut = table.getValueAt(selectedRow, 5).toString();

        if (!"Candidature soumise".equals(statut)) {
            JOptionPane.showMessageDialog(frame, "Cette candidature est déjà traitée ou indisponible.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Permettre de prendre une décision
        int response = JOptionPane.showOptionDialog(frame,
                "Que voulez-vous faire avec cette candidature ?",
                "Gestion de la Candidature",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Accepter", "Refuser", "Annuler"},
                "Accepter");

        if (response == JOptionPane.YES_OPTION) {
            enregistrerMessage(titreRequete, "acceptée");
        } else if (response == JOptionPane.NO_OPTION) {
            enregistrerMessage(titreRequete, "refusée");
        }
    }

    /**
     * Enregistre un message et une décision pour une requête donnée.
     *
     * @param titreRequete Le titre de la requête.
     * @param action L'action prise (acceptée ou refusée).
     */
    private void enregistrerMessage(String titreRequete, String action) {
        String message = JOptionPane.showInputDialog(frame, "Entrez un message pour accompagner votre décision :");
        if (message == null || message.trim().isEmpty()) {
            message = "Aucun message fourni.";
        }
        BaseDeDonnées.écrire(MESSAGES_FILE,new String[]{titreRequete, action, message});
        JOptionPane.showMessageDialog(frame, "Candidature " + action + " avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        loadRequetes();
    }

    /**
     * Ferme une requête sélectionnée par l'utilisateur.
     */
    private void fermerRequete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une requête à fermer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String titreRequete = table.getValueAt(selectedRow, 0).toString();

        int confirmation = JOptionPane.showConfirmDialog(frame,
                "Êtes-vous sûr de vouloir fermer cette requête ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            // Supprimer la requête du fichier
            BaseDeDonnées.supprimer(REQUETE_FILE, enregistrement -> enregistrement[0].equals(titreRequete));
            JOptionPane.showMessageDialog(frame, "La requête a été fermée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            loadRequetes();
        }
    }

    /**
     * Point d'entrée principal pour tester la classe SuiviRequete.
     *
     * @param args Les arguments de ligne de commande.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);
            new SuiviRequete("test@example.com", cardLayout, mainPanel);
        });
    }
}
