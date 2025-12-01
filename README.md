# ⚡ Totem de Autoatendimento - Venda de Energéticos

Este projeto é uma aplicação Desktop desenvolvida em **Java** com interface gráfica **JavaFX**, simulando o fluxo completo de um quiosque de autoatendimento.

O sistema foi projetado para atender aos requisitos da disciplina de Programação Orientada a Objetos, demonstrando a aplicação prática de pilares como Herança, Polimorfismo, Encapsulamento e Abstração.

---

## 📋 Funcionalidades

### 🛒 Módulo do Cliente (Totem)
* **Cardápio Interativo:** Visualização de produtos energéticos com detalhes (ml, sabor, preço).
* **Gestão de Carrinho:** Adição e remoção de itens, cálculo automático de subtotal e total.
* **Pagamento Simulado:**
  * Validação rigorosa de campos (Data, CVV, Senha).
  * **Correção de Usabilidade:** Máscara de data inteligente que permite edição (backspace) sem travamentos.
  * Suporte a múltiplos métodos: Crédito, Débito, Dinheiro e PIX.
* **Geração de Pedido:** Criação de um ID único para cada transação.

### 🛡️ Módulo do Administrador (Gestão)
* **Login Seguro:** Acesso restrito via autenticação.
* **Monitoramento de Pedidos:** Visualização em tempo real dos pedidos realizados no totem.
* **Gestão de Status:** Controle do fluxo de produção com indicação visual por cores:
  * 🟡 **Em Preparo**
  * 🔵 **Pronto**
  * 🟢 **Entregue**
* **Histórico:** Persistência de dados (salvamento em arquivo) e opção de exclusão de registros.

---

## 🔐 Credenciais de Acesso (Admin)

Para acessar a área administrativa e gerenciar os tickets, utilize:

| Campo | Valor |
| :--- | :--- |
| **Usuário** | `ADMIN` |
| **Senha** | `admfeevale` |

---

## 🛠️ Tecnologias e Conceitos Aplicados

O projeto foi estruturado seguindo o padrão **MVC (Model-View-Controller)**.

### Conceitos de Orientação a Objetos (POO):
* **Abstração:** Classe base `Produto` (abstrata) define o contrato para todos os itens vendáveis.
* **Herança:** A classe `ProdutoEnergetico` herda características de `Produto`, reaproveitando código.
* **Polimorfismo:** Listas de itens (`Carrinho`, `ItemPedido`) manipulam objetos de forma genérica.
* **Encapsulamento:** Todos os atributos são protegidos (`private/protected`), acessíveis apenas via métodos controlados.
* **Composição:** O `Pedido` gerencia o ciclo de vida de seus `ItemPedido`. Se o pedido deixa de existir, os itens associados também.

### Outros Recursos Técnicos:
* **JavaFX:** Para construção da interface gráfica (GUI).
* **Collections API:** Uso de `ArrayList` e `ObservableList` para manipulação de dados.
* **Enums:** Controle rígido de estados com `StatusPedido`.
* **Serialização:** Persistência de dados em arquivo binário (`tickets.ser`).

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
* Java JDK 17 ou superior.
* IDE de sua preferência (Eclipse, IntelliJ, VS Code) com suporte a JavaFX.

### Passos
1. Clone este repositório:
   ```bash
   git clone [https://github.com/SEU-USUARIO/NOME-DO-REPO.git](https://github.com/SEU-USUARIO/NOME-DO-REPO.git)
