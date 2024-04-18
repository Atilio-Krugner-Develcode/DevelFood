package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Endereco;
import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.AddressDTO;
import br.com.develfood.develfood.Record.ClientAndAddressDTO;
import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Record.RestaurantAndAddress;
import br.com.develfood.develfood.Repository.AddressRepository;
import br.com.develfood.develfood.Repository.ClientRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import br.com.develfood.develfood.Services.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    public ResponseEntity<Object> entitiesAndAddresses(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(addressService.getEntitiesAndAddresses(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<?> postAddress(@RequestBody @Validated AddressDTO body, @RequestParam(required = false) Long restaurantId, @RequestParam(required = false) Long customerId) {
        return addressService.createAddress(body, restaurantId, customerId);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAddress(@PathVariable Long id, @RequestBody @Validated AddressDTO data) {
        return addressService.updateAddress(id, data);
    }
}


