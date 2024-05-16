package br.com.develfood.develfood.Class.Pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link br.com.develfood.develfood.Class.Plates}
 */
@Value
@AllArgsConstructor
@Builder
@Getter
public class PlatesDto implements Serializable {
    Long id;
    String name;
}