package br.feevale.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private void fazerLogin() {
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();

        if (usuario.equals("ADMIN") && senha.equals("admfeevale")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Login realizado com sucesso!");
            alert.showAndWait();

            abrirTelaTickets();
            Stage stage = (Stage) campoUsuario.getScene().getWindow();
            stage.close();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Credenciais incorretas");
            alert.setContentText("Usuário ou senha inválidos!");
            alert.showAndWait();
        }
    }

    private void abrirTelaTickets() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/feevale/tickets.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Tickets de Pedidos");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
