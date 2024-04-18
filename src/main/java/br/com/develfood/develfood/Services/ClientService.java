package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Record.ClientDTO;
import br.com.develfood.develfood.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Cliente newCliente = new Cliente(data);
        clientRepository.save(newCliente);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity updateClient(Long id, ClientDTO data) {
        if (id != null) {
            Optional<Cliente> optionalCliente = clientRepository.findById(id);
            if (optionalCliente.isPresent()) {
                Cliente cliente = optionalCliente.get();
                cliente.setNome(data.nome());
                cliente.setSobrenome(data.sobrenome());
                cliente.setTelefone(data.telefone());
                cliente.setFoto(data.foto());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
