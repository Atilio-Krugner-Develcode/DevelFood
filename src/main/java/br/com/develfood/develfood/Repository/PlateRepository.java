package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Plates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlateRepository extends JpaRepository<Plates, String> {

        Page<Plates> findByCategoriaOrderByRestauranteName(String categoria, Pageable pageable);



}
    

