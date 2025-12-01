package br.feevale.model;

import java.io.Serializable;

public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProdutoEnergetico produto;
    private int quantidade;

    public ItemPedido(ProdutoEnergetico produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ProdutoEnergetico getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEnergetico produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
