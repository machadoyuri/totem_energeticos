package br.feevale;

import br.feevale.model.CardapioEnergeticos;
import br.feevale.model.Carrinho;
import br.feevale.model.ItemPedido;
import br.feevale.model.Pedido;
import br.feevale.model.ProdutoEnergetico;
import br.feevale.model.TicketArmazenamento;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PrimaryController {

    @FXML
    private GridPane gridProdutos;


    private CardapioEnergeticos cardapio = new CardapioEnergeticos();

    @FXML
    private ListView<ProdutoEnergetico> listaProdutos;

    @FXML
    private ListView<Pedido> listaTickets;

    private Set<String> produtosEmDestaque = new HashSet<>();


    @FXML
    public void initialize() {

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

        setupTicketList();
    }

    private void setupTicketList() {
        if (listaTickets != null) {
            listaTickets.setItems(TicketArmazenamento.getTickets());

            listaTickets.setCellFactory(param -> new ListCell<Pedido>() {
                private final VBox container = new VBox(5);
                private final HBox topRow = new HBox(10);
                private final Label ticketInfo = new Label();
                private final Pane pane = new Pane();
                private final Button removeButton = new Button("-");
                private final VBox itemsBox = new VBox(2);

                {
                    HBox.setHgrow(pane, Priority.ALWAYS);
                    topRow.getChildren().addAll(ticketInfo, pane, removeButton);
                    container.getChildren().addAll(topRow, new Separator(), itemsBox);

                    removeButton.setOnAction(event -> {
                        Pedido pedido = getItem();
                        if (pedido != null) {
                            TicketArmazenamento.removerTicket(pedido);
                            getListView().getItems().remove(pedido);
                        }
                    });
                }

                @Override
                protected void updateItem(Pedido pedido, boolean empty) {
                    super.updateItem(pedido, empty);
                    if (empty || pedido == null) {
                        setGraphic(null);
                    } else {
                        ticketInfo.setText(String.format("Pedido #%05d - R$ %.2f (%s)",
                                pedido.getNumero(),
                                pedido.getTotal(),
                                pedido.getMetodoPagamento()));

                        itemsBox.getChildren().clear();
                        if (pedido.getItens() != null) {
                            for (ItemPedido item : pedido.getItens()) {
                                itemsBox.getChildren().add(
                                    new Label(String.format("  - %d x %s", item.getQuantidade(), item.getProduto().getNome()))
                                );
                            }
                        }
                        
                        setGraphic(container);
                    }
                }
            });
        }
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

