package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.RequestRestaurant;
import br.com.develfood.develfood.Repository.PlateFilterRespository;
import br.com.develfood.develfood.Repository.RestaurantRepository;

import br.com.develfood.develfood.Services.RestaurantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<Page<Restaurant>> getAllRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return restaurantService.getAllRestaurants(page, size);
    }

    @PostMapping
    public ResponseEntity postRestaurant(@RequestBody @Validated RequestRestaurant body) {
        return restaurantService.postRestaurant(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRestaurant(@PathVariable Long id, @RequestBody @Validated RequestRestaurant data) {
        return restaurantService.updateRestaurant(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

}

