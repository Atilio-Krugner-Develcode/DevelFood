package br.com.develfood.develfood.Class.Pedido;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PedidoDTO {
    private String observacao;
    @NotNull
    private Integer quantidade;
    @NotNull
    private Long idPrato;

}
