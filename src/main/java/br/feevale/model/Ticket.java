package br.feevale.model;

public class Ticket {
    private int id;
    private String descricao;
    private double valor;

    public Ticket(int id, String descricao, double valor) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
}
