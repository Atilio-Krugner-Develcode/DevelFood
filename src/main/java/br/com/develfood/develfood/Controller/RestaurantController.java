package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.RequestRestaurant;

import br.com.develfood.develfood.Services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<Page<Restaurant>> getAllRestaurant(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Restaurant> result = restaurantService.getAllRestaurant(pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public Restaurant postRestaurant(@RequestBody @Validated RequestRestaurant body) {
        return restaurantService.postRestaurant(body);
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody @Validated RequestRestaurant data) {
        return restaurantService.updateRestaurant(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable Long id) {
        return restaurantService.deleteRestaurant(id);
    }
}

