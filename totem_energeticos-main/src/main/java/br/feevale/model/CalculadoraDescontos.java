package br.feevale.model;

public class CalculadoraDescontos {

    public static int calcularUnidadesAPagar(int quantidade) {
        if (quantidade < 3) {
            return quantidade;
        }
        int descricoes = quantidade / 3;
        return quantidade - descricoes;
    }

    public static double calcularDescontoItem(double precoUnitario, int quantidade) {
        int unidadesAPagar = calcularUnidadesAPagar(quantidade);
        double totalSemDesconto = precoUnitario * quantidade;
        double totalComDesconto = precoUnitario * unidadesAPagar;
        return totalSemDesconto - totalComDesconto;
    }

    public static double calcularTotalComDesconto(double precoUnitario, int quantidade) {
        int unidadesAPagar = calcularUnidadesAPagar(quantidade);
        return precoUnitario * unidadesAPagar;
    }

    public static double calcularSubtotal(ItemCarrinho item) {
        return item.getProduto().getPreco() * item.getQuantidade();
    }
}
