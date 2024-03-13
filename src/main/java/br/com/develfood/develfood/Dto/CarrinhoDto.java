package br.com.develfood.develfood.Dto;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public class CarrinhoDto {
    ClienteRepository clienteRepository;

    @GetMapping("/{id}")
    public ResponseEntity getCliente(@PathVariable String id){
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity postCliente(@RequestBody @Validated RequestCliente data) {
        Cliente newCliente = new Cliente(data);
        var createCliente = clienteRepository.save(newCliente);
        return ResponseEntity.ok(createCliente);
    }
}
