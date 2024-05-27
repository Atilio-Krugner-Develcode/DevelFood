package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.PlateFilter;

import java.util.List;

public record RestauranteComPratosDTO(

        Long id,
        String nome,
        String cnpj,
        String telefone,
        String foto,

        PlateFilter plateFilter,
        List<PlateDTO>Pratos

) {
}
