package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Avaliacao;
import br.com.develfood.develfood.Class.Restaurant;
import br.com.develfood.develfood.Record.AvaliacaoDTO;
import br.com.develfood.develfood.Record.RestauranteComAvaliacoes;
import br.com.develfood.develfood.Services.AvaliaçaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliaçaoService avaliacaoService;

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> getRestauranteComAvaliacoes(@PathVariable Long id) {
        try {
            RestauranteComAvaliacoes restauranteComAvaliacoesDTO = avaliacaoService.getRestauranteComAvaliacoes(id);
            return ResponseEntity.ok(restauranteComAvaliacoesDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/avaliar")
    public ResponseEntity<?> avaliarRestaurante(
            @PathVariable Long id,
            @RequestBody AvaliacaoDTO avaliacaoDTO) {
        try {
            Avaliacao avaliacao = avaliacaoService.avaliarRestaurante(id, avaliacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(avaliacao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurante não encontrado com o ID: " + id);
        }
    }

    @PutMapping("/{avaliacaoId}")
    public ResponseEntity<?> atualizarAvaliacao(
            @PathVariable Long avaliacaoId,
            @RequestBody AvaliacaoDTO avaliacaoDTO) {
        try {
            Avaliacao avaliacao = avaliacaoService.atualizarAvaliacao(avaliacaoId, avaliacaoDTO);
            return ResponseEntity.ok(avaliacao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação não encontrada com o ID: " + avaliacaoId);
        }
    }

    @DeleteMapping("/{avaliacaoId}")
    public ResponseEntity<?> excluirAvaliacao(@PathVariable Long avaliacaoId) {
        try {
            avaliacaoService.excluirAvaliacao(avaliacaoId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
