package br.com.develfood.develfood.Record;

import java.util.List;

public record ClientAndAddressDTO(

        Long id,

        String nome,
        String sobrenome,
        String cpf,
        int telefone,
        String foto,
        List<AddressDTO> Endereco

) {
}
