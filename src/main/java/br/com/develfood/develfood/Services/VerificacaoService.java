package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificacaoService {

@Autowired
private ClientRepository clientRepository;





    public boolean cpfExistente(String cpf) {
        return clientRepository.existsByCpf(cpf);
    }

    public boolean telefoneExistente(String telefone) {
        return clientRepository.existsByPhone(telefone);
    }

    public boolean emailExistente(String email) {
        return clientRepository.existsByEmail(email);
    }

}
