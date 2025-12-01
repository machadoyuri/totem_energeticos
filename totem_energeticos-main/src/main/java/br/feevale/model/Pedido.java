package br.feevale.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int numero;
    private List<ItemPedido> itens = new ArrayList<>();
    private LocalDateTime dataHora = LocalDateTime.now();
    private double total;

    public Pedido(int numero) {
        this.numero = numero;
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public void calcularTotal() {
        total = itens.stream()
                .mapToDouble(i -> i.getProduto().getPreco() * i.getQuantidade())
                .sum();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
