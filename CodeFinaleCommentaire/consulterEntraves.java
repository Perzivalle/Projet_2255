import code.App;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Classe permettant de consulter les informations sur les entraves à partir des données fournies par une API.
 */
public class consulterEntraves {

    /**
     * Consulte les entraves et affiche les données dans une interface utilisateur.
     * Cette méthode extrait les données de l'API, les traite et les affiche dans un tableau avec des options de filtrage.
     */
    public static void consulterEntraves() {
        // Vérifie que la réponse de l'API est valide
        if (App.responseEntraves == null || App.responseEntraves.getStatusCode() != 200) {
            JOptionPane.showMessageDialog(null, "Erreur de données d'entraves","Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parse les données JSON reçues de l'API
        String jsonResponseEntraves = App.responseEntraves.getBody();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponseEntraves, JsonObject.class);
        JsonArray records = jsonObject.getAsJsonObject("result").getAsJsonArray("records");

        String[] nomColonnes = {"ID Request", "Street ID", "Short Name", "Street Impact Type"};
        Object[][] donnees = new Object[records.size()][4];

        // Extraction et traitement des données pour chaque entrave
        int range = 0;
        for (JsonElement record : records) {
            JsonObject obj = record.getAsJsonObject();
            donnees[range][0] = obj.get("id_request").getAsString().trim().toLowerCase();
            donnees[range][1] = obj.get("streetid").getAsString().trim().toLowerCase();
            donnees[range][2] = obj.get("shortname").getAsString().trim().toLowerCase();
            donnees[range][3] = obj.get("streetimpacttype").getAsString().trim().toLowerCase();
            range++;
        }

        // Configuration de la fenêtre principale
        JFrame frameConsulterEntraves = Frame.nouveauFrame("MaVille");
        frameConsulterEntraves.setVisible(true);

        // Configuration du panel principal
        JPanel panelConsulterEntraves = new JPanel(new BorderLayout());
        JPanel panelEnTete = Panel.panelTitrePage("Consultation des entraves");
        panelConsulterEntraves.add(panelEnTete, BorderLayout.NORTH);

        // Configuration des boutons
        JPanel bouton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton retour = new JButton("Retour");
        bouton.add(retour);
        panelEnTete.add(retour, BorderLayout.WEST);
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameConsulterEntraves.dispose();
                Main.createPageMenuOptionsResident();
            }
        });

        // Création du tableau pour afficher les entraves
        JTable tableEntraves = new JTable(donnees, nomColonnes);
        tableEntraves.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPaneEntraves = new JScrollPane(tableEntraves);
        scrollPaneEntraves.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelConsulterEntraves.add(scrollPaneEntraves, BorderLayout.CENTER);

        // Panel pour les options de filtrage des entraves
        JPanel panelFiltreEntraves = new JPanel(new FlowLayout());
        JTextField inputFiltre = new JTextField(15);
        JButton filtrer = new JButton("Filtrer");
        JButton reinitialiser = new JButton("Réinitialiser");
        String[] typesFiltre = {"ID Request", "Street ID"};
        JComboBox<String> comboBoxTypeFiltre = new JComboBox<>(typesFiltre);

        panelFiltreEntraves.add(inputFiltre);
        panelFiltreEntraves.add(comboBoxTypeFiltre);
        panelFiltreEntraves.add(filtrer);
        panelFiltreEntraves.add(reinitialiser);

        panelConsulterEntraves.add(panelFiltreEntraves, BorderLayout.SOUTH);

        filtrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choixFiltre = (String) comboBoxTypeFiltre.getSelectedItem();
                String inputDonneeFiltre = inputFiltre.getText().trim().toLowerCase();

                Object[][] donneesFiltrer = new Object[records.size()][4];
                int i = 0;
                if ("ID Request".equals(choixFiltre)) {
                    for (JsonElement record : records) {
                        JsonObject obj = record.getAsJsonObject();
                        String idRequest = obj.get("id_request").getAsString().trim().toLowerCase();
                        if (idRequest.equals(inputDonneeFiltre)) {
                            donneesFiltrer[i][0] = idRequest;
                            donneesFiltrer[i][1] = obj.get("streetid").getAsString().trim().toLowerCase();
                            donneesFiltrer[i][2] = obj.get("shortname").getAsString().trim().toLowerCase();
                            donneesFiltrer[i][3] = obj.get("streetimpacttype").getAsString().trim().toLowerCase();
                            i++;
                        }
                    }
                } else if ("Street ID".equals(choixFiltre)) {
                    for (JsonElement record : records) {
                        JsonObject obj = record.getAsJsonObject();
                        String streetid = obj.get("streetid").getAsString().trim().toLowerCase();
                        if (streetid.equals(inputDonneeFiltre)) {
                            donneesFiltrer[i][0] = obj.get("id_request").getAsString().trim().toLowerCase();
                            donneesFiltrer[i][1] = streetid;
                            donneesFiltrer[i][2] = obj.get("shortname").getAsString().trim().toLowerCase();
                            donneesFiltrer[i][3] = obj.get("streetimpacttype").getAsString().trim().toLowerCase();
                            i++;
                        }
                    }
                }
                DefaultTableModel nouvelleTableEntrave = new DefaultTableModel(donneesFiltrer, nomColonnes);
                tableEntraves.setModel(nouvelleTableEntrave);
            }
        });

        reinitialiser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel reinitialiserTableEntrave = new DefaultTableModel(donnees, nomColonnes);
                tableEntraves.setModel(reinitialiserTableEntrave);
                //Permet de s'assurer que le champs soit libre pour taper à nouveau
                inputFiltre.setText("");
            }
        });

        frameConsulterEntraves.add(panelConsulterEntraves);
    }
}
