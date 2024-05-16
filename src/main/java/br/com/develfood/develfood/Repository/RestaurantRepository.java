package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {



    Page<Restaurant> findByNameContainingIgnoreCase(String nomeRestaurante, Pageable pageable);
}
