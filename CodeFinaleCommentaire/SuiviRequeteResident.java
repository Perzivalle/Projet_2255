import javax.swing.*;
import java.awt.*;

/**
 * La classe SuiviRequeteResident étend la classe SuiviRequete pour fournir une interface spécifique aux résidents
 * afin de suivre les requêtes de travaux qu'ils ont soumises.
 * Elle utilise la même logique que SuiviRequete, mais est spécialisée pour l'usage par des résidents.
 */
public class SuiviRequeteResident extends SuiviRequete {

    /**
     * Constructeur de SuiviRequeteResident.
     * @param emailResident L'email du résident connecté, utilisé pour filtrer les requêtes pertinentes.
     * @param cardLayout Gestionnaire de layout pour la navigation entre les différents panneaux de l'interface utilisateur.
     * @param mainPanel Le panneau principal qui contient les différentes vues de l'interface utilisateur.
     */
    public SuiviRequeteResident(String emailResident, CardLayout cardLayout, JPanel mainPanel) {
        super(emailResident, cardLayout, mainPanel);
    }
}
