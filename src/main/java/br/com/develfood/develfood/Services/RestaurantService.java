package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.RequestRestaurant;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository repository;

    public ResponseEntity updateRestaurant(Long id, RequestRestaurant data) {
        Optional<Restaurant> optionalRestaurant = repository.findById(id);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.setNome(data.nome());
            restaurant.setCpf(data.cpf());
            restaurant.setTelefone(data.telefone());
            restaurant.setFoto(data.foto());
            repository.save(restaurant);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
