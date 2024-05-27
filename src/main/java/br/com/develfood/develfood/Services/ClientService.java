package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Endereco;
import br.com.develfood.develfood.Class.User;
import br.com.develfood.develfood.Record.ClientDTO;
import br.com.develfood.develfood.Record.RegisterDTO;
import br.com.develfood.develfood.Repository.AddressRepository;
import br.com.develfood.develfood.Repository.ClientRepository;
import br.com.develfood.develfood.Repository.UserRepository;
import br.com.develfood.develfood.infra.security.TokenService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.mail.Address;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepository addressRepository;

    public List<Cliente> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id) {
        return clientRepository.findById(id);
    }

    private String extrairToken (HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            throw new JWTVerificationException("Token invalido");
        }
        return authorization.substring(7);
    }

    private User encontrarUser(Long userId){
        Optional<User>userOptional = userRepository.findById(String.valueOf(userId));
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
         return userOptional.get();
    }

    private Cliente encontrarCliente(User user){
        Optional<Cliente>clienteOptional = clientRepository.findByUser(user);
        if (clienteOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        return clienteOptional.get();
    }

    private Map<String, Object> criarRespostaCliente(Cliente cliente) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", cliente.getId());
        response.put("firstName", cliente.getFirstName());
        response.put("lastName", cliente.getLastName());
        response.put("email", cliente.getEmail());
        response.put("phone", cliente.getPhone());
        response.put("image", cliente.getImage());
        response.put("cpf", cliente.getCpf());
        response.put("Endereco", cliente.getEndereco());
        return response;
    }

    public Map<String, Object> visualizarCliente(HttpServletRequest request) {
        String token = extrairToken(request);
        Long userId = validarToken(token);
        User usuario = encontrarUser(userId);
        Cliente cliente = encontrarCliente(usuario);
        return criarRespostaCliente(cliente);
    }

    private Long validarToken(String token) {
        Long userId = tokenService.extrairIdUser(token);
        if(userId == null){
            throw new JWTVerificationException("Token invalido");
        }
        return userId;
    }

    @Transactional
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

            RegisterDTO registerDTO = new RegisterDTO(data.email(), data.password(), "ADMIN");

            User newUser = userService.registerUser(registerDTO);

            Cliente newCliente = new Cliente();
            newCliente.setEmail(data.email());
            newCliente.setFirstName(data.nome());
            newCliente.setLastName(data.sobrenome());
            newCliente.setCpf(data.cpf());
            newCliente.setPhone(data.telefone());
            newCliente.setImage(data.foto());
            newCliente.setUser(newUser);

            Endereco address = new Endereco(data.cep(),data.cidade(), data.bairro(), data.numero(), data.rua(), data.state());
            address.setCliente(newCliente);
            addressRepository.save(address);


            Cliente savedCliente = clientRepository.save(newCliente);

            String responseMessage = "Cliente criado com sucesso! ID: " + savedCliente.getId();

            return ResponseEntity.ok(responseMessage);

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
