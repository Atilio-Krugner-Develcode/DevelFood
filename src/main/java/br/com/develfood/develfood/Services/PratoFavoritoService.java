package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Plates;
import br.com.develfood.develfood.Class.PratosFavoritos;
import br.com.develfood.develfood.Class.User;
import br.com.develfood.develfood.Record.PratoPromocaoDTO;
import br.com.develfood.develfood.Repository.ClientRepository;
import br.com.develfood.develfood.Repository.PlateRepository;
import br.com.develfood.develfood.Repository.PratosFavoritosRepository;
import br.com.develfood.develfood.Repository.UserRepository;
import br.com.develfood.develfood.infra.security.TokenService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PratoFavoritoService {

    private final PratosFavoritosRepository pratoFavoritoRepository;

    @Autowired
    public PratoFavoritoService(PratosFavoritosRepository pratoFavoritoRepository) {
        this.pratoFavoritoRepository = pratoFavoritoRepository;
    }


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PlateRepository plateRepository;



//    public List<PratosFavoritos> getPratosFavoritosByClienteId(Long clienteId) {
//        return pratoFavoritoRepository.findByClienteId(clienteId);
//    }




    private String extrairToken (HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            throw new JWTVerificationException("Token invalido");
        }
        return authorization.substring(7);
    }

    private User encontrarUser(Long userId){
        Optional<User>userOptional = userRepository.findById(String.valueOf(userId));
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        return userOptional.get();
    }

    private Cliente encontrarCliente(User user){
        Optional<Cliente>clienteOptional = clientRepository.findByUser(user);
        if (clienteOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        return clienteOptional.get();
    }

    public Long validarToken(String token) {
        Long userId = tokenService.extrairIdUser(token);
        if(userId == null){
            throw new JWTVerificationException("Token invalido");
        }
        return userId;
    }
    public PratosFavoritos findById(Long pratoFavoritoId) {
        return pratoFavoritoRepository.findById(pratoFavoritoId)
                .orElse(null);
    }


//    public void removerPratoFavorito(Long id) {
//
//    }

    public List<Plates> obterPratosFavoritos(HttpServletRequest request) {
        String token = extrairToken(request);
        Long userId = validarToken(token);
        User user = encontrarUser(userId);
        Cliente cliente = encontrarCliente(user);

        return cliente.getPratosFavoritos().stream()
                .map(PratosFavoritos::getPrato)
                .collect(Collectors.toList());
    }

    public void removerPratoFavorito(Long plateId, HttpServletRequest request) {
        String token = extrairToken(request);
        Long userId = validarToken(token);
        User user = encontrarUser(userId);
        Cliente cliente = encontrarCliente(user);

        Optional<Plates> platesOptional = plateRepository.findById(plateId);
        if (platesOptional.isPresent()) {
            Plates plates = platesOptional.get();
            PratosFavoritos pratosFavoritos = (PratosFavoritos) pratoFavoritoRepository.findByClienteAndPrato(cliente, plates)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prato favorito não encontrado para o cliente"));
            pratoFavoritoRepository.delete(pratosFavoritos);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prato não encontrado pelo o ID!" + plateId);
        }
    }

    public Optional<PratosFavoritos> getPratoFavorito(Long clienteId, Long pratoId) {
        return pratoFavoritoRepository.findByClienteIdAndPratoId(clienteId, pratoId);
    }

    public void adicionarPratoFavortito(PratoPromocaoDTO pratoPromocaoDTO, HttpServletRequest request){
        String token = extrairToken(request);
        Long userId = validarToken(token);
        User user = encontrarUser(userId);
        Cliente cliente = encontrarCliente(user);
        adicionarPrato( cliente, pratoPromocaoDTO );
    }

    private void adicionarPrato(Cliente cliente, PratoPromocaoDTO pratoPromocaoDTO) {
        Optional<Plates> platesOptional = plateRepository.findById(pratoPromocaoDTO.plateId());

        if (platesOptional.isPresent()) {
            Plates plates = platesOptional.get();
            boolean jaFavoritou = clientRepository.existsByPratosFavoritos_ClienteAndPratosFavoritos_Prato(cliente, plates);
            if (jaFavoritou) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este prato já foi favoritado pelo cliente");
            }
            PratosFavoritos pratosFavoritos = new PratosFavoritos();
            pratosFavoritos.setCliente(cliente);
            pratosFavoritos.setPrato(plates);
            pratoFavoritoRepository.save(pratosFavoritos);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prato não encontrado pelo o ID!" + pratoPromocaoDTO.plateId());
        }
    }
    }


