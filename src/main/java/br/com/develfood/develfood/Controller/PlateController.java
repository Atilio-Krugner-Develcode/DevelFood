package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Repository.PlateRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/restaurant/plate")
public class PlateController {

    @Autowired
    private PlateRepository plateRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;


    @GetMapping
    public ResponseEntity<Page<Plates>> getAllPlate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String categoria
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Plates> allPlate;

        if (categoria != null && !categoria.isEmpty()) {

            allPlate = plateRepository.findByCategoria(categoria, pageable);
        } else {

            allPlate = plateRepository.findAll(pageable);
        }

        return ResponseEntity.ok(allPlate);
    }

    @PostMapping("/create")
    public ResponseEntity<?> postPlate(@RequestBody @Validated PlateDTO body, @RequestParam Long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();

            Plates newPlate = new Plates(body);
            newPlate.setRestaurante(restaurant);
            plateRepository.save(newPlate);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        @PutMapping("/{id}")
        @Transactional
        public ResponseEntity updatePlate (@PathVariable Long id, @RequestBody @Validated PlateDTO data){
            if (id != null) {
                Optional<Plates> optionalPlates = plateRepository.findById(String.valueOf(id));
                if (optionalPlates.isPresent()) {
                    Plates plates = optionalPlates.get();
                    plates.setNome(data.nome());

                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity deletePlate (@PathVariable String id){
            plateRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

    }

