package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.ClienteNotFoundException;
import br.com.develfood.develfood.Class.PratosFavoritos;

import br.com.develfood.develfood.Services.PratoFavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class PratoFavoritoController {

    private final PratoFavoritoService pratoFavoritoService;

    @Autowired
    public PratoFavoritoController(PratoFavoritoService pratoFavoritoService) {
        this.pratoFavoritoService = pratoFavoritoService;
    }

    @PostMapping("/pratos")
    public ResponseEntity<String> favoritarPrato(@RequestBody PratosFavoritos pratoFavorito) {
        try {
            pratoFavoritoService.favoritarPrato(pratoFavorito);
            return new ResponseEntity<>("Prato favoritado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao favoritar prato: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{clienteId}/list")
    public ResponseEntity<List<PratosFavoritos>> getPratosFavoritosByClienteId(@PathVariable("clienteId") int clienteId) {
        List<PratosFavoritos> pratosFavoritos = pratoFavoritoService.getPratosFavoritosByClienteId(clienteId);
        if (pratosFavoritos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pratosFavoritos, HttpStatus.OK);
    }

    @DeleteMapping("/{clienteId}/pratos/{pratoFavoritoId}")
    public ResponseEntity<String> excluirPratoFavorito(
            @PathVariable("clienteId") int clienteId,
            @PathVariable("pratoFavoritoId") int pratoFavoritoId) {

        try {

            pratoFavoritoService.excluirPratoFavorito(pratoFavoritoId);
            return new ResponseEntity<>("Prato favorito excluído com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao excluir prato favorito: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}




