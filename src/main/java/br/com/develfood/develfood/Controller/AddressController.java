package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Endereco;
import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.AddressDTO;
import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Record.RestaurantAndAddress;
import br.com.develfood.develfood.Repository.AddressRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/list")
    public ResponseEntity<Page<RestaurantAndAddress>> restaurantAndAddress(@PageableDefault(size = 10) Pageable pageable) {
        Page<Restaurant> restaurantesPage = repository.findAll(pageable);

        Page<RestaurantAndAddress> restaurantAndAddresses = restaurantesPage.map(restaurante -> {
            List<Endereco> endereco = restaurante.getEndereco();
            List<AddressDTO> addressDTO = endereco.stream()
                    .map(AddressDTO::new)
                    .collect(Collectors.toList());

            return new RestaurantAndAddress(restaurante.getId(), restaurante.getNome(), restaurante.getCpf(), restaurante.getTelefone(), restaurante.getFoto(),
                    addressDTO);
        });

        if (restaurantAndAddresses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(restaurantAndAddresses);
    }

    @PostMapping("/create")
    public ResponseEntity<?> postAddress(@RequestBody @Validated AddressDTO body, @RequestParam Long restaurantId) {
        Optional<Restaurant> optionalRestaurant = repository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            if (restaurant.getEndereco() != null) {
                return ResponseEntity.badRequest().body("Este restaurante já possui um endereço. Não é permitido criar mais de um.");
            }
            Endereco newEndereco = new Endereco(body);
            newEndereco.setRestaurant(restaurant);
            addressRepository.save(newEndereco);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateAddress (@PathVariable Long id, @RequestBody @Validated AddressDTO data){
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
