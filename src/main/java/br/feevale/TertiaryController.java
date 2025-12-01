package br.feevale;

import java.io.IOException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

import br.feevale.model.Carrinho;
import br.feevale.model.Pedido;
import br.feevale.model.ItemPedido;
import br.feevale.model.ItemCarrinho;

public class TertiaryController {

    @FXML
    private Label lblNumeroPedido;

    @FXML
    private Label lblValorTotal;

    @FXML
    private Label lblMetodoPagamento;

    @FXML
    private Button btnDinheiro;

    @FXML
    private Button btnCartaoCredito;

    @FXML
    private Button btnCartaoDebito;

    @FXML
    private Button btnPix;

    @FXML
    private Button btnFinalizarPedido;

    @FXML
    private StackPane paymentStack;

    @FXML
    private VBox methodsPane;

    @FXML
    private VBox detailsPane;

    private Pedido pedidoAtual;
    private String metodoPagamentoSelecionado = "";
    private static Pedido persistedPedido = null;

    @FXML
    public void initialize() {

        // carregar dados do carrinho
        if (persistedPedido == null || Carrinho.isModificado()) {
            int numeroPedido = gerarNumeroPedido();
            lblNumeroPedido.setText(String.format("%05d", numeroPedido));
            pedidoAtual = new Pedido(numeroPedido);

            for (ItemCarrinho item : Carrinho.getItens()) {
                pedidoAtual.adicionarItem(new ItemPedido(item.getProduto(), item.getQuantidade()));
            }

            double totalComDesconto = Carrinho.getItens().stream()
                    .mapToDouble(ItemCarrinho::getTotalComDesconto)
                    .sum();

            pedidoAtual.setTotal(totalComDesconto);
            lblValorTotal.setText("Total: R$ " + String.format("%.2f", totalComDesconto));

            persistedPedido = pedidoAtual;
            Carrinho.setModificado(false);
        } else {
            pedidoAtual = persistedPedido;
            lblNumeroPedido.setText(String.format("%05d", pedidoAtual.getNumero()));
            lblValorTotal.setText("Total: R$ " + String.format("%.2f", pedidoAtual.getTotal()));
        }

        // ações dos botões
        btnDinheiro.setOnAction(event -> showCashPane());
        btnCartaoCredito.setOnAction(event -> showCreditPane());
        btnCartaoDebito.setOnAction(event -> showDebitPane());
        btnPix.setOnAction(event -> showPixPane());

        btnFinalizarPedido.setOnAction(event -> {
            if (metodoPagamentoSelecionado.isEmpty()) {
                lblMetodoPagamento.setText("Método: Selecione um método!");
                return;
            }
            finalizarPedido();
        });
    }

    private void showMethodsPane() {
        methodsPane.setVisible(true);
        detailsPane.getChildren().clear();
        detailsPane.setVisible(false);

        // reset estilo
        btnDinheiro.setStyle(null);
        btnCartaoCredito.setStyle(null);
        btnCartaoDebito.setStyle(null);
        btnPix.setStyle(null);
    }

    //===========================
    //     DINHEIRO
    //===========================

    private void showCashPane() {
        methodsPane.setVisible(false);
        detailsPane.setVisible(true);
        detailsPane.getChildren().clear();

        Label title = new Label("Pagamento em Dinheiro");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label lblTotal = new Label(lblValorTotal.getText());

        HBox row = new HBox(8);
        row.setAlignment(Pos.CENTER_LEFT);

        Label lblPago = new Label("Valor recebido:");
        TextField tfPago = new TextField();
        tfPago.setPromptText("Ex: 50.00");

        tfPago.setTextFormatter(new TextFormatter<>(change -> {
            return change.getControlNewText().matches("[0-9.,]*") ? change : null;
        }));

        row.getChildren().addAll(lblPago, tfPago);

        Label lblTroco = new Label("Troco: R$ 0.00");

        tfPago.textProperty().addListener((obs, oldV, newV) -> {
            try {
                double recebido = Double.parseDouble(newV.replace(",", "."));
                double total = pedidoAtual.getTotal();
                double troco = Math.max(recebido - total, 0);
                lblTroco.setText(String.format("Troco: R$ %.2f", troco));
            } catch (Exception e) {
                lblTroco.setText("Troco: R$ 0.00");
            }
        });

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(evt -> showMethodsPane());

        detailsPane.getChildren().addAll(title, lblTotal, row, lblTroco, btnBack);

        metodoPagamentoSelecionado = "Dinheiro";
        lblMetodoPagamento.setText("Método: Dinheiro");
        btnDinheiro.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    }

