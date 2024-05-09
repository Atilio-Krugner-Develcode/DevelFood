package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.RestaurantePromocao;
import br.com.develfood.develfood.Record.CreatePromotionRequest;
import br.com.develfood.develfood.Services.PromocaoService;
import br.com.develfood.develfood.Services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotions")
public class PromocaoController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private PromocaoService promocaoService;



    @Autowired
    public void RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @PostMapping("/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantePromocao> createPromotionForRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody CreatePromotionRequest request) {
        try {
            RestaurantePromocao createdPromotion = promocaoService.createPromotion(restaurantId, request.getFotoUrl());
            return new ResponseEntity<>(createdPromotion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

