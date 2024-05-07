package br.com.develfood.develfood.Record;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EsqueciSenhaDTO {

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "Formato de email inválido")
    private String email;

    public EsqueciSenhaDTO() {
    }

    public EsqueciSenhaDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
