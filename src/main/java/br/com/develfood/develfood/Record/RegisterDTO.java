package br.com.develfood.develfood.Record;


import br.com.develfood.develfood.Role.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {

}
