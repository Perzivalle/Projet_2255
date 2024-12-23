import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * La classe soumettreProjet gère l'interface pour la soumission de nouveaux projets par les utilisateurs.
 * Elle permet aux utilisateurs de saisir des informations détaillées sur les projets et de les soumettre pour enregistrement.
 */
public class soumettreProjet {

    private JFrame frame;

    /**
     * Constructeur qui initialise l'interface graphique pour la soumission de nouveaux projets.
     */
    public soumettreProjet() {
        frame = new JFrame("Soumettre un nouveau projet");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Configuration des panneaux pour chaque attribut du projet
        panel.add(createTitlePanel());
        panel.add(createDescriptionPanel());
        panel.add(createTypePanel());
        panel.add(createAffectedAreasPanel());
        panel.add(createAffectedStreetsPanel());
        panel.add(createDatePanel());
        panel.add(createWorkSchedulePanel());

        // Panneau pour les boutons de soumission et d'annulation
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton submitButton = new JButton("Soumettre");
        JButton cancelButton = new JButton("Annuler");

        submitButton.addActionListener(e -> submitProject());
        cancelButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel);

        frame.add(new JScrollPane(panel));
        frame.setVisible(true);
    }

    /**
     * Crée et retourne un panneau pour la saisie du titre du projet.
     * @return JPanel le panneau contenant les composants pour le titre.
     */
    private JPanel createTitlePanel() {
        // Initialisation et configuration du panneau titre
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Titre du projet :");
        JTextField titleField = new JTextField(20);
        titlePanel.add(titleLabel);
        titlePanel.add(titleField);
        return titlePanel;
    }

    /**
     * Crée et retourne un panneau pour la description du projet.
     * @return JPanel le panneau contenant les composants pour la description.
     */
    private JPanel createDescriptionPanel() {
        // Initialisation et configuration du panneau description
        JPanel descriptionPanel = new JPanel(new FlowLayout());
        JLabel descriptionLabel = new JLabel("Description du projet :");
        JTextArea descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(scrollPane);
        return descriptionPanel;
    }

    /**
     * Soumet le projet saisi par l'utilisateur à la base de données après validation.
     */
    private void submitProject() {
        // Récupération des valeurs des champs, validation et traitement de la soumission
        String title = titleField.getText();
        String description = descriptionArea.getText();
        String type = typeComboBox.getSelectedItem().toString();
        String quarters = quartersField.getText();
        String streets = streetsField.getText();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        String schedule = scheduleField.getText();

        if (title.isEmpty() || description.isEmpty() || quarters.isEmpty() || streets.isEmpty()
                || startDate.isEmpty() || endDate.isEmpty() || schedule.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Enregistrement des informations du projet
        List<String> projectDetails = Arrays.asList(title, description, type, quarters, streets, startDate, endDate, schedule);
        BaseDeDonnées.ajouterLigne("projetsSoumis.csv", projectDetails);

        JOptionPane.showMessageDialog(frame, "Projet soumis avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
    }

    /**
     * Méthode principale pour exécuter la classe de manière indépendante.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(soumettreProjet::new);
    }
}
