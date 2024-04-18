package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.ClientDTO;
import br.com.develfood.develfood.Record.RequestRestaurant;
import br.com.develfood.develfood.Repository.ClientRepository;
import br.com.develfood.develfood.Services.ClientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
    public ResponseEntity getAllClients() {
        List<Cliente> allClients = clientService.getAllClients();
        return ResponseEntity.ok(allClients);
    }

    @PostMapping("/create")
    public ResponseEntity createClient(@RequestBody @Validated ClientDTO data) {
        return clientService.createClient(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody @Validated ClientDTO data) {
        return clientService.updateClient(id, data);
    }
}
