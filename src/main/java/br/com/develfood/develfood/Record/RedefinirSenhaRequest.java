package br.com.develfood.develfood.Record;

import javax.validation.constraints.NotBlank;

public class RedefinirSenhaRequest {

    @NotBlank(message = "O código de verificação não pode estar em branco")
    private String codigoVerificacao;

    @NotBlank(message = "A nova senha não pode estar em branco")
    private String novaSenha;

    public RedefinirSenhaRequest() {
    }

    public RedefinirSenhaRequest(String codigoVerificacao, String novaSenha) {
        this.codigoVerificacao = codigoVerificacao;
        this.novaSenha = novaSenha;
    }

    public String getCodigoVerificacao() {
        return codigoVerificacao;
    }

    public void setCodigoVerificacao(String codigoVerificacao) {
        this.codigoVerificacao = codigoVerificacao;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
