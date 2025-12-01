package br.feevale.controller;

import java.util.ArrayList;
import java.util.List;

import br.feevale.model.ProdutoEnergetico;

public class CardapioEnergeticos {

    private List<ProdutoEnergetico> produtos = new ArrayList<>();

    public CardapioEnergeticos() {
        produtos.add(new ProdutoEnergetico("Red Bull", 8.99, 250, "Red Bull", 0, null));
        produtos.add(new ProdutoEnergetico("Monster Energy", 10.99, 473, "Monster", 0, null));
        produtos.add(new ProdutoEnergetico("TNT Energy Drink", 6.99, 269, "TNT", 0, null));
        produtos.add(new ProdutoEnergetico("Baly", 11.50, 2000, "Baly", 0, null));
        produtos.add(new ProdutoEnergetico("Burn", 5.99, 260, "Burn", 0, null));
        produtos.add(new ProdutoEnergetico("Fusion", 5.80, 473, "Fusion", 0, null));
        produtos.add(new ProdutoEnergetico("Reign", 10.99, 473, "Reign", 0, null));
        produtos.add(new ProdutoEnergetico("Flying Horse", 13.00, 2000, "Flying Horse", 0, null));
        produtos.add(new ProdutoEnergetico("Vulcano", 16.99, 2000, "Vulcano", 0, null));
        produtos.add(new ProdutoEnergetico("Extra Power", 10.50, 1000, "Extra Power", 0, null));
        produtos.add(new ProdutoEnergetico("Vibe", 13.00, 2000, "Vibe", 0, null));
        produtos.add(new ProdutoEnergetico("Furioso", 8.85, 2000, "Furioso", 0, null));
        produtos.add(new ProdutoEnergetico("Red Horse", 10.40, 2000, "Red Horse", 0, null));
        produtos.add(new ProdutoEnergetico("Tsunami", 6.00, 2000, "Tsunami", 0, null));
        produtos.add(new ProdutoEnergetico("Night Power", 15.00, 2000, "Night Power", 0, null));
        produtos.add(new ProdutoEnergetico("Long Night", 6.00, 2000, "Long Night", 0, null));
        produtos.add(new ProdutoEnergetico("Big Power", 11.00, 2000, "Big Power", 0, null));
        produtos.add(new ProdutoEnergetico("Atomic", 7.00, 270, "Atomic", 0, null));
        produtos.add(new ProdutoEnergetico("220V", 10.00, 2000, "220V", 0, null));
        produtos.add(new ProdutoEnergetico("Engov UP", 9.00, 269, "Engov", 0, null));
        produtos.add(new ProdutoEnergetico("Dopamina", 6.00, 269, "Dopamina", 0, null));
        produtos.add(new ProdutoEnergetico("Organique", 7.00, 269, "Organique", 0, null));
        produtos.add(new ProdutoEnergetico("Wewi Energy", 6.00, 269, "Wewi", 0, null));
        produtos.add(new ProdutoEnergetico("Life Strong", 8.00, 473, "Life Strong", 0, null));
        produtos.add(new ProdutoEnergetico("Start", 8.30, 2000, "Start", 0, null));
        produtos.add(new ProdutoEnergetico("Mood", 8.00, 269, "Mood", 0, null));
        produtos.add(new ProdutoEnergetico("Guara Mix", 3.00, 290, "Guara Mix", 0, null));
        produtos.add(new ProdutoEnergetico("Hype", 10.00, 355, "Hype", 0, null));
        produtos.add(new ProdutoEnergetico("Bivolt", 8.50, 2000, "Bivolt", 0, null));
        produtos.add(new ProdutoEnergetico("Itts", 11.00, 269, "Itts", 0, null));
    }

    public void adicionarProduto(ProdutoEnergetico p) {
        produtos.add(p);
    }

    public List<ProdutoEnergetico> getProdutos() {
        return produtos;
    }
}
