package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.ClienteNotFoundException;
import br.com.develfood.develfood.Class.PratosFavoritos;
import br.com.develfood.develfood.Repository.PratosFavoritosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PratoFavoritoService {

    private final PratosFavoritosRepository pratoFavoritoRepository;

    @Autowired
    public PratoFavoritoService(PratosFavoritosRepository pratoFavoritoRepository) {
        this.pratoFavoritoRepository = pratoFavoritoRepository;
    }

    public void favoritarPrato(PratosFavoritos pratoFavorito) {
        pratoFavoritoRepository.save(pratoFavorito);
    }

    public List<PratosFavoritos> getPratosFavoritosByClienteId(int clienteId) {
        return pratoFavoritoRepository.findByClienteId(clienteId);
    }

    public void excluirPratoFavorito(int pratoFavoritoId) {
        pratoFavoritoRepository.deleteById((long) pratoFavoritoId);
    }
}
