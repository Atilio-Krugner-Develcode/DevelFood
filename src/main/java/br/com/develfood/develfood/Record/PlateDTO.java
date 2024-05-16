package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Class.Plates;

import java.math.BigDecimal;

public record PlateDTO(
        Long id,
        String name,
        String description,
        String image,
        String category,
        BigDecimal price,
        PlateFilter plateFilter

) {
    public PlateDTO(Plates plates) {
        this(plates.getId(), plates.getName(), plates.getDescription(), plates.getImage(), plates.getCategory(), plates.getPrice(), plates.getRestaurante().getPlateFilter());
    }


    
}