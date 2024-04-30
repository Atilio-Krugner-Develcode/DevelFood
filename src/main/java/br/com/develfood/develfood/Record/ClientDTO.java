package br.com.develfood.develfood.Record;

import javax.validation.constraints.NotBlank;

public record ClientDTO(

        Long id,

        String email,
        String nome,
        String sobrenome,
        String cpf,
        int telefone,
        String foto


) {
}
