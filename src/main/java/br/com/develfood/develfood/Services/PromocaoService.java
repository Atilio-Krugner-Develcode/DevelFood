package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Class.RestaurantePromocao;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import br.com.develfood.develfood.Repository.RestaurantePromocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PromocaoService {
    private RestaurantRepository restaurantRepository;
    private final RestaurantePromocaoRepository restaurantePromocaoRepository;

    public PromocaoService(RestaurantePromocaoRepository restaurantePromocaoRepository) {
        this.restaurantePromocaoRepository = restaurantePromocaoRepository;
    }

    @Autowired
    public void RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    public RestaurantePromocao createPromotion(Long restauranteId, String fotoUrl) {
        Restaurant restaurante = restaurantRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado com ID: " + restauranteId));

        RestaurantePromocao novaPromocao = new RestaurantePromocao();
        novaPromocao.setFoto(fotoUrl);

        novaPromocao.setDataFinal(LocalDate.from(LocalDateTime.now().plusHours(8)));
        novaPromocao.setAtiva(true);


        novaPromocao.setRestaurante(restaurante);

        if (novaPromocao.getFoto() == null || novaPromocao.getDataFinal() == null) {
            throw new IllegalArgumentException("A foto e a data final da promoção são obrigatórias.");
        }

        return restaurantePromocaoRepository.save(novaPromocao);
    }
}


