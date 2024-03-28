package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.RequestRestaurant;
import br.com.develfood.develfood.Repository.RestaurantRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantRepository repository;

    @GetMapping
    public ResponseEntity getAllRestaurant(){
        var allRestaurant = repository.findAll();
        return ResponseEntity.ok(allRestaurant);
    }
    @PostMapping
    public ResponseEntity postRestaurant(@RequestBody @Validated RequestRestaurant body){
        Restaurant newRestaurant = new Restaurant(body);

        repository.save(newRestaurant);
        return ResponseEntity.ok().build();

    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity updateRestaurant(@PathVariable Long id, @RequestBody @Validated RequestRestaurant data) {
        if (id != null) {
            Optional<Restaurant> optionalRestaurant = repository.findById(id);
            if (optionalRestaurant.isPresent()) {
                Restaurant restaurant = optionalRestaurant.get();
                restaurant.setNome(data.nome());
                restaurant.setCpf(data.cpf());
                restaurant.setTelefone(data.telefone());
                restaurant.setFoto(data.foto());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable String id){
        repository.deleteById(Long.valueOf(id));
        return   ResponseEntity.noContent().build();
    }

    }
