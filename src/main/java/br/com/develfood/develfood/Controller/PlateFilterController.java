package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Record.PlateFilterDTO;
import br.com.develfood.develfood.Repository.PlateFilterRespository;
import br.com.develfood.develfood.Services.PlateFilterService;
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
@RequestMapping("/type")
public class PlateFilterController {

    @Autowired
    private PlateFilterService plateFilterService;

    @GetMapping
    public ResponseEntity<Page<PlateFilter>> getAllPlateFilter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PlateFilter> result = plateFilterService.getAllPlateFilter(page, size);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/filter")
    public ResponseEntity postPlateFilter(@RequestBody @Validated PlateFilterDTO body) {
        return plateFilterService.postPlateFilter(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePlateFilter(@PathVariable Long id, @RequestBody @Validated PlateFilterDTO data) {
        return plateFilterService.updatePlateFilter(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePlateFilter(@PathVariable Long id) {
        return plateFilterService.deletePlateFilter(id);
    }
}
