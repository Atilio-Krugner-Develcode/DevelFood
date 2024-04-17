package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Endereco;
import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.AddressDTO;
import br.com.develfood.develfood.Record.ClientAndAddressDTO;
import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Record.RestaurantAndAddress;
import br.com.develfood.develfood.Repository.AddressRepository;
import br.com.develfood.develfood.Repository.ClientRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private RestaurantRepository repository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/list")
    public ResponseEntity<Object> entitiesAndAddresses(@PageableDefault(size = 10) Pageable pageable) {
        Page<Restaurant> restaurantPage = repository.findAll(pageable);
        Page<Cliente> customerPage = clientRepository.findAll(pageable);

        List<Object> resultList = new ArrayList<>();

        restaurantPage.forEach(restaurant -> {
            List<Endereco> endereco = restaurant.getEndereco();
            List<AddressDTO> addressDTO = endereco.stream()
                    .map(AddressDTO::new)
                    .collect(Collectors.toList());

            resultList.add(new RestaurantAndAddress(restaurant.getId(), restaurant.getNome(), restaurant.getCpf(), restaurant.getTelefone(), restaurant.getFoto(),
                    addressDTO));
        });

        customerPage.forEach(customer -> {
            List<Endereco> endereco = customer.getEndereco();
            List<AddressDTO> addressDTO = endereco.stream()
                    .map(AddressDTO::new)
                    .collect(Collectors.toList());

            resultList.add(new ClientAndAddressDTO(customer.getId(), customer.getNome(), customer.getSobrenome(), customer.getCpf(), customer.getTelefone(), customer.getFoto(),
                    addressDTO));
        });

        if (resultList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultList);
    }

    @PostMapping("/create")
    public ResponseEntity<?> postAddress(@RequestBody @Validated AddressDTO body, @RequestParam(required = false) Long restaurantId, @RequestParam(required = false) Long customerId) {

        if (restaurantId != null) {
            Optional<Restaurant> optionalRestaurant = repository.findById(restaurantId);
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

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateAddress(@PathVariable Long id, @RequestBody @Validated AddressDTO data) {
        if (id != null) {
            Optional<Endereco> optionalEndereco = addressRepository.findById(Long.valueOf(id));
            if (optionalEndereco.isPresent()) {
                Endereco endereco = optionalEndereco.get();
                endereco.setRua(data.rua());
                endereco.setNumero(data.numero());
                endereco.setBairro(data.bairro());
                endereco.setCidade(data.cidade());
                endereco.setCep(data.cep());

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

