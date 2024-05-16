package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Record.RestauranteComPratosDTO;
import br.com.develfood.develfood.Repository.PlateRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlateService {
    @Autowired
    private PlateRepository plateRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestauranteComPratosDTO getRestauranteComPratosById(Long restauranteId) {
        Restaurant restaurante = restaurantRepository.findById(restauranteId)
                .orElseThrow(() -> new NoSuchElementException("Restaurante não encontrado com o ID: " + restauranteId));

        List<PlateDTO> pratosDTO = restaurante.getPratos().stream()
                .map(PlateDTO::new)
                .collect(Collectors.toList());

        return new RestauranteComPratosDTO(restaurante.getId(), restaurante.getName(), restaurante.getCnpj(),
                restaurante.getPhone(), restaurante.getImage(), restaurante.getPlateFilter(), pratosDTO);
    }



    public ResponseEntity<?> createPlate(PlateDTO body, Long restaurantId) {
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

    @Transactional
    public ResponseEntity updatePlate(Long id, PlateDTO data) {
        if (id != null) {
            Optional<Plates> optionalPlates = plateRepository.findById(Long.valueOf(String.valueOf(id)));
            if (optionalPlates.isPresent()) {
                Plates plates = optionalPlates.get();
                plates.setName(data.name());
                plates.setDescription(data.description());
                plates.setImage(data.image());
                plates.setPrice(data.price());
                plates.setCategory(data.category());

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public void deletePlate(Long id) {
        plateRepository.deleteById(Long.valueOf(String.valueOf(id)));
    }

}

