module br.feevale {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens br.feevale to javafx.fxml;
    opens br.feevale.controller to javafx.fxml;

    exports br.feevale;
    exports br.feevale.controller;
    exports br.feevale.model;
}
