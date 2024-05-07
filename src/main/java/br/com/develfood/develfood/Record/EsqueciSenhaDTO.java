package br.com.develfood.develfood.Record;

import javax.validation.constraints.NotBlank;

public class EsqueciSenhaDTO {

    @NotBlank(message = "O login não pode estar em branco")
    private String login;


    public EsqueciSenhaDTO() {
    }

    public EsqueciSenhaDTO(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
