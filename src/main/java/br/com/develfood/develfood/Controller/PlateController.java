package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Record.PlatesDTO;
import br.com.develfood.develfood.Record.RestauranteComPratosDTO;
import br.com.develfood.develfood.Repository.PlateRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import br.com.develfood.develfood.Services.PlateService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant/plate")
public class PlateController {

    @Autowired
    private PlateService plateService;

    @GetMapping("/list")
    public ResponseEntity<Page<RestauranteComPratosDTO>> listarTodosPratos(@PageableDefault(size = 10) Pageable pageable) {
        return plateService.listarTodosPratos(pageable);
    }

    @GetMapping
    public ResponseEntity<Page<PlatesDTO>> getAllPlates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String categoria
    ) {
        return plateService.getAllPlates(page, size, categoria);
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

