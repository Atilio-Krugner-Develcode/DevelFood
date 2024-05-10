package br.com.develfood.develfood.Record;

import java.util.List;

public record RestaurantAndAddress(

        Long id,
        String nome,
        String cnpj,
        String telefone,
        String foto,

        List<AddressDTO> Endereco

) {
}
