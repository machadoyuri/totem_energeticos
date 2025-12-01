package br.feevale.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LiveOrderService {

    private static final ObservableList<String> activeOrders = FXCollections.observableArrayList();

    public static ObservableList<String> getActiveOrders() {
        return activeOrders;
    }

    public static void addOrder(String orderSummary) {
        if (orderSummary != null && !activeOrders.contains(orderSummary)) {
            activeOrders.add(orderSummary);
        }
    }

    public static void removeOrder(String orderSummary) {
        if (orderSummary != null) {
            activeOrders.remove(orderSummary);
        }
    }
}
