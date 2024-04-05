package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Class.Plates;

import java.math.BigDecimal;

public record PlateDTO(
        Long id,
        String nome,
        String descricao,
        String foto,
        String categoria,
        BigDecimal preco,
        PlateFilter plateFilter

) {
    public PlateDTO(Plates plates){
        this(plates.getId(), plates.getNome(), plates.getDescricao(), plates.getFoto(),plates.getCategoria(), plates.getPreco(), plates.getRestaurante().getPlateFilter());
    }
}
