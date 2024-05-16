package br.com.develfood.develfood.Class.Pedido;

import br.com.develfood.develfood.Class.Plates;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CriarPedidoDTO {
    @NotNull
    private Long idRestaurantes;
    @NotBlank
    private String formaPagamento;
    @NotNull
    private List<PedidoDTO> pedidos;

}
