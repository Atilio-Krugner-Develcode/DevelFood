package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Record.PlatesDTO;
import br.com.develfood.develfood.Record.RestauranteComPratosDTO;
import br.com.develfood.develfood.Repository.PlateRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import br.com.develfood.develfood.Services.PlateService;
import br.com.develfood.develfood.Services.RestaurantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant/plate")
public class PlateController {

    @Autowired
    private PlateService plateService;

    @Autowired
    private RestaurantService restaurantService;



    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestauranteComPratosDTO> getRestauranteComPratosById(@PathVariable Long restaurantId) {
        try {
            RestauranteComPratosDTO restauranteComPratosDTO = plateService.getRestauranteComPratosById(restaurantId);
            return new ResponseEntity<>(restauranteComPratosDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/create")
    public ResponseEntity<?> postPlate(@RequestBody @Validated PlateDTO body, @RequestParam Long restaurantId) {
        return plateService.createPlate(body, restaurantId);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePlate(@PathVariable Long id, @RequestBody @Validated PlateDTO data) {
        return plateService.updatePlate(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlate(@PathVariable Long id) {
        plateService.deletePlate(id);
        return ResponseEntity.noContent().build();
    }
}

