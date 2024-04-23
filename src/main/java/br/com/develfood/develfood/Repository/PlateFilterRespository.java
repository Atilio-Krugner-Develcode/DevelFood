package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.PlateFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlateFilterRespository  extends JpaRepository<PlateFilter, Long> {
    @Override
    Optional<PlateFilter> findById(Long aLong);
}
