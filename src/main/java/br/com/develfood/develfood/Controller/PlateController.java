package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Record.PlateFilterDTO;
import br.com.develfood.develfood.Services.PlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/plate")
public class PlateController {


    @Autowired
    private PlateService plateService;

    @GetMapping
    public ResponseEntity getAllPlateFilter() {
        List<PlateFilter> allPlateFilter = plateService.getAllPlateFilters();
        return ResponseEntity.ok(allPlateFilter);
    }

    @PostMapping("/filter")
    public ResponseEntity postPlateFilter(@RequestBody @Validated PlateFilterDTO body) {
        plateService.savePlateFilter(body);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePlateFilter(@PathVariable Long id, @RequestBody @Validated PlateFilterDTO data) {
        return plateService.updatePlateFilter(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePlateFilter(@PathVariable String id) {
        return plateService.deletePlateFilter(id);
    }

}
