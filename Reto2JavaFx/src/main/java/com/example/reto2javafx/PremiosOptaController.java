package com.example.reto2javafx;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class PremiosOptaController {
    @FXML
    private ListView<String> listaPremioOpta;

    public void mostrarPremios(List<Premio> premios) {
        if (premios != null && !premios.isEmpty()) {
            ObservableList<String> items = FXCollections.observableArrayList();
            for (Premio premio : premios) {
                items.add(premio.getNamePremioShow());
            }
            listaPremioOpta.setItems(items);
        } else {
            listaPremioOpta.getItems().clear();
        }
    }
}
