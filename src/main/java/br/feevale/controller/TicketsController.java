package br.feevale.controller;

import br.feevale.model.ItemPedido;
import br.feevale.model.Pedido;
import br.feevale.model.TicketArmazenamento;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class TicketsController {

    @FXML
    private ListView<Pedido> listaTickets;

    @FXML
    public void initialize() {
        listaTickets.setItems(TicketArmazenamento.getTickets());

        listaTickets.setCellFactory(lv -> new ListCell<Pedido>() {
            private final VBox container = new VBox(5);
            private final HBox headerRow = new HBox(10);
            private final Label ticketInfo = new Label();
            private final Label statusLabel = new Label();
            private final Region spacer = new Region();
            private final Button btnAvançar = new Button("Avançar");
            private final Button btnRemover = new Button("X");
            private final VBox itemsBox = new VBox(2);
            private final Separator separator = new Separator();

            {
                container.setStyle("-fx-padding: 10; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-width: 1;");
                headerRow.setAlignment(Pos.CENTER_LEFT);
                HBox.setHgrow(spacer, Priority.ALWAYS);
                
                ticketInfo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                statusLabel.setStyle("-fx-font-weight: bold; -fx-padding: 3 8; -fx-background-radius: 3; -fx-text-fill: white;");

                btnAvançar.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 11px;");
                btnAvançar.setOnAction(event -> {
                    Pedido p = getItem();
                    if (p != null) {
                        p.avancarStatus();
                        TicketArmazenamento.salvarTicket(p);
                        updateItem(p, false); 
                    }
                });

                btnRemover.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 30px;");
                btnRemover.setOnAction(event -> {
                    Pedido p = getItem();
                    if (p != null) {
                        TicketArmazenamento.removerTicket(p);
                    }
                });

                headerRow.getChildren().addAll(ticketInfo, spacer, statusLabel, btnAvançar, btnRemover);
                container.getChildren().addAll(headerRow, separator, itemsBox);
            }

            @Override
            protected void updateItem(Pedido item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    String metodo = item.getMetodoPagamento() == null || item.getMetodoPagamento().isEmpty() 
                                    ? "..." : item.getMetodoPagamento();

                    ticketInfo.setText(String.format("#%05d | R$ %.2f | %s", 
                        item.getNumero(), item.getTotal(), metodo));

                    statusLabel.setText(item.getStatus().getDescricao());
                    switch (item.getStatus()) {
                        case EM_PREPARO:
                            statusLabel.setStyle("-fx-background-color: #ffc107; -fx-text-fill: black; -fx-padding: 3 8; -fx-background-radius: 5;");
                            btnAvançar.setDisable(false);
                            btnAvançar.setText("Pronto?");
                            break;
                        case PRONTO:
                            statusLabel.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-padding: 3 8; -fx-background-radius: 5;");
                            btnAvançar.setDisable(false);
                            btnAvançar.setText("Entregar");
                            break;
                        case ENTREGUE:
                            statusLabel.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-padding: 3 8; -fx-background-radius: 5;");
                            btnAvançar.setDisable(true);
                            btnAvançar.setText("Concluído");
                            break;
                    }

                    itemsBox.getChildren().clear();
                    if (item.getItens() != null) {
                        for (ItemPedido ip : item.getItens()) {
                            Label lblItem = new Label(String.format("• %dx %s", ip.getQuantidade(), ip.getProduto().getNome()));
                            lblItem.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
                            itemsBox.getChildren().add(lblItem);
                        }
                    }

                    setGraphic(container);
                }
            }
        });
    }
}