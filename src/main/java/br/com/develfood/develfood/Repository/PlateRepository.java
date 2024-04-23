package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Plates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlateRepository extends JpaRepository<Plates, String> {

        Page<Plates> findByCategoriaOrderByRestauranteNome(String categoria, Pageable pageable);
    List<Plates> findByRestauranteId(Long restauranteId);


}
    