    //===========================
    //     CRÉDITO
    //===========================

    private void showCreditPane() {
        methodsPane.setVisible(false);
        detailsPane.setVisible(true);
        detailsPane.getChildren().clear();

        Label title = new Label("Pagamento - Cartão de Crédito");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        TextField tfNome = new TextField();
        tfNome.setPromptText("Nome no cartão");

        TextField tfNumero = new TextField();
        tfNumero.setPromptText("Número do cartão");
        tfNumero.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("\\d{0,16}") ? c : null));

        HBox h = new HBox(8);
        TextField tfVal = new TextField();
        tfVal.setPromptText("MM/AA");

        tfVal.textProperty().addListener((obs, oldVal, newVal) -> {
            String num = newVal.replaceAll("\\D", "");
            if (num.length() > 4) num = num.substring(0, 4);
            if (num.length() >= 2) num = num.substring(0, 2) + "/" + num.substring(2);
            if (!newVal.equals(num)) tfVal.setText(num);
        });

        PasswordField tfCvv = new PasswordField();
        tfCvv.setPromptText("CVV");
        tfCvv.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("\\d{0,4}") ? c : null));

        h.getChildren().addAll(tfVal, tfCvv);

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(evt -> showMethodsPane());

        detailsPane.getChildren().addAll(title, tfNome, tfNumero, h, btnBack);

        metodoPagamentoSelecionado = "Cartão de Crédito";
        lblMetodoPagamento.setText("Método: Cartão de Crédito");
        btnCartaoCredito.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    }

    //===========================
    //     DÉBITO
    //===========================

    private void showDebitPane() {
        methodsPane.setVisible(false);
        detailsPane.setVisible(true);
        detailsPane.getChildren().clear();

        Label title = new Label("Pagamento - Cartão de Débito");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        TextField tfNumero = new TextField();
        tfNumero.setPromptText("Número do cartão");
        tfNumero.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("\\d{0,16}") ? c : null));

        PasswordField tfSenha = new PasswordField();
        tfSenha.setPromptText("Senha");
        tfSenha.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("\\d{0,6}") ? c : null));

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(evt -> showMethodsPane());

        detailsPane.getChildren().addAll(title, tfNumero, tfSenha, btnBack);

        metodoPagamentoSelecionado = "Cartão de Débito";
        lblMetodoPagamento.setText("Método: Cartão de Débito");
        btnCartaoDebito.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    }

    //===========================
    //     PIX
    //===========================

    private void showPixPane() {
        methodsPane.setVisible(false);
        detailsPane.setVisible(true);
        detailsPane.getChildren().clear();

        Label title = new Label("Pagamento - PIX");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label lblChave = new Label("Chave PIX:");
        TextArea taChave = new TextArea("meu-pix@exemplo.com");
        taChave.setWrapText(true);
        taChave.setPrefRowCount(2);

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(evt -> showMethodsPane());

        detailsPane.getChildren().addAll(title, lblChave, taChave, btnBack);

        metodoPagamentoSelecionado = "PIX";
        lblMetodoPagamento.setText("Método: PIX");
        btnPix.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    }

    //===========================
    //  FINALIZAR PEDIDO
    //===========================

    private void finalizarPedido() {
        System.out.println("Pedido #" + String.format("%05d", pedidoAtual.getNumero()) +
                " finalizado com " + metodoPagamentoSelecionado);
        System.out.println("Total: R$ " + String.format("%.2f", pedidoAtual.getTotal()));

        Carrinho.limpar();

        try {
            App.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int gerarNumeroPedido() {
        return new Random().nextInt(90000) + 10000;
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
