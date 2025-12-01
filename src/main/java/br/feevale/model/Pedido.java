package br.feevale.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    private int numero;
    private transient ObservableList<ItemPedido> itens = FXCollections.observableArrayList();
    private LocalDateTime dataHora = LocalDateTime.now();
    private transient DoubleProperty totalProperty = new SimpleDoubleProperty(0.0);
    private double total; 
    private transient ObjectProperty<StatusPedido> statusProperty = new SimpleObjectProperty<>(StatusPedido.EM_PREPARO);
    private StatusPedido status = StatusPedido.EM_PREPARO; 

    private String metodoPagamento;

    public Pedido(int numero) {
        this.numero = numero;
        this.totalProperty.set(total);
        this.statusProperty.set(status);
        
        this.itens.addListener((javafx.collections.ListChangeListener.Change<? extends ItemPedido> change) -> {
            calcularTotal();
        });
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public void calcularTotal() {
        totalProperty.set(itens.stream()
                .mapToDouble(i -> i.getProduto().getPreco() * i.getQuantidade())
                .sum());
        this.total = totalProperty.get();
    }

    public void avancarStatus() {
        if (this.status == StatusPedido.EM_PREPARO) {
            setStatus(StatusPedido.PRONTO);
        } else if (this.status == StatusPedido.PRONTO) {
            setStatus(StatusPedido.ENTREGUE);
        }
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public ObservableList<ItemPedido> getItens() { return itens; }
    public void setItens(ObservableList<ItemPedido> itens) { this.itens.setAll(itens); }

    public double getTotal() { return totalProperty.get(); }
    public void setTotal(double total) { 
        this.total = total;
        this.totalProperty.set(total);
    }
    public DoubleProperty totalProperty() { return totalProperty; }

    public StatusPedido getStatus() { return statusProperty.get(); }
    public void setStatus(StatusPedido status) { 
        this.status = status;
        this.statusProperty.set(status);
    }
    public ObjectProperty<StatusPedido> statusProperty() { return statusProperty; }

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    public LocalDateTime getDataHora() { return dataHora; }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        
        out.writeDouble(totalProperty.get());
        out.writeObject(statusProperty.get());
        

        out.writeObject(new ArrayList<>(itens));
    }

    @SuppressWarnings("unchecked")
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        
        totalProperty = new SimpleDoubleProperty(0.0);
        totalProperty.set(in.readDouble());
        
        statusProperty = new SimpleObjectProperty<>(StatusPedido.EM_PREPARO);
        StatusPedido savedStatus = (StatusPedido) in.readObject();
        if (savedStatus != null) {
            statusProperty.set(savedStatus);
            this.status = savedStatus;
        }

        List<ItemPedido> listaSalva = (List<ItemPedido>) in.readObject();
        this.itens = FXCollections.observableArrayList(listaSalva);
        this.itens.addListener((javafx.collections.ListChangeListener.Change<? extends ItemPedido> change) -> {
            calcularTotal();
        });
    }
}