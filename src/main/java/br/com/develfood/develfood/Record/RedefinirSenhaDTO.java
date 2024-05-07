package br.com.develfood.develfood.Record;

import javax.validation.constraints.NotBlank;

public class RedefinirSenhaDTO {

    @NotBlank(message = "A nova senha não pode estar em branco")
    private String novaSenha;


    public RedefinirSenhaDTO() {
    }

    public RedefinirSenhaDTO(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
