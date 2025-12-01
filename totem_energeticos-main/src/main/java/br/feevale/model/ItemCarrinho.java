package br.feevale.model;

public class ItemCarrinho {
    private ProdutoEnergetico produto;
    private int quantidade;

    public ItemCarrinho(ProdutoEnergetico produto, int quantidade) {
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

    public void incrementar() {
        this.quantidade++;
    }

    public void decrementar() {
        if (this.quantidade > 1) {
            this.quantidade--;
        }
    }

    public double getSubtotal() {
        return CalculadoraDescontos.calcularSubtotal(this);
    }

    public double getDesconto() {
        return CalculadoraDescontos.calcularDescontoItem(produto.getPreco(), quantidade);
    }

    public double getTotalComDesconto() {
        return CalculadoraDescontos.calcularTotalComDesconto(produto.getPreco(), quantidade);
    }

    @Override
    public String toString() {
        return produto.getNome() + " (" + produto.getMl() + "ml) - R$ " 
            + String.format("%.2f", produto.getPreco()) + " x" + quantidade;
    }
}
