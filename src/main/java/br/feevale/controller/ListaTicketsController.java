package br.feevale.controller;

import br.feevale.model.TicketDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ListaTicketsController {

    @FXML
    private VBox listaBox;

    @FXML
    private Label lblTotal;

    @FXML
    public void initialize() {
        carregarTickets();
    }

    private void carregarTickets() {
        listaBox.getChildren().clear();
        double soma = 0;

        for (TicketDatabase.Ticket t : TicketDatabase.getTickets()) {
            Label item = new Label(
                "Ticket Nº " + String.format("%05d", t.numero) +
                " | Método: " + t.metodo +
                " | Valor: R$ " + String.format("%.2f", t.valor)
            );
            item.setStyle("-fx-font-size: 16px;");
            listaBox.getChildren().add(item);

            soma += t.valor;
        }

        lblTotal.setText("TOTAL GERAL: R$ " + String.format("%.2f", soma));
    }
}
