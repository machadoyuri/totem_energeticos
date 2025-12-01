package br.feevale.model;

import java.util.ArrayList;
import java.util.List;

public class TicketStore {

    private static List<Pedido> tickets = new ArrayList<>();

    public static void adicionarTicket(Pedido ticket) {
        tickets.add(ticket);
    }

    public static List<Pedido> getTickets() {
        return tickets;
    }

    public static void removerTicket(Pedido ticket) {
        tickets.remove(ticket);
    }
}
