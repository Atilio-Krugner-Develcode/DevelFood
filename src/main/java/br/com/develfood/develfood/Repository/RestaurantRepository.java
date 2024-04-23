package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
