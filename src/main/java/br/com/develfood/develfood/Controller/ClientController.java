package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.ClientDTO;
import br.com.develfood.develfood.Record.RequestRestaurant;
import br.com.develfood.develfood.Repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/all")
    public ResponseEntity getAllClient(){
        var allClient = clientRepository.findAll();
        return ResponseEntity.ok(allClient);
    }


    @PostMapping("/create")
    public ResponseEntity postClient(@RequestBody @Validated ClientDTO data){
        Cliente newProdutos = new Cliente(data);
        clientRepository.save(newProdutos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody @Validated ClientDTO data) {
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
