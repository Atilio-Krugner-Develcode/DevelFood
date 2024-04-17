package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Record.PlatesDTO;
import br.com.develfood.develfood.Record.RestauranteComPratosDTO;
import br.com.develfood.develfood.Services.PlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant/plate")
public class PlateController {

    @Autowired
    private PlateService plateService;

    @GetMapping("/list")
    public ResponseEntity<Page<RestauranteComPratosDTO>> listarTodosPratos(@PageableDefault(size = 10) Pageable pageable) {
        Page<RestauranteComPratosDTO> result = plateService.listarTodosPratos(pageable);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Page<PlatesDTO>> getAllPlate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String categoria
    ) {
        Page<PlatesDTO> result = plateService.getAllPlate(page, size, categoria);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public ResponseEntity<?> postPlate(@RequestBody @Validated PlateDTO body, @RequestParam Long restaurantId) {
        return plateService.postPlate(body, restaurantId);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePlate (@PathVariable Long id, @RequestBody @Validated PlateDTO data){
        return plateService.updatePlate(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePlate (@PathVariable Long id){
        return plateService.deletePlate(id);
    }
}

