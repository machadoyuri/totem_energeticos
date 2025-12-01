package br.feevale.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class TicketsController {

    @FXML
    private ListView<String> listaTickets;

    @FXML
    private Label labelTotal;

    @FXML
    public void initialize() {
        // EXEMPLO (você vai trocar pelos tickets reais depois)
        listaTickets.getItems().add("Pedido 01 - R$ 15,00");
        listaTickets.getItems().add("Pedido 02 - R$ 12,00");
        listaTickets.getItems().add("Pedido 03 - R$ 18,00");

        atualizarTotal();
    }

    private void atualizarTotal() {
        double total = listaTickets.getItems().stream()
                .mapToDouble(item -> {
                    String valorStr = item.substring(item.indexOf("R$") + 2).replace(",", ".");
                    return Double.parseDouble(valorStr);
                })
                .sum();

        labelTotal.setText(String.format("Total: R$ %.2f", total));
    }
}
