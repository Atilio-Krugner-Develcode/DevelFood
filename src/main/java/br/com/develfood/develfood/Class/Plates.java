package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Record.PlateFilterDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pratos")
@EqualsAndHashCode(of = "id")
public class Plates {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String nome;
    private String descricao;
    private String foto;
    private BigDecimal preco;
    private String tipo;

//    @ManyToOne
//    private Plates pratos;
//
//    @ManyToOne
//    private PlateFilter tiposDeComida;




    public Plates(PlateDTO body) {
        this.nome = body.nome();
        this.descricao = body.descricao();
        this.foto = body.foto();
        this.preco = body.preco();
    }
}
