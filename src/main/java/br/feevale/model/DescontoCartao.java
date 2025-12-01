package br.feevale.model;

public class DescontoCartao implements ICalculadoraDescontos {

    @Override
    public double aplicarDesconto(double total) {
        return total * 0.90;
    }
}
