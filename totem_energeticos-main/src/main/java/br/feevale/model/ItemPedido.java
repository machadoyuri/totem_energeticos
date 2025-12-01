package br.feevale.model;

public class ItemPedido {

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
