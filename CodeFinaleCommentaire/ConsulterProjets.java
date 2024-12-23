import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour consulter les projets soumis. Permet d'afficher les projets dans une interface graphique,
 * avec des fonctionnalités de filtrage par quartier et par date.
 */
public class ConsulterProjets {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private static final String PROJETS_FILE = "projetsSoumis.csv"; // Fichier CSV contenant les informations sur les projets

    /**
     * Constructeur qui initialise l'interface graphique et charge les données des projets.
     */
    public ConsulterProjets() {
        frame = new JFrame("Consulter les projets en cours ou à venir");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

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

        // Configuration du panneau de filtres
        JPanel filterPanel = new JPanel(new FlowLayout());
        JTextField inputFilterQuartier = new JTextField(15); // Champ pour le filtre par quartier
        JTextField inputFilterDate = new JTextField(15); // Champ pour le filtre par date
        JButton applyFilterButton = new JButton("Appliquer Filtre");
        JButton resetFilterButton = new JButton("Réinitialiser");
        JButton retourButton = new JButton("Retour");

        filterPanel.add(new JLabel("Filtrer par quartier :"));
        filterPanel.add(inputFilterQuartier);
        filterPanel.add(new JLabel("Filtrer par date (JJ/MM/AAAA) :"));
        filterPanel.add(inputFilterDate);
        filterPanel.add(applyFilterButton);
        filterPanel.add(resetFilterButton);
        filterPanel.add(retourButton);

        panel.add(filterPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        // Charger les projets dès l'ouverture de la fenêtre
        loadProjects();

        // Ajout des listeners pour les boutons de filtrage et de retour
        applyFilterButton.addActionListener(e -> applyFilter(inputFilterQuartier.getText(), inputFilterDate.getText()));
        resetFilterButton.addActionListener(e -> loadProjects());
        retourButton.addActionListener(e -> frame.dispose());
    }

    /**
     * Charge et affiche tous les projets depuis le fichier CSV dans la table.
     */
    private void loadProjects() {
        List<String[]> projects = BaseDeDonnées.lireTous(PROJETS_FILE);
        tableModel.setRowCount(0); // Réinitialisation de la table
        for (String[] project : projects) {
            tableModel.addRow(project);
        }
    }

    /**
     * Applique les filtres spécifiés aux données des projets et met à jour l'affichage.
     *
     * @param quartier Le quartier pour filtrer les projets.
     * @param dateDebut La date de début pour filtrer les projets.
     */
    private void applyFilter(String quartier, String dateDebut) {
        List<String[]> projects = BaseDeDonnées.lireTous(PROJETS_FILE);
        List<String[]> filteredProjects = new ArrayList<>();

        for (String[] project : projects) {
            boolean matchQuartier = quartier.isEmpty() || project[3].toLowerCase().contains(quartier.toLowerCase());
            boolean matchDateDebut = dateDebut.isEmpty() || project[5].equals(dateDebut);

            if (matchQuartier && matchDateDebut) {
                filteredProjects.add(project);
            }
        }

        tableModel.setRowCount(0); // Réinitialisation de la table
        for (String[] project : filteredProjects) {
            tableModel.addRow(project);
        }

        if (filteredProjects.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Aucun projet trouvé pour les critères sélectionnés.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Méthode principale pour exécuter l'application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConsulterProjets::new);
    }
}
