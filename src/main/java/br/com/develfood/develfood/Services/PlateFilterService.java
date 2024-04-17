package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Record.PlateFilterDTO;
import br.com.develfood.develfood.Repository.PlateFilterRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlateFilterService {

    @Autowired
    private PlateFilterRespository plateFilterRepository;

    public Page<PlateFilter> getAllPlateFilter(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return plateFilterRepository.findAll(pageable);
    }

    public ResponseEntity postPlateFilter(PlateFilterDTO body) {
        PlateFilter newPlateFilter = new PlateFilter(body);
        plateFilterRepository.save(newPlateFilter);
        return ResponseEntity.ok().build();
    }


    public ResponseEntity updatePlateFilter(Long id, PlateFilterDTO data) {
        if (id != null) {
            Optional<PlateFilter> optionalPlateFilter = plateFilterRepository.findById(id);
            if (optionalPlateFilter.isPresent()) {
                PlateFilter plateFilter = optionalPlateFilter.get();
                plateFilter.setNome(data.nome());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity deletePlateFilter(Long id) {
        plateFilterRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


