package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Record.ClientDTO;
import br.com.develfood.develfood.Services.ClientService;
import br.com.develfood.develfood.Services.VerificacaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private VerificacaoService verificacaoService;

    @GetMapping("/all")
    public ResponseEntity getAllClients() {
        List<Cliente> allClients = clientService.getAllClients();
        return ResponseEntity.ok(allClients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = clientService.getClienteById(id);
        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/visualizar")
    public ResponseEntity<?> visualizarCliente(HttpServletRequest request) {
        try {
            Map<String, Object> clienteData = clientService.visualizarCliente(request);
            return ResponseEntity.ok(clienteData);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody ClientDTO data) {
        try {
            return clientService.createClient(data);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verificar-cpf")
    public ResponseEntity<String> verificarCpfExistente(@RequestBody ClientDTO request) {
        String cpf = request.cpf();

        if (verificacaoService.cpfExistente(cpf)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("CPF já existente no banco de dados.");
        } else {
            return ResponseEntity.ok("CPF ainda não registrado.");
        }
    }

    @PostMapping("/verificar-telefone")
    public ResponseEntity<String> verificarTelefoneExistente(@RequestBody ClientDTO request) {
        String telefone = request.telefone();

        if (verificacaoService.telefoneExistente(telefone)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Telefone já existente no banco de dados.");
        } else {
            return ResponseEntity.ok("Telefone ainda não registrado.");
        }
    }

    @PostMapping("/verificar-email")
    public ResponseEntity<String> verificarEmailExistente(@RequestBody ClientDTO request) {
        String email = request.email();

        if (verificacaoService.emailExistente(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("E-mail já existente no banco de dados.");
        } else {
            return ResponseEntity.ok("E-mail ainda não registrado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody @Validated ClientDTO data) {
        return clientService.updateClient(id, data);
    }
}

