package br.feevale.model;

import java.io.Serializable;

public abstract class Produto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    protected String nome;
    protected double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public abstract String getDescricaoCompleta();
    
    @Override
    public String toString() {
        return nome + " - R$ " + String.format("%.2f", preco);
    }
}