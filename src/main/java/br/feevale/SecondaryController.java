package br.feevale;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;
import br.feevale.model.Carrinho;
import br.feevale.model.ItemCarrinho;

public class SecondaryController {

    @FXML
    private ListView<ItemCarrinho> listaCarrinho;

    @FXML
    private Label lblTotalAPagar;

    @FXML
    private Button btnSeguirPagamento;

    @FXML
    private Label lblSubtotal;

    @FXML
    private Label lblTotalDescontos;

    @FXML
    private ListView<String> listaDescontos;

    @FXML
    public void initialize() {
        configurarListaCarrinho();
        atualizarCarrinho();
        btnSeguirPagamento.setVisible(!Carrinho.getItens().isEmpty());
    }

    private void configurarListaCarrinho() {
        listaCarrinho.setCellFactory(lv -> new ListCell<ItemCarrinho>() {
            @Override
            protected void updateItem(ItemCarrinho item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox container = new HBox(10);
                    container.setStyle("-fx-padding: 5px;");
                    container.setAlignment(Pos.CENTER_LEFT);

                    String texto = item.getProduto().getNome() + " (" + item.getProduto().getMl() 
                        + "ml) - R$ " + String.format("%.2f", item.getProduto().getPreco()) 
                        + " x" + item.getQuantidade();
                    javafx.scene.control.Label labelProduto = new javafx.scene.control.Label(texto);
                    labelProduto.setStyle("-fx-font-size: 12px;");

                    HBox espacador = new HBox();
                    HBox.setHgrow(espacador, Priority.ALWAYS);

                    Button btnMenos = new Button("-");
                    btnMenos.setStyle(
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 5px 10px; " +
                        "-fx-min-width: 35px; " +
                        "-fx-min-height: 35px; " +
                        "-fx-pref-width: 35px; " +
                        "-fx-pref-height: 35px; " +
                        "-fx-border-color: #ccc; " +
                        "-fx-border-width: 1; " +
                        "-fx-background-color: #f5f5f5;"
                    );

                    btnMenos.setOnAction(event -> {
                        if (item.getQuantidade() == 1) {
                            Carrinho.removerItem(item);
                        } else {
                            item.decrementar();
                        }
                        atualizarCarrinho();
                    });

                    Button btnMais = new Button("+");
                    btnMais.setStyle(
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 5px 10px; " +
                        "-fx-min-width: 35px; " +
                        "-fx-min-height: 35px; " +
                        "-fx-pref-width: 35px; " +
                        "-fx-pref-height: 35px; " +
                        "-fx-border-color: #ccc; " +
                        "-fx-border-width: 1; " +
                        "-fx-background-color: #f5f5f5;"
                    );

                    btnMais.setOnAction(event -> {
                        item.incrementar();
                        atualizarCarrinho();
                    });

                    container.getChildren().addAll(labelProduto, espacador, btnMenos, btnMais);
                    setGraphic(container);
                    setText(null);
                }
            }
        });
    }

    private void atualizarCarrinho() {
        listaCarrinho.getItems().clear();
        listaCarrinho.getItems().addAll(Carrinho.getItens());
        atualizarTotais();
        btnSeguirPagamento.setVisible(!Carrinho.getItens().isEmpty());
    }

    private void atualizarTotais() {
        double subtotal = 0.0;
        double totalComDesconto = 0.0;
        double totalDescontos = 0.0;

        listaDescontos.getItems().clear();

        for (ItemCarrinho item : Carrinho.getItens()) {
            subtotal += item.getSubtotal();
            totalComDesconto += item.getTotalComDesconto();
            totalDescontos += item.getDesconto();

            if (item.getDesconto() > 0) {
                String descricaoDesconto = item.getProduto().getNome() + ": -R$ " 
                    + String.format("%.2f", item.getDesconto());
                listaDescontos.getItems().add(descricaoDesconto);
            }
        }

        lblSubtotal.setText("R$ " + String.format("%.2f", subtotal));
        lblTotalAPagar.setText("R$ " + String.format("%.2f", totalComDesconto));
        lblTotalDescontos.setText("-R$ " + String.format("%.2f", totalDescontos));
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchToTertiary() throws IOException {
        App.setRoot("tertiary");
    }
}
