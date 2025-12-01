package br.feevale.model;

public class DescontoCombo implements ICalculadoraDescontos {

    @Override
    public double aplicarDesconto(double total) {
        return total - 5;
    }
}
