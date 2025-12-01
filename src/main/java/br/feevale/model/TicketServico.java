package br.feevale.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketServico {

    private static final TicketServico INSTANCE = new TicketServico();
    private final ObservableList<Ticket> tickets = FXCollections.observableArrayList();
    private final ObservableList<Pedido> activeSessions = FXCollections.observableArrayList();

    private TicketServico() {
    }

    public static TicketServico getInstance() {
        return INSTANCE;
    }

    public ObservableList<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public ObservableList<Pedido> getActiveSessions() {
        return activeSessions;
    }

    public void addActiveSession(Pedido p) {
        if (p != null && !activeSessions.contains(p)) {
            activeSessions.add(p);
        }
    }

    public void removeActiveSession(Pedido p) {
        if (p != null) {
            activeSessions.remove(p);
        }
    }
}
