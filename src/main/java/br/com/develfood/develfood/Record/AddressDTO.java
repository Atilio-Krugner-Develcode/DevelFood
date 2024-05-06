package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.Endereco;
import br.com.develfood.develfood.Class.Restaurant;

public record AddressDTO(
        Long id,
        String rua,
        int numero,
        String cidade,
        String bairro,
        String cep,
        String state
) {
    public AddressDTO(Endereco endereco){
        this(endereco.getId(), endereco.getRua(), endereco.getNumero(), endereco.getCidade(), endereco.getBairro(), endereco.getCep(), endereco.getState());
    }
}
