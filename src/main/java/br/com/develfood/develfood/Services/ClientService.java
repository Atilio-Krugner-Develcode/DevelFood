package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Record.ClientDTO;
import br.com.develfood.develfood.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Cliente> getAllClients() {
        return clientRepository.findAll();
    }

    public ResponseEntity<?> createClient(ClientDTO data) {
        try {
            if (clientRepository.existsByEmail(data.email())) {
                throw new DataIntegrityViolationException("Já existe um cliente com este email.");
            }

            if (clientRepository.existsByCpf(data.cpf())) {
                throw new DataIntegrityViolationException("Já existe um cliente com este CPF.");
            }

            if (clientRepository.existsByPhone(String.valueOf(data.telefone()))) {
                throw new DataIntegrityViolationException("Já existe um cliente com este telefone.");
            }

            Cliente newCliente = new Cliente();
            newCliente.setEmail(data.email());
            newCliente.setFirstName(data.nome());
            newCliente.setLastName(data.sobrenome());
            newCliente.setCpf(data.cpf());
            newCliente.setPhone(data.telefone());
            newCliente.setImage(data.foto());

            clientRepository.save(newCliente);

            return ResponseEntity.ok("Cliente criado com sucesso!");
        } catch (DataIntegrityViolationException e) {
            throw e;
        }
    }

    @Transactional
    public ResponseEntity updateClient(Long id, ClientDTO data) {
        if (id != null) {
            Optional<Cliente> optionalCliente = clientRepository.findById(id);
            if (optionalCliente.isPresent()) {
                Cliente cliente = optionalCliente.get();
                cliente.setFirstName(data.nome());
                cliente.setLastName(data.sobrenome());
                cliente.setPhone(data.telefone());
                cliente.setImage(data.foto());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
