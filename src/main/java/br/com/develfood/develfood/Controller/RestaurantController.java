package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.RequestRestaurant;
import br.com.develfood.develfood.Repository.PlateFilterRespository;
import br.com.develfood.develfood.Repository.RestaurantRepository;

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
    private RestaurantRepository repository;
    @Autowired
    private PlateFilterRespository plateFilterRespository;

    @GetMapping
    public ResponseEntity <Page<Restaurant>>getAllRestaurant(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<Restaurant> allRestaurant = repository.findAll(pageable);
        return ResponseEntity.ok(allRestaurant);
    }
    @PostMapping
    public ResponseEntity postRestaurant(@RequestBody @Validated RequestRestaurant body){

        if(body.plateFilter() == null || body.plateFilter().getId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de filtro");
        }
        Long idPlateFilter = body.plateFilter().getId();
        Optional<PlateFilter>plateFilterOptional = plateFilterRespository.findById(idPlateFilter);
        if(plateFilterOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comida não encontrada");
        }
        PlateFilter plateFilter = plateFilterOptional.get();
        Restaurant newRestaurant = new Restaurant(body);
        repository.save(newRestaurant);
        return ResponseEntity.ok().build();

    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity updateRestaurant(@PathVariable Long id, @RequestBody @Validated RequestRestaurant data) {
        if (id != null) {
            Optional<Restaurant> optionalRestaurant = repository.findById(id);
            if (optionalRestaurant.isPresent()) {
                Restaurant restaurant = optionalRestaurant.get();
                restaurant.setNome(data.nome());
                restaurant.setCpf(data.cpf());
                restaurant.setTelefone(data.telefone());
                restaurant.setFoto(data.foto());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable String id){
        repository.deleteById(Long.valueOf(id));
        return   ResponseEntity.noContent().build();
    }

    }

