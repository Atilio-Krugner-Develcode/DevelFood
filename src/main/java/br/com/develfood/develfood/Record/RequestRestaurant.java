package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Class.Restaurant;

import java.math.BigDecimal;

public record RequestRestaurant(
        Long id,
        String nome,
        String cnpj,
        String telefone,
        String foto,
        PlateFilter plateFilter,
        BigDecimal nota


) {
    public RequestRestaurant(Restaurant restaurant){
        this(Long.valueOf(String.valueOf(restaurant.getId())),restaurant.getName(),restaurant.getCnpj(),restaurant.getPhone(), restaurant.getImage(),restaurant.getPlateFilter(), (BigDecimal) restaurant.getAvaliacoes());
    }

}
