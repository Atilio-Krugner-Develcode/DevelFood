package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Record.PlateFilterDTO;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@Table(name = "plate_filter")
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PlateFilter {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String name;



    public PlateFilter(PlateFilterDTO data) {
        this.name = data.name();
    }


}
