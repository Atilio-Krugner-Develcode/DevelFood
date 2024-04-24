package br.com.develfood.develfood.Class;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CriarPedidoDTO {


    private Long idCliente;
    private Long idPrato;
    private int quantidade;

}
