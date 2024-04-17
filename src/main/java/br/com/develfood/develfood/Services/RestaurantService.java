package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.RequestRestaurant;
import br.com.develfood.develfood.Repository.PlateFilterRespository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PlateFilterRespository plateFilterRepository;

    public Page<Restaurant> getAllRestaurant(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    public Restaurant postRestaurant(RequestRestaurant body) {
        if (body.plateFilter() == null || body.plateFilter().getId() == null) {
            throw new IllegalArgumentException("Erro de filtro");
        }
        Long idPlateFilter = body.plateFilter().getId();
        Optional<PlateFilter> plateFilterOptional = plateFilterRepository.findById(idPlateFilter);
        PlateFilter plateFilter = plateFilterOptional.orElseThrow(() -> new IllegalArgumentException("Comida não encontrada"));

        Restaurant newRestaurant = new Restaurant(body);
        newRestaurant.setPlateFilter(plateFilter);
        return restaurantRepository.save(newRestaurant);
    }

    @Transactional
    public Restaurant updateRestaurant(Long id, RequestRestaurant data) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

        restaurant.setNome(data.nome());
        restaurant.setCpf(data.cpf());
        restaurant.setTelefone(data.telefone());
        restaurant.setFoto(data.foto());

        return restaurantRepository.save(restaurant);
    }

    public ResponseEntity deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
        return null;
    }


}
