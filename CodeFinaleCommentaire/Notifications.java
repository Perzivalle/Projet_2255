import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Notifications gère l'interface utilisateur pour afficher les notifications liées aux projets.
 * Elle permet aux utilisateurs de voir les notifications des modifications et soumissions des projets.
 */
public class Notifications {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<String[]> modifications;

    /**
     * Constructeur qui initialise les composants de l'interface utilisateur et charge les notifications.
     */
    public Notifications() {
        frame = new JFrame("Notifications des Projets");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Notifications des Projets", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Notifications (cliquez pour plus de détails)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> frame.dispose());
        buttonPanel.add(closeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        loadNotifications();

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                afficherDetailsNotification(table.getSelectedRow());
            }
        });
    }

    /**
     * Charge et affiche les notifications à partir des fichiers CSV.
     */
    private void loadNotifications() {
        try {
            List<String[]> projets = BaseDeDonnées.lireTous("projetsSoumis.csv");
            modifications = BaseDeDonnées.lireTous("modificationsProjets.csv");

            List<String[]> notifications = preparerNotifications(projets);

            tableModel.setRowCount(0);
            for (String[] notification : notifications) {
                tableModel.addRow(new String[]{notification[0]});
            }

            for (String[] modification : modifications) {
                tableModel.addRow(new String[]{"La modification d'un projet a été faite"});
            }

            if (notifications.isEmpty() && modifications.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Aucune notification disponible.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des notifications.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Compte et retourne le nombre total de notifications.
     *
     * @return int Nombre total de notifications.
     */
    public static int getNotificationCount() {
        try {
            List<String[]> projets = BaseDeDonnées.lireTous("projetsSoumis.csv");
            int countProjets = projets.size();
            List<String[]> modifications = BaseDeDonnées.lireTous("modificationsProjets.csv");
            int countModifications = modifications.size();
            return countProjets + countModifications;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Prépare les notifications pour l'affichage à partir des projets soumis.
     *
     * @param projets Liste des projets soumis.
     * @return List<String[]> Liste formatée des notifications.
     */
    private List<String[]> preparerNotifications(List<String[]> projets) {
        List<String[]> notifications = new ArrayList<>();
        for (String[] projet : projets) {
            String typeNotif = "Un projet a été soumis";
            notifications.add(new String[]{typeNotif});
        }
        return notifications;
    }

    /**
     * Affiche les détails d'une notification sélectionnée.
     *
     * @param selectedRow L'index de la ligne sélectionnée dans la table.
     */
    private void afficherDetailsNotification(int selectedRow) {
        try {
            int projetsCount = BaseDeDonnées.lireTous("projetsSoumis.csv").size();
            if (selectedRow < projetsCount) {
                List<String[]> projets = BaseDeDonnées.lireTous("projetsSoumis.csv");
                String[] projet = projets.get(selectedRow);
                String details = String.format(
                        "Titre : %s%nType : %s%nQuartiers : %s%nRues : %s%nDate Début : %s%nDate Fin : %s%nStatut : %s",
                        projet[0], projet[1], projet[2], projet[3], projet[4], projet[5], projet[6]
                );
                JOptionPane.showMessageDialog(frame, details, "Détails de la Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int modificationIndex = selectedRow - projetsCount;
                String[] modification = modifications.get(modificationIndex);
                String details = String.format(
                        "Titre du projet : %s%nType de modification : %s%nAncienne valeur : %s%nNouvelle valeur : %s",
                        modification[0], modification[1], modification[2], modification[3]
                );
                JOptionPane.showMessageDialog(frame, details, "Détails de la Modification", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors de l'affichage des détails.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Méthode principale pour exécuter la classe de manière indépendante.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Notifications::new);
    }
}
