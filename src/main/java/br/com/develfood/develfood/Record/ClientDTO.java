package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.Endereco;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

public record ClientDTO(
        Long id,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email,

        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O sobrenome é obrigatório")
        String sobrenome,

        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato 'xxx.xxx.xxx-xx'")
        String cpf,

        @Positive(message = "O telefone deve ser um número positivo")
        String telefone,

        String user,
        String password,
        String role,
        String rua,
        String numero,
        String cidade,
        String bairro,
        String cep,
        String state,
        String foto

) {
}
