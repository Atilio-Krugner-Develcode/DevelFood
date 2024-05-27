package br.com.develfood.develfood.Class.Pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link br.com.develfood.develfood.Class.Restaurant}
 */
@Value
@AllArgsConstructor
@Builder
@Getter
public class RestaurantDto implements Serializable {
    Long id;
    String name;
}