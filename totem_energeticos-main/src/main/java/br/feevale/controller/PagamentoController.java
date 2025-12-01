package br.feevale.controller;

import br.feevale.model.ProdutoEnergetico;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class PagamentoController {

    @FXML
    private ListView<ProdutoEnergetico> listaPedido;

    @FXML
    private Label lblTotal;

    @FXML
    public void initialize() {
        double total = 0.0;

        for (ProdutoEnergetico p : TotemController.carrinho) {
            listaPedido.getItems().add(p);
            total += p.getPreco();
        }

        lblTotal.setText(String.format("Total: R$ %.2f", total));
    }
}
