package br.com.develfood.develfood.Record;


import br.com.develfood.develfood.role.UserRole;

public record RegisterDTO(String userEmail, String password, UserRole role) {

}
