package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.Endereco;

public record AddressDTO(
        Long id,
        String rua,
        String numero,
        String cidade,
        String bairro,
        String cep,
        String state
) {
    public AddressDTO(Endereco endereco){
        this(endereco.getId(), endereco.getStreet(), endereco.getNumber(), endereco.getCity(), endereco.getNeigbourhood(), endereco.getCep(), endereco.getState());
    }
}
