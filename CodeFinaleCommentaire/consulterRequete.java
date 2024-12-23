import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe destinée à consulter et gérer les requêtes de travaux.
 * Elle permet de visualiser, filtrer, soumettre et retirer des candidatures pour des travaux.
 */
public class consulterRequete {

    private static final String CSV_FILE = "requeteTravaux.csv"; // Fichier CSV contenant les requêtes de travaux
    private static final String CANDIDATURES_FILE = "candidatures.csv"; // Fichier CSV contenant les candidatures aux travaux
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<String[]> allTravaux;
    private List<String[]> candidatures;

    /**
     * Constructeur qui initialise l'interface utilisateur pour la consultation des requêtes de travaux.
     */
    public consulterRequete() {
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

        applyFilterButton.addActionListener(e -> applyFilter(inputFilter.getText(), (String) filterComboBox.getSelectedItem()));
        resetFilterButton.addActionListener(e -> resetTable());

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

        soumettreButton.addActionListener(e -> soumettreCandidature());
        retirerButton.addActionListener(e -> retirerCandidature());
        refreshButton.addActionListener(e -> updateTable());
        closeButton.addActionListener(e -> frame.dispose());

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
     * Met à jour la table des travaux en rechargeant les données depuis les fichiers CSV.
     */
    private void updateTable() {
        allTravaux = lireRequetesDepuisCSV();
        candidatures = lireCandidaturesDepuisCSV();
        resetTable();
    }

    /**
     * Réinitialise et met à jour les données affichées dans la table en fonction des travaux et des candidatures existantes.
     */
    private void resetTable() {
        tableModel.setRowCount(0);
        for (String[] travail : allTravaux) {
            String statut = candidatures.stream().anyMatch(c -> Arrays.equals(travail, Arrays.copyOfRange(c, 0, travail.length))) ? "Candidature soumise" : "Disponible";
            tableModel.addRow(new String[]{travail[0], travail[1], travail[2], travail[3], travail[4], travail[5], statut});
        }
    }

    /**
     * Applique un filtre aux travaux affichés en fonction du type, du quartier ou de la date de début.
     *
     * @param criteria La valeur du critère de filtrage.
     * @param filterType Le type de filtre appliqué (Type, Quartier, Date Début).
     */
    private void applyFilter(String criteria, String filterType) {
        if (criteria == null || criteria.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Veuillez entrer une valeur pour le filtre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String[]> filteredTravaux = allTravaux.stream().filter(travail -> {
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

        tableModel.setRowCount(0); // Réinitialise le tableau
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
            sauvegarderCandidature(travail, dateDebut, dateFin);
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

        List<String[]> updatedCandidatures = candidatures.stream()
                .filter(c -> !Arrays.equals(travail, Arrays.copyOfRange(c, 0, travail.length)))
                .collect(Collectors.toList());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CANDIDATURES_FILE))) {
            for (String[] candidature : updatedCandidatures) {
                writer.write(String.join(",", candidature));
                writer.newLine();
            }
            JOptionPane.showMessageDialog(frame, "Candidature retirée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            updateTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sauvegarde une candidature dans le fichier CSV des candidatures.
     *
     * @param travail Les détails du travail pour lequel la candidature est soumise.
     * @param dateDebut La date de début de la candidature.
     * @param dateFin La date de fin de la candidature.
     */
    private void sauvegarderCandidature(String[] travail, String dateDebut, String dateFin) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CANDIDATURES_FILE, true))) {
            writer.write(String.join(",", travail) + ",Date Début: " + dateDebut + ",Date Fin: " + dateFin);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors de la sauvegarde de la candidature.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Lit les requêtes de travaux depuis un fichier CSV.
     *
     * @return Une liste de tableaux de String contenant les détails de chaque travail.
     */
    private List<String[]> lireRequetesDepuisCSV() {
        List<String[]> travaux = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                travaux.add(ligne.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return travaux;
    }

    /**
     * Lit les candidatures depuis un fichier CSV.
     *
     * @return Une liste de tableaux de String contenant les détails de chaque candidature.
     */
    private List<String[]> lireCandidaturesDepuisCSV() {
        List<String[]> candidatures = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CANDIDATURES_FILE))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                candidatures.add(ligne.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return candidatures;
    }

    /**
     * Méthode principale pour exécuter l'application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(consulterRequete::new);
    }
}
