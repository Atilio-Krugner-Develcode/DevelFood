package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.RequestRestaurant;
import br.com.develfood.develfood.Repository.PlateFilterRespository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PlateFilterRespository plateFilterRepository;

    public ResponseEntity<Page<Restaurant>> getAllRestaurants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Restaurant> allRestaurant = restaurantRepository.findAll(pageable);
        return ResponseEntity.ok(allRestaurant);
    }

    public ResponseEntity postRestaurant(RequestRestaurant body) {
        if (body.plateFilter() == null || body.plateFilter().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de filtro");
        }
        Long idPlateFilter = body.plateFilter().getId();
        Optional<PlateFilter> plateFilterOptional = plateFilterRepository.findById(idPlateFilter);
        if (plateFilterOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comida não encontrada");
        }
        PlateFilter plateFilter = plateFilterOptional.get();
        Restaurant newRestaurant = new Restaurant(body);
        restaurantRepository.save(newRestaurant);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity updateRestaurant(Long id, RequestRestaurant data) {
        if (id != null) {
            Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
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

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}
