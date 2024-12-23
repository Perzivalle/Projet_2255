import code.App;
import com.google.gson.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

/**
 * Cette classe est responsable de l'affichage des demandes de travaux à partir d'une réponse d'API.
 * Elle permet aux utilisateurs de visualiser, filtrer et interagir avec les demandes de travaux en utilisant une interface graphique.
 */
public class ConsulterTravaux {

    /**
     * Méthode pour initialiser et afficher les demandes de travaux si l'appel API est réussi.
     */
    public static void ConsulterTravaux() {
        if (App.responseTravaux == null || App.responseTravaux.getStatusCode() != 200) {
            JOptionPane.showMessageDialog(null, "Erreur de données de travaux", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String jsonResponseTravaux = App.responseTravaux.getBody();  // Extraction des données depuis la réponse API
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponseTravaux, JsonObject.class);
        JsonArray records = jsonObject.getAsJsonObject("result").getAsJsonArray("records");

        String[] columnNames = {"ID", "BoroughId", "CurrentStatus", "ReasonCategory", "SubmitterCategory", "OrganizationName"};
        Object[][] data = new Object[records.size()][6];

        // Remplissage du tableau de données avec les informations issues des objets JSON
        int index = 0;
        for (JsonElement record : records) {
            JsonObject obj = record.getAsJsonObject();
            data[index][0] = obj.has("id") && !obj.get("id").isJsonNull() ? obj.get("id").getAsString() : "Indisponible";
            data[index][1] = obj.has("boroughid") && !obj.get("boroughid").isJsonNull() ? obj.get("boroughid").getAsString() : "Indisponible";
            data[index][2] = obj.has("currentstatus") && !obj.get("currentstatus").isJsonNull() ? obj.get("currentstatus").getAsString() : "Indisponible";
            data[index][3] = obj.has("reason_category") && !obj.get("reason_category").isJsonNull() ? obj.get("reason_category").getAsString() : "Indisponible";
            data[index][4] = obj.has("submittercategory") && !obj.get("submittercategory").isJsonNull() ? obj.get("submittercategory").getAsString() : "Indisponible";
            data[index][5] = obj.has("organizationname") && !obj.get("organizationname").isJsonNull() ? obj.get("organizationname").getAsString() : "Indisponible";
            index++;
        }

        JFrame frame = Frame.nouveauFrame("MaVille");
        frame.setVisible(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = Panel.panelTitrePage("Consultation des travaux");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Retour");
        buttonPanel.add(backButton);
        headerPanel.add(buttonPanel, BorderLayout.WEST);
        backButton.addActionListener(e -> {
            frame.dispose();
            menu.createPageMenuOptionsResident();
        });

        JTable table = new JTable(data, columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout());
        JTextField filterInput = new JTextField(15);
        JButton filterButton = new JButton("Filtrer");
        JButton upcomingMonthsButton = new JButton("Voir 3 prochains mois");
        JButton resetButton = new JButton("Réinitialiser");
        String[] filterOptions = {"boroughid", "ID"};
        JComboBox<String> comboBox = new JComboBox<>(filterOptions);

        filterPanel.add(filterInput);
        filterPanel.add(comboBox);
        filterPanel.add(filterButton);
        filterPanel.add(upcomingMonthsButton);
        filterPanel.add(resetButton);
        mainPanel.add(filterPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Listeners d'événements pour le filtrage et la visualisation des demandes de travaux pour les mois à venir
        filterButton.addActionListener(e -> {
            String filterChoice = (String) comboBox.getSelectedItem();
            String filterData = filterInput.getText().trim().toLowerCase();
            // La logique de filtrage doit être implémentée ici
        });

        upcomingMonthsButton.addActionListener(e -> {
            // Logique pour afficher les demandes de travaux pour les trois prochains mois
        });

        resetButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (Object[] row : data) {
                model.addRow(row);
            }
            filterInput.setText(""); // Effacer l'entrée du filtre
        });
    }
}
