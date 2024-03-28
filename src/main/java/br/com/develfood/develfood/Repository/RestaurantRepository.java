package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
