package br.feevale.model;

import java.util.ArrayList;
import java.util.List;

public class TicketStore {

    private static List<String> tickets = new ArrayList<>();

    public static void adicionarTicket(String ticket) {
        tickets.add(ticket);
    }

    public static List<String> getTickets() {
        return tickets;
    }
}
