package br.feevale.model;

public class ProdutoEnergetico extends Produto {

    private static final long serialVersionUID = 1L;

    private int ml;
    private String marca;
    private int estoque;
    private String sabor;

    public ProdutoEnergetico(String nome, double preco, int ml, String marca, int estoque, String sabor) {
        super(nome, preco);
        this.ml = ml;
        this.marca = marca;
        this.estoque = estoque;
        this.sabor = sabor;
    }

    public int getMl() {
        return ml;
    }

    public void setMl(int ml) {
        this.ml = ml;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    @Override
    public String getDescricaoCompleta() {
        return getNome() + " (" + ml + "ml) - " + sabor;
    }

    @Override
    public String toString() {
        return getNome() + " (" + ml + "ml, " + sabor + ") - R$ " + String.format("%.2f", getPreco());
    }
}