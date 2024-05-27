package br.com.develfood.develfood.Record;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Getter
@Setter
public class VerificarCodigoDTO {

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotBlank(message = "O código é obrigatório.")
    private String codigo;

}