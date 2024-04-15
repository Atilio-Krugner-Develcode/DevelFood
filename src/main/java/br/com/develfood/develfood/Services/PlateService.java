package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.PlateFilter;
import br.com.develfood.develfood.Record.PlateFilterDTO;
import br.com.develfood.develfood.Repository.PlateFilterRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class PlateService {

    @Autowired
    private PlateFilterRespository plateFilterRespository;

    @Transactional(readOnly = true)
    public List<PlateFilter> getAllPlateFilters() {
        return plateFilterRespository.findAll();
    }

    @Transactional
    public void savePlateFilter(PlateFilterDTO body) {
        PlateFilter newPlateFilter = new PlateFilter(body);
        plateFilterRespository.save(newPlateFilter);
    }

    @Transactional
    public ResponseEntity updatePlateFilter(Long id, PlateFilterDTO data) {
        Optional<PlateFilter> optionalPlateFilter = plateFilterRespository.findById(String.valueOf(id));
        if (optionalPlateFilter.isPresent()) {
            PlateFilter plateFilter = optionalPlateFilter.get();
            plateFilter.setNome(data.nome());
            plateFilterRespository.save(plateFilter);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public ResponseEntity deletePlateFilter(String id) {
        plateFilterRespository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
