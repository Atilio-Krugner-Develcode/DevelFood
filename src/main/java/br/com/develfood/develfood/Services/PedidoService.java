package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.*;

import br.com.develfood.develfood.Class.Pedido.*;
import br.com.develfood.develfood.Record.AddressDTO;
import br.com.develfood.develfood.Record.ClientAndAddressDTO;
import br.com.develfood.develfood.Repository.*;
import br.com.develfood.develfood.infra.security.TokenService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {


    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PlateRepository plateRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public Long criarPedido(CriarPedidoDTO pedidoDTO, HttpServletRequest request) {
        String token = extrairToken(request);
        Long userId = validarToken(token);

        Cliente cliente = encontrarCliente(userId);

        Restaurant restaurante = restaurantRepository.findById(pedidoDTO.getIdRestaurantes())
                .orElseThrow(() -> new NoSuchElementException("Restaurante não encontrado com o ID: " + pedidoDTO.getIdRestaurantes()));

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<ItemPedido> plates = new ArrayList<>();

        Pedido pedido = new Pedido();

        for (PedidoDTO pratoDTO : pedidoDTO.getPedidos()) {
            Plates prato = plateRepository.findById(pratoDTO.getIdPrato())
                    .orElseThrow(() -> new NoSuchElementException("Prato não encontrado com o ID: " + pratoDTO.getIdPrato()));

            if (!prato.getRestaurante().getId().equals(pedidoDTO.getIdRestaurantes())) {
                throw new RuntimeException("Prato não pertence a esse restaurante");
            }

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPlates(prato);
            itemPedido.setPedido(pedido);
            itemPedido.setObservacao(pratoDTO.getObservacao());
            itemPedido.setQuantidade(pratoDTO.getQuantidade());

            plates.add(itemPedido);

            BigDecimal precoTotal = prato.getPrice().multiply(BigDecimal.valueOf(pratoDTO.getQuantidade()));
            totalPrice = totalPrice.add(precoTotal);
        }

        pedido.setCliente(cliente);
        pedido.setRestaurantes(restaurante);
        pedido.setItemPedidos(plates);
        pedido.setStatus("PEDIDO_REALIZADO");
        pedido.setDate(LocalDate.now());
        pedido.setPaymentType("DINHEIRO");
        pedido.setFullPrice(totalPrice);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return pedidoSalvo.getId();
    }


    public PedidoResponseDTO buscarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado com o ID: " + pedidoId));

        PedidoResponseDTO responseDTO = new PedidoResponseDTO();
        responseDTO.setRestauranteId(pedido.getRestaurantes().getId());
        responseDTO.setTotalPreco(pedido.getFullPrice());
        responseDTO.setFormaPagamento(pedido.getPaymentType());
        responseDTO.setPedidoId(pedido.getId());
        responseDTO.setStatus(pedido.getStatus());
        responseDTO.setDataCriacao(pedido.getDate());

        Cliente cliente = pedido.getCliente();
        List<AddressDTO> enderecosDTO = cliente.getEndereco().stream()
                .map(endereco -> new AddressDTO(
                        endereco.getStreet(),
                        endereco.getNumber(),
                        endereco.getCity(),
                        endereco.getNeigbourhood(),
                        endereco.getCep(),
                        endereco.getState()
                ))
                .collect(Collectors.toList());

        ClientAndAddressDTO clientAndAddressDTO = new ClientAndAddressDTO(
                cliente.getId(),
                cliente.getFirstName(),
                cliente.getLastName(),
                cliente.getCpf(),
                cliente.getPhone(),
                cliente.getImage(),
                enderecosDTO
        );
        responseDTO.setCliente(clientAndAddressDTO);

        List<PratoResponseDTO> pratos = pedido.getItemPedidos().stream().map(item -> {
            PratoResponseDTO pratoDTO = new PratoResponseDTO();
            pratoDTO.setId(item.getPlates().getId());
            pratoDTO.setNome(item.getPlates().getName());
            pratoDTO.setPreco(item.getPlates().getPrice());
            pratoDTO.setQuantidade(item.getQuantidade());
            pratoDTO.setObservacao(item.getObservacao());
            return pratoDTO;
        }).collect(Collectors.toList());

        responseDTO.setPratos(pratos);

        System.out.println("Pedido encontrado: " + responseDTO);

        return responseDTO;
    }

    public List<PedidoListDTO> listarPratosDoCliente(Cliente cliente) {

        var pedidos = cliente.getPedido();
        var listaItemsDTO = pedidos.stream().map(pedido -> {
            var itensPedido = pedido.getItemPedidos().stream().map(itemPedido -> {
                Plates prato = itemPedido.getPlates();
                BigDecimal price = (prato != null && prato.getPrice() != null) ? prato.getPrice() : BigDecimal.ZERO;

                var itemPedidoDTO = ItemPedidoDto.builder()
                        .id(itemPedido.getId())
                        .observacao(itemPedido.getObservacao())
                        .price(price)
                        .quantidade(itemPedido.getQuantidade())
                        .plates(PlatesDto.builder()
                                .name(prato.getName())
                                .id(prato.getId())
                                .build())
                        .build();

                return itemPedidoDTO;
            }).toList();
            return PedidoListDTO.builder()
                    .itemPedidos(itensPedido)
                    .date(LocalDate.now())
                    .fullPrice(pedido.getFullPrice())
                    .id(pedido.getId())
                    .paymentType(pedido.getPaymentType())
                    .restaurantes(RestaurantDto.builder()
                            .id(pedido.getRestaurantes().getId())
                            .name(pedido.getRestaurantes().getName())
                            .build())
                    .status(pedido.getStatus())
                    .build();
        }).toList();

        return listaItemsDTO;
    }

    public List<PedidoListDTO> pedidoResponseDTO(HttpServletRequest request) {
        String token = extrairToken(request);
        Long userId = validarToken(token);
        User usuario = encontrarUser(userId);
        Cliente cliente = encontrarCliente(usuario);
        return listarPratosDoCliente(cliente);
    }


    public void atualizarStatusPedido(Long pedidoId, Estatus novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatus(String.valueOf(novoStatus));
        pedidoRepository.save(pedido);

        enviarEmailClientePedidoAtualizado(pedido);
    }

    private void enviarEmailClientePedidoAtualizado(Pedido pedido) {
        String clienteEmail = pedido.getCliente().getEmail();

        if (clienteEmail == null || clienteEmail.isEmpty()) {
            throw new IllegalArgumentException("O e-mail do cliente não está disponível.");
        }

        Email email = new Email();
        email.setEmailTo(clienteEmail);
        email.setTitle("Atualização do Status do Pedido");
        email.setTexto("Olá,\n\nO status do seu pedido foi atualizado para: " + pedido.getStatus() + "\n\nAtenciosamente,\nDevelFood");

        try {
            emailService.enviarEmail(email);
            System.out.println("E-mail enviado com sucesso para: " + clienteEmail);
        } catch (MailException e) {
            System.err.println("Erro ao enviar e-mail para " + clienteEmail + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String extrairToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new JWTVerificationException("Token invalido");
        }
        return authorization.substring(7);
    }


    private User encontrarUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(String.valueOf(userId));
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        return userOptional.get();
    }

    private Cliente encontrarCliente(User user) {
        Optional<Cliente> clienteOptional = clientRepository.findByUser(user);
        if (clienteOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        return clienteOptional.get();
    }

    private Long encontrarClienteId(Long userId) {
        Optional<Cliente> clienteOptional = clientRepository.findByUserId(userId);
        if (clienteOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado para o ID de usuário: " + userId);
        }
        return clienteOptional.get().getId();
    }

    public Long extrairIdClienteDoToken(HttpServletRequest request) {
        String token = extrairToken(request);
        Long userId = validarToken(token);
        return encontrarClienteId(userId);
    }

    private Cliente encontrarCliente(Long userId) {
        Optional<Cliente> clienteOptional = clientRepository.findByUserId(userId);
        if (clienteOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        return clienteOptional.get();
    }

    private Long validarToken(String token) {
        Long userId = tokenService.extrairIdUser(token);
        if (userId == null) {
            throw new JWTVerificationException("Token invalido");
        }
        return userId;
    }


}





