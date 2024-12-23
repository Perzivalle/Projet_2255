import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe pour consulter et gérer les requêtes de travaux en utilisant une interface graphique.
 * Elle permet de visualiser des requêtes, appliquer des filtres, et gérer des candidatures.
 */
public class consulterRequeteTravail {

    private static final String CSV_FILE = "requeteTravaux.csv"; // Fichier contenant les requêtes de travaux.
    private static final String CANDIDATURES_FILE = "candidatures.csv"; // Fichier contenant les candidatures aux travaux.
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<String[]> allTravaux;
    private List<String[]> candidatures;

    /**
     * Constructeur qui initialise l'interface graphique et charge les données initiales.
     */
    public consulterRequeteTravail() {
        frame = new JFrame("Consulter Requêtes de Travaux");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Liste des Travaux Soumis", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Titre", "Description", "Type", "Quartier", "Date Début", "Suivi", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout());
        JTextField inputFilter = new JTextField(15);
        String[] filterOptions = {"Type", "Quartier", "Date Début"};
        JComboBox<String> filterComboBox = new JComboBox<>(filterOptions);
        JButton applyFilterButton = new JButton("Filtrer");
        JButton resetFilterButton = new JButton("Réinitialiser");

        filterPanel.add(new JLabel("Filtrer par :"));
        filterPanel.add(filterComboBox);
        filterPanel.add(inputFilter);
        filterPanel.add(applyFilterButton);
        filterPanel.add(resetFilterButton);
        panel.add(filterPanel, BorderLayout.NORTH);

        JPanel actionButtonPanel = new JPanel(new FlowLayout());
        JButton soumettreButton = new JButton("Soumettre une Candidature");
        JButton retirerButton = new JButton("Retirer une Candidature");
        JButton refreshButton = new JButton("Actualiser");
        JButton closeButton = new JButton("Fermer");

        actionButtonPanel.add(soumettreButton);
        actionButtonPanel.add(retirerButton);
        actionButtonPanel.add(refreshButton);
        actionButtonPanel.add(closeButton);
        panel.add(actionButtonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        updateTable();
    }

    /**
     * Met à jour les données affichées dans la table en rechargeant les données depuis les fichiers CSV.
     */
    private void updateTable() {
        allTravaux = BaseDeDonnées.lireTous(CSV_FILE);
        candidatures = BaseDeDonnées.lireTous(CANDIDATURES_FILE);
        resetTable();
    }

    /**
     * Réinitialise les données affichées dans la table à l'état actuel des travaux et des candidatures.
     */
    private void resetTable() {
        tableModel.setRowCount(0);
        for (String[] travail : allTravaux) {
            String statut = candidatures.stream()
                    .anyMatch(c -> Arrays.equals(travail, Arrays.copyOfRange(c, 0, travail.length)))
                    ? "Candidature soumise" : "Disponible";
            tableModel.addRow(new String[]{travail[0], travail[1], travail[2], travail[3], travail[4], travail[5], statut});
        }
    }

    /**
     * Applique un filtre aux travaux affichés en fonction du type, du quartier ou de la date de début.
     *
     * @param criteria Critère de filtrage.
     * @param filterType Type de filtre à appliquer.
     */
    private void applyFilter(String criteria, String filterType) {
        if (criteria == null || criteria.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Veuillez entrer une valeur pour le filtre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String[]> filteredTravaux = allTravaux.stream()
                .filter(travail -> {
                    switch (filterType) {
                        case "Type":
                            return travail[2].equalsIgnoreCase(criteria);
                        case "Quartier":
                            return travail[3].equalsIgnoreCase(criteria);
                        case "Date Début":
                            return travail[4].equals(criteria); // Format exact : AAAA-MM-JJ
                        default:
                            return false;
                    }
                }).collect(Collectors.toList());

        tableModel.setRowCount(0);
        for (String[] travail : filteredTravaux) {
            String statut = candidatures.stream()
                    .anyMatch(c -> Arrays.equals(travail, Arrays.copyOfRange(c, 0, travail.length)))
                    ? "Candidature soumise" : "Disponible";
            tableModel.addRow(new String[]{travail[0], travail[1], travail[2], travail[3], travail[4], travail[5], statut});
        }
    }

    /**
     * Permet à l'utilisateur de soumettre une candidature pour un travail sélectionné.
     */
    private void soumettreCandidature() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un travail pour soumettre votre candidature.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String statut = table.getValueAt(selectedRow, 6).toString();
        if ("Candidature soumise".equals(statut)) {
            JOptionPane.showMessageDialog(frame, "Vous avez déjà soumis une candidature.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] travail = new String[6];
        for (int i = 0; i < 6; i++) {
            travail[i] = table.getValueAt(selectedRow, i).toString();
        }

        String dateDebut = JOptionPane.showInputDialog(frame, "Entrez la date de début (format : JJ/MM/AAAA) :");
        String dateFin = JOptionPane.showInputDialog(frame, "Entrez la date de fin (format : JJ/MM/AAAA) :");

        if (dateDebut != null && !dateDebut.isEmpty() && dateFin != null && !dateFin.isEmpty()) {
            String[] valeurs = new String[travail.length + 2];
            System.arraycopy(travail, 0, valeurs, 0, travail.length);
            valeurs[travail.length] = dateDebut;
            valeurs[travail.length + 1] = dateFin;
            BaseDeDonnées.écrire(CANDIDATURES_FILE, valeurs);

            JOptionPane.showMessageDialog(frame, "Candidature soumise avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            updateTable();
        }
    }

    /**
     * Permet à l'utilisateur de retirer une candidature précédemment soumise pour un travail sélectionné.
     */
    private void retirerCandidature() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un travail pour retirer votre candidature.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] travail = new String[6];
        for (int i = 0; i < 6; i++) {
            travail[i] = table.getValueAt(selectedRow, i).toString();
        }

        BaseDeDonnées.ConditionSuppression conditionSuppression = enregistrement -> Arrays.equals(travail, Arrays.copyOfRange(enregistrement, 0, travail.length));

        BaseDeDonnées.supprimer(CANDIDATURES_FILE, conditionSuppression);
    }

    /**
     * Programme principal pour lancer l'application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(consulterRequeteTravail::new);
    }
}
