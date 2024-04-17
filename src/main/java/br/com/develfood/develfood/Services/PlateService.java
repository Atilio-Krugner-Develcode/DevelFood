package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Record.PlatesDTO;
import br.com.develfood.develfood.Record.RestauranteComPratosDTO;
import br.com.develfood.develfood.Repository.PlateRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlateService {
    @Autowired
    private PlateRepository plateRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Page<RestauranteComPratosDTO> listarTodosPratos(Pageable pageable) {
        Page<Restaurant> restaurantesPage = restaurantRepository.findAll(pageable);

        return restaurantesPage.map(restaurante -> {
            List<Plates> pratos = restaurante.getPratos();
            List<PlateDTO> pratosDTO = pratos.stream()
                    .map(PlateDTO::new)
                    .collect(Collectors.toList());
            return new RestauranteComPratosDTO(restaurante.getId(), restaurante.getNome(), restaurante.getCpf(), restaurante.getTelefone(), restaurante.getFoto(),
                    restaurante.getPlateFilter(), pratosDTO);
        });
    }

    public Page<PlatesDTO> getAllPlate(int page, int size, String categoria) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Plates> allPlate;

        if (categoria != null && !categoria.isEmpty()) {
            allPlate = plateRepository.findByCategoriaOrderByRestauranteNome(categoria, pageable);
        } else {
            allPlate = plateRepository.findAll(pageable);
        }

        return allPlate.map(plate -> new PlatesDTO(plate.getId(), plate.getNome(), plate.getDescricao(), plate.getFoto(),
                plate.getPreco(), plate.getCategoria()));
    }

    public ResponseEntity<?> postPlate(PlateDTO body, Long restaurantId) {
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

    public ResponseEntity updatePlate(Long id, PlateDTO data) {
        if (id != null) {
            Optional<Plates> optionalPlates = plateRepository.findById(String.valueOf(id));
            if (optionalPlates.isPresent()) {
                Plates plates = optionalPlates.get();
                plates.setNome(data.nome());
                plates.setDescricao(data.descricao());
                plates.setFoto(data.foto());
                plates.setPreco(data.preco());
                plates.setCategoria(data.categoria());

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity deletePlate(Long id) {
        plateRepository.deleteById(String.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}

