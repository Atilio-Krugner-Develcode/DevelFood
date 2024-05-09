package br.com.develfood.develfood.Job;

import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Class.RestaurantePromocao;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
public class PromoCleanerJob {

    private static final long PERIOD_HOURS = 8;

    private final RestaurantRepository restaurantRepository;

    public PromoCleanerJob(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Scheduled(fixedRate = PERIOD_HOURS * 60 * 60 * 1000)
    public void cleanExpiredPromotions() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        for (Restaurant restaurant : restaurants) {
            List<RestaurantePromocao> promocoes = restaurant.getPromocoes();

            promocoes.removeIf(promo -> promo.getDataFinal().isBefore(LocalDate.now()));

            if (!promocoes.isEmpty()) {
                restaurantRepository.save(restaurant);
            }
        }

    }
}