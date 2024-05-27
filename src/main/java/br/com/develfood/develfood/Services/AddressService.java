package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Endereco;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Class.User;
import br.com.develfood.develfood.Record.AddressDTO;
import br.com.develfood.develfood.Record.ClientAndAddressDTO;
import br.com.develfood.develfood.Record.RestaurantAndAddress;
import br.com.develfood.develfood.Repository.AddressRepository;
import br.com.develfood.develfood.Repository.ClientRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import br.com.develfood.develfood.Repository.UserRepository;
import br.com.develfood.develfood.infra.security.TokenService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;


    public List<Object> getEntitiesAndAddresses(Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantRepository.findAll(pageable);
        Page<Cliente> customerPage = clientRepository.findAll(pageable);

        List<Object> resultList = new ArrayList<>();

        restaurantPage.forEach(restaurant -> {
            List<Endereco> endereco = restaurant.getEndereco();
            List<AddressDTO> addressDTO = endereco.stream()
                    .map(AddressDTO::new)
                    .collect(Collectors.toList());

            resultList.add(new RestaurantAndAddress(restaurant.getId(), restaurant.getName(), restaurant.getCnpj(), restaurant.getPhone(), restaurant.getImage(),
                    addressDTO));
        });

        customerPage.forEach(customer -> {
            List<Endereco> endereco = customer.getEndereco();
            List<AddressDTO> addressDTO = endereco.stream()
                    .map(AddressDTO::new)
                    .collect(Collectors.toList());

            resultList.add(new ClientAndAddressDTO(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getCpf(), customer.getPhone(), customer.getImage(),
                    addressDTO));
        });

        return resultList;
    }

    public Optional<Endereco> clienteEndereco(Long id) {
        return addressRepository.findById(id);
    }

    public ResponseEntity<?> createAddress(AddressDTO body, Long restaurantId, Long customerId) {
        if (restaurantId != null) {
            Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
            if (optionalRestaurant.isPresent()) {
                Restaurant restaurant = optionalRestaurant.get();
                if (!restaurant.getEndereco().isEmpty()) {
                    return ResponseEntity.badRequest().body("Este restaurante já possui um endereço. Não é permitido criar mais de um.");
                }
                Endereco newEndereco = new Endereco(body);
                newEndereco.setRestaurant(restaurant);
                addressRepository.save(newEndereco);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else if (customerId != null) {
            Optional<Cliente> optionalCustomer = clientRepository.findById(customerId);
            if (optionalCustomer.isPresent()) {
                Cliente customer = optionalCustomer.get();

                Endereco newEndereco = new Endereco(body);
                newEndereco.setCliente(customer);
                addressRepository.save(newEndereco);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().body("É necessário fornecer o ID do restaurante ou do cliente.");
        }
    }

    @Transactional
    public ResponseEntity updateAddress(Long id, AddressDTO data) {
        if (id != null) {
            Optional<Endereco> optionalEndereco = addressRepository.findById(Long.valueOf(id));
            if (optionalEndereco.isPresent()) {
                Endereco endereco = optionalEndereco.get();
                endereco.setStreet(data.rua());
                endereco.setNumber(data.numero());
                endereco.setNeigbourhood(data.bairro());
                endereco.setCity(data.cidade());
                endereco.setCep(data.cep());
                endereco.setState(data.state());

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public void deleteAddress(Long customerId, Long addressId) {
        Optional<Endereco> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Endereco address = optionalAddress.get();
            if (address.getCliente().getId().equals(customerId)) {
                addressRepository.deleteById(addressId);
            }

        }
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

    private Cliente encontrarCliente(Long userId){
        Optional<Cliente>clienteOptional = clientRepository.findByUserId(userId);
        if (clienteOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        return clienteOptional.get();
    }

    private Long validarToken(String token) {
        Long userId = tokenService.extrairIdUser(token);
        if(userId == null){
            throw new JWTVerificationException("Token invalido");
        }
        return userId;
    }

}

