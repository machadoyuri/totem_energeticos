package br.feevale.model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TicketArmazenamento {

    private static final String FILE_NAME = "tickets.ser";
    private static final ObservableList<Pedido> tickets = FXCollections.observableArrayList();

    static {
        loadTickets();
    }

    public static void salvarTicket(Pedido ticket) {
        boolean existe = false;
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getNumero() == ticket.getNumero()) {
                tickets.set(i, ticket);
                existe = true;
                break;
            }
        }
        if (!existe) {
            tickets.add(ticket);
        }
        saveTickets();
    }

    public static ObservableList<Pedido> getTickets() {
        return tickets;
    }

    public static void removerTicket(Pedido ticket) {
        tickets.remove(ticket);
        saveTickets();
    }

    private static void saveTickets() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(new ArrayList<>(tickets));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadTickets() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                List<Pedido> savedTickets = (List<Pedido>) ois.readObject();
                tickets.setAll(savedTickets);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}