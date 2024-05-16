package br.com.develfood.develfood.Class;

import br.com.develfood.develfood.Record.AddressDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(of = "id")
public class Endereco {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Long id;
    private String street;
    private String number;
    private String city;
    private String neigbourhood;
    private String cep;
    private String state;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

    public Endereco(AddressDTO body) {
        this.street = body.rua();
        this.number = String.valueOf(body.numero());
        this.city = body.cidade();
        this.neigbourhood = body.bairro();
        this.cep = body.cep();
        this.state = body.state();
    }

    public Endereco(String cep, String cidade, String bairro, String numero, String rua, String state) {
        this.state = state;
        this.number = numero;
        this.street = rua;
        this.neigbourhood = bairro;
        this.city = cidade;
        this.cep = cep;
    }
}
