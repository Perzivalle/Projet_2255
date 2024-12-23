import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * La classe soumettreTravaux gère l'interface pour soumettre des requêtes de travaux résidentiels par les utilisateurs.
 * Elle permet de saisir des informations sur des travaux prévus et de les enregistrer dans un fichier CSV.
 */
public class soumettreTravaux {

    private final JFrame frame;
    private final ArrayList<String> informationRequete = new ArrayList<>();
    private static final String CSV_FILE = "requeteTravaux.csv";
    private String suivi = "Non";

    /**
     * Constructeur qui initialise l'interface graphique pour la soumission de nouvelles requêtes de travaux.
     */
    public soumettreTravaux() {
        frame = new JFrame("Soumettre une Requête de Travail Résidentiel");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Configuration des composants de l'interface
        JLabel titleLabel = new JLabel("Soumettre une Requête de Travail", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = createFormPanel(); // Création du panneau de formulaire
        frame.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel(); // Création du panneau des boutons
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * Crée et retourne un panneau de formulaire pour la saisie des détails de la requête.
     * @return JPanel le panneau contenant les champs de formulaire.
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));

        // Initialisation et ajout des différents champs de saisie
        formPanel.add(new JLabel("Titre du travail :"));
        JTextField titreField = new JTextField();
        formPanel.add(titreField);

        formPanel.add(new JLabel("Description détaillée :"));
        JTextArea descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(descriptionArea));

        formPanel.add(new JLabel("Type de travaux :"));
        JComboBox<String> typeTravauxBox = new JComboBox<>(new String[]{"Chaussée", "Éclairage", "Autre"});
        formPanel.add(typeTravauxBox);

        formPanel.add(new JLabel("Quartier :"));
        JComboBox<String> quartierBox = new JComboBox<>(new String[]{"Centre-ville", "Plateau Mont-Royal", "Rosemont", "Villeray", "Outremont", "Verdun", "Autre"});
        formPanel.add(quartierBox);

        formPanel.add(new JLabel("Date de début (JJ/MM/AAAA) :"));
        JComboBox<String> jourBox = new JComboBox<>(); // Jours ajoutés en fonction du mois et de l'année
        JComboBox<String> moisBox = new JComboBox<>(new String[]{"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"});
        JComboBox<String> anneeBox = new JComboBox<>(new String[]{"2024", "2025", "2026"});
        formPanel.add(anneeBox);
        formPanel.add(moisBox);
        formPanel.add(jourBox);

        JCheckBox suiviCheckBox = new JCheckBox("Faire le suivi de cette requête");
        formPanel.add(new JLabel("Options de suivi :"));
        formPanel.add(suiviCheckBox);

        return formPanel;
    }

    /**
     * Crée et retourne un panneau de boutons pour les actions de soumission et d'annulation.
     * @return JPanel le panneau contenant les boutons.
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton submitButton = new JButton("Soumettre");
        JButton cancelButton = new JButton("Annuler");
        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);
        return buttonPanel;
    }

    /**
     * Méthode principale pour exécuter la classe de manière indépendante.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(soumettreTravaux::new);
    }
}
