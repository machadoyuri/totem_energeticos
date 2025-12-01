package br.feevale;

import br.feevale.model.Carrinho;
import br.feevale.model.ItemCarrinho;
import br.feevale.model.ItemPedido;
import br.feevale.model.Pedido;
import br.feevale.model.TicketArmazenamento;
import java.io.IOException;
import java.util.Random;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
            int numeroPedido;

            if (persistedPedido != null) {
                numeroPedido = persistedPedido.getNumero();
            } else {
                numeroPedido = new Random().nextInt(90000) + 10000;
            }

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

        TicketArmazenamento.salvarTicket(pedidoAtual);

        btnDinheiro.setOnAction(event -> showCashPane());
        btnCartaoCredito.setOnAction(event -> showCreditPane());
        btnCartaoDebito.setOnAction(event -> showDebitPane());
        btnPix.setOnAction(event -> showPixPane());

        btnFinalizarPedido.setOnAction(event -> handleFinalizarPedido());
    }

    private void handleFinalizarPedido() {
        if (metodoPagamentoSelecionado.isEmpty()) {
            lblMetodoPagamento.setText("Método: Selecione um método!");
            return;
        }

        boolean camposValidos = true;
        
        if ("Cartão de Crédito".equals(metodoPagamentoSelecionado)) {
            TextField tfNome = (TextField) detailsPane.lookup("#tfNome");
            TextField tfNumero = (TextField) detailsPane.lookup("#tfNumero");
            TextField tfVal = (TextField) detailsPane.lookup("#tfVal");
            PasswordField tfCvv = (PasswordField) detailsPane.lookup("#tfCvv");

            if (tfNome == null || tfNome.getText().trim().isEmpty() ||
                tfNumero == null || tfNumero.getText().trim().isEmpty() ||
                tfVal == null || tfVal.getText().trim().isEmpty() ||
                tfCvv == null || tfCvv.getText().trim().isEmpty()) {
                camposValidos = false;
            }
        } else if ("Cartão de Débito".equals(metodoPagamentoSelecionado)) {
            TextField tfNumero = (TextField) detailsPane.lookup("#tfNumero");
            PasswordField tfSenha = (PasswordField) detailsPane.lookup("#tfSenha");

            if (tfNumero == null || tfNumero.getText().trim().isEmpty() ||
                tfSenha == null || tfSenha.getText().trim().isEmpty()) {
                camposValidos = false;
            }
        }

        if (!camposValidos) {
            lblMetodoPagamento.setText("Preencha todos os campos!");
            return;
        }

        finalizarPedido();
    }

    private void showCashPane() {
        methodsPane.setVisible(false);
        detailsPane.setVisible(true);
        detailsPane.getChildren().clear();

        Label title = new Label("Pagamento em Dinheiro");
        TextField tfPago = new TextField();
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
        btnBack.setOnAction(evt -> goBackToCart());

        detailsPane.getChildren().addAll(title, new Label(lblValorTotal.getText()), tfPago, lblTroco, btnBack);
        metodoPagamentoSelecionado = "Dinheiro";
        lblMetodoPagamento.setText("Método: Dinheiro");
        atualizarMetodoNoTicket();
    }

    private void showCreditPane() {
        methodsPane.setVisible(false);
        detailsPane.setVisible(true);
        detailsPane.getChildren().clear();

        TextField tfNome = new TextField();
        tfNome.setPromptText("Nome no cartão");
        tfNome.setId("tfNome");

        TextField tfNumero = new TextField();
        tfNumero.setPromptText("Número do cartão");
        tfNumero.setId("tfNumero");

        TextField tfVal = new TextField();
        tfVal.setPromptText("MM/AA");
        tfVal.setId("tfVal");

        UnaryOperator<TextFormatter.Change> dateFilter = change -> {
            if (change.getControlNewText().length() > 5) {
                return null;
            }
            if (change.isContentChange()) {
                String newText = change.getControlNewText();
                if (newText.length() == 2 && change.getText().length() > 0) { 
                    change.setText(change.getText() + "/");
                    change.setCaretPosition(change.getControlNewText().length() + 1);
                    change.setAnchor(change.getCaretPosition());
                }
            }
            return change;
        };
        tfVal.setTextFormatter(new TextFormatter<>(dateFilter));

        PasswordField tfCvv = new PasswordField();
        tfCvv.setPromptText("CVV");
        tfCvv.setId("tfCvv");

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(evt -> goBackToCart());

        HBox h = new HBox(8, tfVal, tfCvv);
        detailsPane.getChildren().addAll(new Label("Pagamento - Cartão de Crédito"), tfNome, tfNumero, h, btnBack);

        metodoPagamentoSelecionado = "Cartão de Crédito";
        lblMetodoPagamento.setText("Método: Cartão de Crédito");
        atualizarMetodoNoTicket();
    }

    private void showDebitPane() {
        methodsPane.setVisible(false);
        detailsPane.setVisible(true);
        detailsPane.getChildren().clear();

        TextField tfNumero = new TextField();
        tfNumero.setPromptText("Número do cartão");
        tfNumero.setId("tfNumero");

        PasswordField tfSenha = new PasswordField();
        tfSenha.setPromptText("Senha");
        tfSenha.setId("tfSenha");

        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(evt -> goBackToCart());

        detailsPane.getChildren().addAll(new Label("Pagamento - Cartão de Débito"), tfNumero, tfSenha, btnBack);

        metodoPagamentoSelecionado = "Cartão de Débito";
        lblMetodoPagamento.setText("Método: Cartão de Débito");
        atualizarMetodoNoTicket();
    }

    private void showPixPane() {
        methodsPane.setVisible(false);
        detailsPane.setVisible(true);
        detailsPane.getChildren().clear();

        TextArea taChave = new TextArea("meu-pix@exemplo.com");
        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(evt -> goBackToCart());

        detailsPane.getChildren().addAll(new Label("Pagamento - PIX"), taChave, btnBack);

        metodoPagamentoSelecionado = "PIX";
        lblMetodoPagamento.setText("Método: PIX");
        atualizarMetodoNoTicket();
    }

    private void atualizarMetodoNoTicket() {
        if(pedidoAtual != null) {
            pedidoAtual.setMetodoPagamento(metodoPagamentoSelecionado);
            TicketArmazenamento.salvarTicket(pedidoAtual);
        }
    }

    private void goBackToCart() {
        try {
            switchToSecondary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finalizarPedido() {
        pedidoAtual.setMetodoPagamento(metodoPagamentoSelecionado);
        TicketArmazenamento.salvarTicket(pedidoAtual); 
        
        Carrinho.limpar();
        persistedPedido = null; 

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