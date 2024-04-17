package br.com.develfood.develfood.Record;

import br.com.develfood.develfood.Class.Endereco;


public record AddressDTO(
        Long id,
        String rua,
        int numero,
        String cidade,
        String bairro,
        String cep
) {
    public AddressDTO(Endereco endereco){
        this(endereco.getId(), endereco.getRua(), endereco.getNumero(), endereco.getCidade(), endereco.getBairro(), endereco.getCep());
    }
}
