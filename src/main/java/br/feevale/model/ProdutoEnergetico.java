package br.feevale.model;

public class ProdutoEnergetico {

    private String nome;
    private double preco;
    private int ml;
    private String marca;
    private int estoque;
    private String sabor;

    public ProdutoEnergetico(String nome, double preco, int ml, String marca, int estoque, String sabor) {
        this.nome = nome;
        this.preco = preco;
        this.ml = ml;
        this.marca = marca;
        this.estoque = estoque;
        this.sabor = sabor;
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
    public String toString() {
        return nome + " (" + ml + "ml, " + sabor + ") - R$ " + String.format("%.2f", preco);
    }
}
