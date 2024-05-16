package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.PratosFavoritos;

import br.com.develfood.develfood.Record.PratoPromocaoDTO;
import br.com.develfood.develfood.Repository.PlateRepository;
import br.com.develfood.develfood.Services.PratoFavoritoService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favoritos")
public class PratoFavoritoController {

    private final PratoFavoritoService pratoFavoritoService;
    private final PlateRepository plateRepository;

    @Autowired
    public PratoFavoritoController(PratoFavoritoService pratoFavoritoService,
                                   PlateRepository plateRepository) {
        this.pratoFavoritoService = pratoFavoritoService;
        this.plateRepository = plateRepository;
    }


    @PostMapping("/prato")
    public ResponseEntity<String> adicionarPratoFavorito(@RequestBody PratoPromocaoDTO dadosPromocao, HttpServletRequest request) {
        try {
            pratoFavoritoService.adicionarPratoFavortito(dadosPromocao, request);
            return ResponseEntity.ok("Prato favoritado com sucesso!");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }


    @DeleteMapping("/{plateId}")
    public ResponseEntity<String> removerPratoFavorito(@PathVariable Long plateId, HttpServletRequest request) {
        try {
            pratoFavoritoService.removerPratoFavorito(plateId, request);
            return ResponseEntity.ok("Prato removido dos favoritos com sucesso!");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Plates>> obterPratosFavoritos(HttpServletRequest request) {
        try {
            List<Plates> pratosFavoritos = pratoFavoritoService.obterPratosFavoritos(request);
            return ResponseEntity.ok(pratosFavoritos);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    private String extrairToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new JWTVerificationException("Token inválido");
        }
        return authorization.substring(7);
    }

    private Long validarToken(String token) {
        Long userId = pratoFavoritoService.validarToken(token);
        if (userId == null) {
            throw new JWTVerificationException("Token inválido");
        }
        return userId;
    }



    private Long obterIdClienteDoToken(HttpServletRequest request) {
        String token = extrairToken(request);
        Long userId = validarToken(token);
        return userId;
    }


}

