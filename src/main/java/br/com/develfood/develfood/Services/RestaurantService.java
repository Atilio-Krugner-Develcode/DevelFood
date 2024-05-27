package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Avaliacao;
import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.RequestRestaurant;
import br.com.develfood.develfood.Repository.AvaliacaoRepository;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PlateFilterRespository plateFilterRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;





    public ResponseEntity<Page<RequestRestaurant>> getAllRestaurants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Restaurant> allRestaurants = restaurantRepository.findAll(pageable);

        Page<RequestRestaurant> restaurantDTOs = allRestaurants.map(restaurant -> {
            List<Avaliacao> avaliacoes = avaliacaoRepository.findByRestauranteId(restaurant.getId());
            BigDecimal mediaDasNotas = calcularMediaDasNotas(avaliacoes);
            return convertToDTO(restaurant, mediaDasNotas);
        });

        return ResponseEntity.ok(restaurantDTOs);
    }

    public RequestRestaurant getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Restaurante não encontrado com o ID: " + restaurantId));

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByRestauranteId(restaurantId);
        BigDecimal mediaDasNotas = calcularMediaDasNotas(avaliacoes);

        return convertToDTO(restaurant, mediaDasNotas);
    }

    private BigDecimal calcularMediaDasNotas(List<Avaliacao> avaliacoes) {
        if (avaliacoes == null || avaliacoes.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal somaDasNotas = BigDecimal.ZERO;
        for (Avaliacao avaliacao : avaliacoes) {
            somaDasNotas = somaDasNotas.add(avaliacao.getNota());
        }

        BigDecimal quantidadeDeAvaliacoes = BigDecimal.valueOf(avaliacoes.size());
        return somaDasNotas.divide(quantidadeDeAvaliacoes, 2, RoundingMode.HALF_UP);
    }

    private RequestRestaurant convertToDTO(Restaurant restaurant, BigDecimal mediaDasNotas) {
        return new RequestRestaurant(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCnpj(),
                restaurant.getPhone(),
                restaurant.getImage(),
                restaurant.getPlateFilter(),
                mediaDasNotas
        );
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
                restaurant.setName(data.nome());
                restaurant.setCnpj(data.cnpj());
                restaurant.setPhone(String.valueOf(data.telefone()));
                restaurant.setImage(data.foto());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public boolean deletRestuarant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
        return false;
    }

    public Page<Restaurant> findRestaurantsByName(String nomeRestaurante, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return restaurantRepository.findByNameContainingIgnoreCase(nomeRestaurante, pageable);
    }
}


