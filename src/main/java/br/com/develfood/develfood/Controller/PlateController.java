package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Repository.PlateRepository;
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
@RequestMapping("/plate")
public class PlateController {

    @Autowired
    private PlateRepository plateRepository;


    @GetMapping
    public ResponseEntity<Page<Plates>> getAllPlate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Plates> allPlate = plateRepository.findAll(pageable);
        return ResponseEntity.ok(allPlate);
    }

    @PostMapping("/create")
    public ResponseEntity postPlate(@RequestBody @Validated PlateDTO body){
        Plates newPlate = new Plates(body);
        plateRepository.save(newPlate);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity updatePlate(@PathVariable Long id, @RequestBody @Validated PlateDTO data) {
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
    public ResponseEntity deletePlate(@PathVariable String id){
        plateRepository.deleteById(id);
        return   ResponseEntity.noContent().build();
    }

}
