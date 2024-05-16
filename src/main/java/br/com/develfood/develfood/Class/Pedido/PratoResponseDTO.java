package br.com.develfood.develfood.Class.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public  class PratoResponseDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private Integer quantidade;
    private String observacao;
}