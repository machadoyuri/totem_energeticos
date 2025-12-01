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
        if (persistedPedido == null || Carrinho.isModificado()) {
            int numeroPedido = gerarNumeroPedido();
            lblNumeroPedido.setText(String.format("%05d", numeroPedido));

            pedidoAtual = new Pedido(numeroPedido);

            for (ItemCarrinho item : Carrinho.getItens()) {
                ItemPedido itemPedido = new ItemPedido(item.getProduto(), item.getQuantidade());
                pedidoAtual.adicionarItem(itemPedido);
            }

            double totalComDesconto = 0.0;
            for (ItemCarrinho item : Carrinho.getItens()) {
                totalComDesconto += item.getTotalComDesconto();
            }
            pedidoAtual.setTotal(totalComDesconto);
            lblValorTotal.setText("Total: R$ " + String.format("%.2f", totalComDesconto));

            persistedPedido = pedidoAtual;
            Carrinho.setModificado(false);
        } else {
            pedidoAtual = persistedPedido;
            lblNumeroPedido.setText(String.format("%05d", pedidoAtual.getNumero()));
            lblValorTotal.setText("Total: R$ " + String.format("%.2f", pedidoAtual.getTotal()));
        }

        btnDinheiro.setOnAction(event -> showCashPane());
        btnCartaoCredito.setOnAction(event -> showCreditPane());
        btnCartaoDebito.setOnAction(event -> showDebitPane());
        btnPix.setOnAction(event -> showPixPane());

        btnFinalizarPedido.setOnAction(event -> {
            if (!metodoPagamentoSelecionado.isEmpty()) {
                finalizarPedido();
            } else {
                lblMetodoPagamento.setText("Método: Selecione um método!");
            }
        });
    }

    private void showMethodsPane() {
        methodsPane.setVisible(true);
        detailsPane.getChildren().clear();
        detailsPane.setVisible(false);
        btnDinheiro.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        btnCartaoCredito.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        btnCartaoDebito.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        btnPix.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
    }

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
        tfPago.setPromptText("Ex: 20.00");
        
        TextFormatter<String> formatterDinheiro = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9.,]*")) {
                return change;
            }
            return null;
        });
        tfPago.setTextFormatter(formatterDinheiro);

        row.getChildren().addAll(lblPago, tfPago);

        Label lblTroco = new Label("Troco: R$ 0.00");

        tfPago.textProperty().addListener((obs, oldV, newV) -> {
            try {
                double recebido = Double.parseDouble(newV.replace(',', '.'));
                double total = pedidoAtual.getTotal();
                double troco = recebido - total;
                if (troco < 0) troco = 0;
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
        
        TextFormatter<String> formatterNumero = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,16}")) {
                return change;
            }
            return null;
        });
        tfNumero.setTextFormatter(formatterNumero);

        HBox h = new HBox(8);
        TextField tfVal = new TextField();
        tfVal.setPromptText("MM/AA");
        
        tfVal.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                String filtered = newVal.replaceAll("[^0-9]", "");
                if (filtered.length() > 4) {
                    filtered = filtered.substring(0, 4);
                }
                if (filtered.length() >= 2) {
                    String formatted = filtered.substring(0, 2) + "/" + filtered.substring(2);
                    if (!newVal.equals(formatted)) {
                        tfVal.setText(formatted);
                        tfVal.positionCaret(formatted.length());
                    }
                } else if (!newVal.equals(filtered)) {
                    tfVal.setText(filtered);
                }
            }
        });
        
        PasswordField tfCvv = new PasswordField();
        tfCvv.setPromptText("CVV");
        
        TextFormatter<String> formatterCvv = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,4}")) {
                return change;
            }
            return null;
        });
        tfCvv.setTextFormatter(formatterCvv);
        
        h.getChildren().addAll(tfVal, tfCvv);

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(evt -> showMethodsPane());

        detailsPane.getChildren().addAll(title, tfNome, tfNumero, h, btnBack);
        metodoPagamentoSelecionado = "Cartão de Crédito";
        lblMetodoPagamento.setText("Método: Cartão de Crédito");
        btnCartaoCredito.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    }

    private void showDebitPane() {
        methodsPane.setVisible(false);
        detailsPane.setVisible(true);
        detailsPane.getChildren().clear();

        Label title = new Label("Pagamento - Cartão de Débito");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        TextField tfNumero = new TextField();
        tfNumero.setPromptText("Número do cartão");
        
        TextFormatter<String> formatterNumeroDebito = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,16}")) {
                return change;
            }
            return null;
        });
        tfNumero.setTextFormatter(formatterNumeroDebito);

        PasswordField tfSenha = new PasswordField();
        tfSenha.setPromptText("Senha/PIN");
        
        TextFormatter<String> formatterPin = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,6}")) {
                return change;
            }
            return null;
        });
        tfSenha.setTextFormatter(formatterPin);

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(evt -> showMethodsPane());

        detailsPane.getChildren().addAll(title, tfNumero, tfSenha, btnBack);
        metodoPagamentoSelecionado = "Cartão de Débito";
        lblMetodoPagamento.setText("Método: Cartão de Débito");
        btnCartaoDebito.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    }

    private void showPixPane() {
        methodsPane.setVisible(false);
        detailsPane.setVisible(true);
        detailsPane.getChildren().clear();

        Label title = new Label("Pagamento - PIX");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label lblChave = new Label("Chave PIX (copiar/colar):");
        TextArea taChave = new TextArea();
        taChave.setText("meu-pix@exemplo.com");
        taChave.setWrapText(true);
        taChave.setPrefRowCount(2);

        Button btnCopiar = new Button("Copiar chave");
        btnCopiar.setOnAction(evt -> {
            javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
            javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
            content.putString(taChave.getText());
            clipboard.setContent(content);
        });

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(evt -> showMethodsPane());

        detailsPane.getChildren().addAll(title, lblChave, taChave, btnCopiar, btnBack);
        metodoPagamentoSelecionado = "PIX";
        lblMetodoPagamento.setText("Método: PIX");
        btnPix.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    }

    private int gerarNumeroPedido() {
        Random random = new Random();
        return random.nextInt(100000);
    }

    

    private void finalizarPedido() {
        System.out.println("Pedido #" + String.format("%05d", pedidoAtual.getNumero()) + " finalizado com " + metodoPagamentoSelecionado);
        System.out.println("Total: R$ " + String.format("%.2f", pedidoAtual.getTotal()));

        Carrinho.limpar();

        try {
            App.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
