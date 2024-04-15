package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.RequestRestaurant;
import br.com.develfood.develfood.Repository.RestaurantRepository;

import br.com.develfood.develfood.Services.RestaurantService;
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
    @Autowired
    private RestaurantService restaurantService;

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

    @PutMapping("/{id}")
    public ResponseEntity updateRestaurant(@PathVariable Long id, @RequestBody @Validated RequestRestaurant data) {
        if (id != null) {
            return restaurantService.updateRestaurant(id, data);
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

