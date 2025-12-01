package br.feevale;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import br.feevale.model.*;
import java.util.List;

public class PrimaryController {

    @FXML
    private GridPane gridProdutos;

    private CardapioEnergeticos cardapio = new CardapioEnergeticos();

    @FXML
    private ListView<ProdutoEnergetico> listaProdutos;

    @FXML
    private ListView<String> listaTickets;   // <--- ADICIONADO

    private Set<String> produtosEmDestaque = new HashSet<>();


    @FXML
    public void initialize() {

        // --------------------
        //    PRODUTOS
        // --------------------
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

        for (ProdutoEnergetico p : cardapio.getProdutos()) {
            listaProdutos.getItems().add(p);
        }

        listaProdutos.setCellFactory(lv -> new ListCell<ProdutoEnergetico>() {
            @Override
            protected void updateItem(ProdutoEnergetico produto, boolean empty) {
                super.updateItem(produto, empty);

                if (empty || produto == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(produto.toString());
                    if (produtosEmDestaque.contains(produto.getNome())) {
                        setStyle("-fx-padding: 5px; -fx-background-color: #90EE90;");
                    } else {
                        setStyle("-fx-padding: 5px;");
                    }
                }
            }
        });

        listaProdutos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                ProdutoEnergetico produtoSelecionado = listaProdutos.getSelectionModel().getSelectedItem();
                if (produtoSelecionado != null) {
                    adicionarAoCarrinhoComFeedback(produtoSelecionado);
                }
            }
        });

        // -------------------------
        //     CARREGAR TICKETS
        // -------------------------
        atualizarListaTickets();
    }



    // ---------------------------------------------------------
    //           FUNÇÃO PARA ATUALIZAR LISTA DE TICKETS
    // ---------------------------------------------------------
    private void atualizarListaTickets() {
        if (listaTickets == null) return;  // caso não exista no FXML

        List<String> tickets = TicketStore.getTickets();

        listaTickets.getItems().clear();
        listaTickets.getItems().addAll(tickets);
    }



    private void adicionarAoCarrinhoComFeedback(ProdutoEnergetico produto) {
        Carrinho.adicionar(produto);
        produtosEmDestaque.add(produto.getNome());
        listaProdutos.refresh();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), animEvent -> {
            produtosEmDestaque.remove(produto.getNome());
            listaProdutos.refresh();
        }));
        timeline.play();
    }


    // ---------------------------------------------
    //                BOTÕES
    // ---------------------------------------------
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void abrirLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginadm.fxml"));
            Scene cena = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Login do Administrador");
            stage.setScene(cena);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
