package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Class.Restaurant;

public record RequestRestaurant(
        String id,
        String nome,
        String cpf,
        int telefone,
        String foto,
        PlateFilter plateFilter

) {
    public RequestRestaurant(Restaurant restaurant){
        this(String.valueOf(restaurant.getId()),restaurant.getNome(),restaurant.getCpf(),restaurant.getTelefone(), restaurant.getFoto(),restaurant.getPlateFilter());
    }

}
