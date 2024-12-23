import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de modifier les informations des projets soumis.
 * Offre des fonctionnalités pour mettre à jour la description, la date de fin, et le statut des projets.
 */
public class ModifierProjet {

    /** Fichier contenant les projets soumis. */
    private static final String PROJETS_FILE = "projetsSoumis.csv";

    /** Fichier pour les notifications. */
    private static final String NOTIFICATIONS_FILE = "notifications.csv";

    /** Fichier pour les modifications des projets. */
    private static final String MODIFICATIONS_FILE = "modificationsProjets.csv";

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    /**
     * Constructeur de la classe ModifierProjet.
     * Initialise l'interface graphique et charge les projets soumis dans une JTable.
     */
    public ModifierProjet() {
        frame = new JFrame("Modifier un projet");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Modifier les informations d'un projet", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Configuration de la JTable pour afficher les projets
        String[] columnNames = {
                "Titre du projet",
                "Description du projet",
                "Type de travaux",
                "Quartiers affectés",
                "Rues affectées",
                "Date de début",
                "Date de fin",
                "Horaires des travaux",
                "Statut"
        };

        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Boutons d'action
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton modifyDescriptionButton = new JButton("Modifier la description");
        JButton modifyEndDateButton = new JButton("Modifier la date de fin");
        JButton modifyStatusButton = new JButton("Modifier le statut");
        JButton retourButton = new JButton("Retour");

        modifyDescriptionButton.addActionListener(e -> modifierDescription());
        modifyEndDateButton.addActionListener(e -> modifierDateFin());
        modifyStatusButton.addActionListener(e -> modifierStatut());
        retourButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(modifyDescriptionButton);
        buttonPanel.add(modifyEndDateButton);
        buttonPanel.add(modifyStatusButton);
        buttonPanel.add(retourButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        // Charger les projets dès l'ouverture
        loadProjets(PROJETS_FILE, tableModel);
    }

    /**
     * Charge les projets depuis le fichier spécifié et les affiche dans la JTable.
     *
     * @param fichier     Chemin du fichier contenant les projets.
     * @param tableModel  Modèle de la JTable où afficher les projets.
     */
    private void loadProjets(String fichier, DefaultTableModel tableModel) {
        List<String[]> projets = BaseDeDonnées.lireTous(fichier);
        tableModel.setRowCount(0); // Réinitialiser la table
        for (String[] projet : projets) {
            if (projet.length == 9) { // Vérifie que toutes les colonnes sont présentes
                tableModel.addRow(projet);
            }
        }
    }

    /**
     * Modifie la description d'un projet sélectionné dans la JTable.
     */
    private void modifierDescription() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un projet pour modifier la description.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String titre = table.getValueAt(selectedRow, 0).toString();
        String ancienneDescription = table.getValueAt(selectedRow, 1).toString();
        String nouvelleDescription = JOptionPane.showInputDialog(frame, "Entrez la nouvelle description pour le projet : " + titre, ancienneDescription);
        if (nouvelleDescription != null && !nouvelleDescription.isEmpty()) {
            table.setValueAt(nouvelleDescription, selectedRow, 1); // Met à jour la colonne Description
            ajouterNotification("Un projet a été modifié", titre);
            enregistrerModification(titre, "Description modifiée", ancienneDescription, nouvelleDescription);
            sauvegarderModifications();
        }
    }

    /**
     * Modifie la date de fin d'un projet sélectionné dans la JTable.
     */
    private void modifierDateFin() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un projet pour modifier la date de fin.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String titre = table.getValueAt(selectedRow, 0).toString();
        String ancienneDateFin = table.getValueAt(selectedRow, 6).toString();
        String nouvelleDateFin = JOptionPane.showInputDialog(frame, "Entrez la nouvelle date de fin pour le projet : " + titre, ancienneDateFin);
        if (nouvelleDateFin != null && !nouvelleDateFin.isEmpty()) {
            table.setValueAt(nouvelleDateFin, selectedRow, 6); // Met à jour la colonne Date Fin
            ajouterNotification("Un projet a été modifié", titre);
            enregistrerModification(titre, "Date de fin modifiée", ancienneDateFin, nouvelleDateFin);
            sauvegarderModifications();
        }
    }

    /**
     * Modifie le statut d'un projet sélectionné dans la JTable.
     */
    private void modifierStatut() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un projet pour modifier le statut.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String titre = table.getValueAt(selectedRow, 0).toString();
        String ancienStatut = table.getValueAt(selectedRow, 8).toString();
        String[] options = {"Prévu", "En cours", "Suspendu", "Terminé"};
        String nouveauStatut = (String) JOptionPane.showInputDialog(frame, "Sélectionnez le nouveau statut pour le projet : " + titre,
                "Modifier le statut", JOptionPane.QUESTION_MESSAGE, null, options, ancienStatut);

        if (nouveauStatut != null) {
            table.setValueAt(nouveauStatut, selectedRow, 8); // Met à jour la colonne Statut
            ajouterNotification("Un projet a été modifié", titre);
            enregistrerModification(titre, "Statut modifié", ancienStatut, nouveauStatut);
            sauvegarderModifications();
        }
    }

    /**
     * Sauvegarde les modifications apportées à la liste des projets.
     */
    private void sauvegarderModifications() {
        List<String[]> projets = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String[] projet = new String[tableModel.getColumnCount()];
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                projet[j] = table.getValueAt(i, j).toString();
            }
            projets.add(projet);
        }

        BaseDeDonnées.réécrire(PROJETS_FILE, projets);
        JOptionPane.showMessageDialog(frame, "Modifications enregistrées avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Ajoute une notification concernant une modification de projet.
     *
     * @param typeNotif  Type de notification.
     * @param titreProjet Titre du projet concerné.
     */
    private void ajouterNotification(String typeNotif, String titreProjet) {
        String[] notification = {typeNotif, titreProjet};
        BaseDeDonnées.écrire(NOTIFICATIONS_FILE, notification);
    }

    /**
     * Enregistre une modification spécifique pour un projet.
     *
     * @param titre           Titre du projet concerné.
     * @param typeModification Type de modification effectuée.
     * @param ancienneValeur   Ancienne valeur de l'attribut modifié.
     * @param nouvelleValeur   Nouvelle valeur de l'attribut modifié.
     */
    private void enregistrerModification(String titre, String typeModification, String ancienneValeur, String nouvelleValeur) {
        String[] modification = {titre, typeModification, ancienneValeur, nouvelleValeur};
        BaseDeDonnées.écrire(MODIFICATIONS_FILE, modification);
    }

    /**
     * Méthode principale pour lancer l'application.
     *
     * @param args Arguments de ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ModifierProjet::new);
    }
}
