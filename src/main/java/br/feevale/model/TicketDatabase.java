package br.feevale.model;

import java.util.ArrayList;
import java.util.List;

public class TicketDatabase {

    public static class Ticket {
        public int numero;
        public double valor;
        public String metodo;

        public Ticket(int numero, double valor, String metodo) {
            this.numero = numero;
            this.valor = valor;
            this.metodo = metodo;
        }
    }

    private static final List<Ticket> tickets = new ArrayList<>();

    public static void adicionarTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public static List<Ticket> getTickets() {
        return tickets;
    }
}
