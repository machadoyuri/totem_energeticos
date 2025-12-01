package br.feevale.model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private static List<ItemCarrinho> itens = new ArrayList<>();
    private static boolean modificado = false;

    public static void adicionar(ProdutoEnergetico p) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getNome().equals(p.getNome())) {
                item.incrementar();
                modificado = true;
                return;
            }
        }
        itens.add(new ItemCarrinho(p, 1));
        modificado = true;
    }

    public static List<ItemCarrinho> getItens() {
        return itens;
    }

    public static void removerItem(ItemCarrinho item) {
        itens.remove(item);
        modificado = true;
    }

    public static void limpar() {
        itens.clear();
        modificado = true;
    }

    public static boolean isModificado() {
        return modificado;
    }

    public static void setModificado(boolean value) {
        modificado = value;
    }
}
