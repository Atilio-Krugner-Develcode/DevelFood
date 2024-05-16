package br.com.develfood.develfood.Class.Pedido;

import br.com.develfood.develfood.Record.ClientAndAddressDTO;
import br.com.develfood.develfood.Record.ClientDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoResponseDTO {
    private Long restauranteId;
    private List<PratoResponseDTO> pratos;
    private String observacao;
    private BigDecimal totalPreco;
    private String formaPagamento;
    private Long pedidoId;
    private ClientAndAddressDTO cliente;
    private String status;
    private LocalDate dataCriacao;
}
