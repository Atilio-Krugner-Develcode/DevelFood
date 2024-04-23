package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Endereco;
import br.com.develfood.develfood.Record.AddressDTO;
import br.com.develfood.develfood.Repository.AddressRepository;
import br.com.develfood.develfood.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;

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
    @DeleteMapping("/customer/{customerId}/address/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long customerId, @PathVariable Long addressId) {
        addressService.deleteAddress(customerId, addressId);
        Optional<Endereco> optionalAddress = addressRepository.findById(addressId);
        if (!optionalAddress.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("O endereço não pertence ao cliente especificado.");
        }
    }
}


