import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * La classe SuiviCandidature gère l'interface pour le suivi des candidatures soumises par les utilisateurs.
 * Elle permet d'afficher les candidatures et leurs statuts actuels, et de gérer les actions sur ces candidatures.
 */
public class SuiviCandidature {

    private static final String CANDIDATURES_FILE = "candidatures.csv"; // Fichier des candidatures
    private static final String STATUT_FILE = "messagesCandidatures.csv"; // Fichier des statuts de candidature
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    /**
     * Constructeur qui initialise l'interface graphique pour le suivi des candidatures.
     */
    public SuiviCandidature() {
        frame = new JFrame("Suivi des Candidatures");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Suivi des Candidatures", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Requête", "Statut", "Message"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> frame.dispose());
        buttonPanel.add(retourButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        chargerCandidatures();

        frame.setVisible(true);
    }

    /**
     * Charge et affiche les candidatures avec leurs statuts depuis les fichiers CSV.
     */
    private void chargerCandidatures() {
        List<String[]> candidatures = BaseDeDonnées.lireTous(CANDIDATURES_FILE);
        List<String[]> statuts = BaseDeDonnées.lireTous(STATUT_FILE);

        tableModel.setRowCount(0);

        for (String[] candidature : candidatures) {
            String requete = candidature[0];
            String statut = "En attente"; // Par défaut, le statut est "En attente"
            String message = "";

            for (String[] statutEntry : statuts) {
                if (statutEntry[0].equals(requete)) {
                    statut = statutEntry[1];
                    message = statutEntry[2];
                    break;
                }
            }

            tableModel.addRow(new Object[]{requete, statut, message});
        }
    }

    /**
     * Gère les interactions avec les candidatures sélectionnées, permettant de modifier le statut de la candidature.
     * @param selectedRow l'indice de la ligne sélectionnée dans la table.
     */
    private void gererCandidature(int selectedRow) {
        String requete = table.getValueAt(selectedRow, 0).toString();
        String statut = table.getValueAt(selectedRow, 1).toString();

        // Affichage des options et mise à jour du statut selon le choix de l'utilisateur
        int choix = JOptionPane.showOptionDialog(frame,
                "Voulez-vous confirmer ou annuler votre candidature ?",
                "Gérer la Candidature",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Confirmer", "Annuler", "Annuler l'action"},
                "Confirmer");

        if (choix == JOptionPane.YES_OPTION) {
            modifierStatutCandidature(requete, "confirmée", "Votre candidature a été confirmée par vous.");
        } else if (choix == JOptionPane.NO_OPTION) {
            modifierStatutCandidature(requete, "annulée", "Votre candidature a été annulée par vous.");
        }
    }

    /**
     * Modifie le statut d'une candidature et enregistre le changement dans le fichier CSV.
     * @param requete le titre de la candidature à modifier.
     * @param nouveauStatut le nouveau statut de la candidature.
     * @param message le message à associer avec le changement de statut.
     */
    private void modifierStatutCandidature(String requete, String nouveauStatut, String message) {
        // Mise à jour des statuts et enregistrement dans le fichier CSV
        List<String[]> statuts = BaseDeDonnées.lireTous(STATUT_FILE);
        boolean statutExistant = false;

        for (String[] statutEntry : statuts) {
            if (statutEntry[0].equals(requete)) {
                statutEntry[1] = nouveauStatut;
                statutEntry[2] = message;
                statutExistant = true;
                break;
            }
        }

        if (!statutExistant) {
            statuts.add(new String[]{requete, nouveauStatut, message});
        }

        BaseDeDonnées.ecrireTous(STATUT_FILE, statuts);
        chargerCandidatures();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SuiviCandidature::new);
    }
}
