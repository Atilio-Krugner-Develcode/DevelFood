package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Record.PlateFilterDTO;
import br.com.develfood.develfood.Repository.PlateFilterRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/plate")
public class PlateController {

    @Autowired
    private PlateFilterRespository plateFilterRespository;

    @GetMapping
    public ResponseEntity getAllPlateFilter(){
        var allPlateFilter = plateFilterRespository.findAll();
        return ResponseEntity.ok(allPlateFilter);
    }

    @PostMapping("/filter")
    public ResponseEntity postPlateFilter(@RequestBody @Validated PlateFilterDTO body){
        PlateFilter newPlateFilter = new PlateFilter(body);
        plateFilterRespository.save(newPlateFilter);
        return ResponseEntity.ok().build();
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity updatePlateFilter(@PathVariable Long id, @RequestBody @Validated PlateFilterDTO data) {
        if (id != null) {
            Optional<PlateFilter> optionalPlateFilter = plateFilterRespository.findById(String.valueOf(id));
            if (optionalPlateFilter.isPresent()) {
                PlateFilter plateFilter = optionalPlateFilter.get();
                plateFilter.setNome(data.nome());

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePlateFilter(@PathVariable String id){
        plateFilterRespository.deleteById(id);
        return   ResponseEntity.noContent().build();
    }

}
