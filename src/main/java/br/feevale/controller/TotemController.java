package br.feevale.controller;

import br.feevale.model.CardapioEnergeticos;
import br.feevale.model.ProdutoEnergetico;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class TotemController {

    public static ProdutoEnergetico[] carrinho;

    @FXML
    private ListView<List<ProdutoEnergetico>> listaProdutos;

    @FXML
    private Button btnAdicionar, btnPagamento;

    private CardapioEnergeticos cardapio = new CardapioEnergeticos();

    @FXML
    public void initialize() {
        cardapio.adicionarProduto(new ProdutoEnergetico("Red Bull", 8.99, 250, "Red Bull", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Monster Energy", 10.99, 473, "Monster", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("TNT Energy Drink", 6.99, 269, "TNT", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Baly", 11.50, 2000, "Baly", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Burn", 5.99, 260, "Burn", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Fusion", 5.80, 473, "Fusion", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Reign", 10.99, 473, "Reign", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Flying Horse", 13.00, 2000, "Flying Horse", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Vulcano", 16.99, 2000, "Vulcano", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Extra Power", 10.50, 1000, "Extra Power", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Vibe", 13.00, 2000, "Vibe", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Furioso", 8.85, 2000, "Furioso", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Red Horse", 10.40, 2000, "Red Horse", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Tsunami", 6.00, 2000, "Tsunami", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Night Power", 15.00, 2000, "Night Power", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Long Night", 6.00, 2000, "Long Night", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Big Power", 11.00, 2000, "Big Power", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Atomic", 7.00, 270, "Atomic", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("220V", 10.00, 2000, "220V", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Engov UP", 9.00, 269, "Engov", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Dopamina", 6.00, 269, "Dopamina", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Organique", 7.00, 269, "Organique", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Wewi Energy", 6.00, 269, "Wewi", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Life Strong", 8.00, 473, "Life Strong", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Start", 8.30, 2000, "Start", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Mood", 8.00, 269, "Mood", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Guara Mix", 3.00, 290, "Guara Mix", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Hype", 10.00, 355, "Hype", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Bivolt", 8.50, 2000, "Bivolt", 0, null));
        cardapio.adicionarProduto(new ProdutoEnergetico("Itts", 11.00, 269, "Itts", 0, null));

        List<List<ProdutoEnergetico>> linhas = new ArrayList<>();
        List<ProdutoEnergetico> temp = new ArrayList<>();

        for (ProdutoEnergetico p : cardapio.getProdutos()) {
            temp.add(p);
            if (temp.size() == 2) {
                linhas.add(new ArrayList<>(temp));
                temp.clear();
            }
        }

        if (!temp.isEmpty()) {
            linhas.add(new ArrayList<>(temp));
        }

        listaProdutos.getItems().addAll(linhas);

        listaProdutos.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(List<ProdutoEnergetico> linha, boolean empty) {
                super.updateItem(linha, empty);

                if (empty || linha == null) {
                    setGraphic(null);
                    return;
                }

                HBox hbox = new HBox(20);

                for (ProdutoEnergetico p : linha) {
                    VBox card = new VBox(5);
                    card.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10;");

                    Button btn = new Button(p.getNome());
                    btn.setPrefWidth(150);

                    btn.setOnAction(e -> {
                        System.out.println("Adicionado: " + p.getNome());
                    });

                    card.getChildren().addAll(btn);
                    hbox.getChildren().add(card);
                }

                setGraphic(hbox);
            }
        });
    }
}
