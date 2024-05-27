package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.Pedido.ItemPedido;
import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Class.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantePedidoDTO {
    private Restaurant restaurante;
    private Pedido pedido;
    private List<ItemPedido> itens;

    // Construtor vazio
    public RestaurantePedidoDTO() {
        this.itens = new ArrayList<>();
    }



    public Restaurant getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurant restaurante) {
        this.restaurante = restaurante;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemPedido item) {
        this.itens.add(item);
    }
}