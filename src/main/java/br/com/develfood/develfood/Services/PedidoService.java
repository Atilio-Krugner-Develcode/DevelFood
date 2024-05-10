package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.*;
import br.com.develfood.develfood.Class.Pedido.CriarPedidoDTO;
import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Class.Pedido.PedidoDetalhado;
import br.com.develfood.develfood.Record.PedidoDTO;
import br.com.develfood.develfood.Repository.ClientRepository;
import br.com.develfood.develfood.Repository.PedidoRepository;

import br.com.develfood.develfood.Repository.PlateRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public void criarPedido(CriarPedidoDTO pedidoDTO) {
        Cliente cliente = clientRepository.findById(pedidoDTO.getIdCliente())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o ID: " + pedidoDTO.getIdCliente()));

        Restaurant restaurante = restaurantRepository.findById(pedidoDTO.getIdRestaurantes())
                .orElseThrow(() -> new NoSuchElementException("Restaurante não encontrado com o ID: " + pedidoDTO.getIdRestaurantes()));

        Plates prato = plateRepository.findById(String.valueOf(pedidoDTO.getIdPrato()))
                .orElseThrow(() -> new NoSuchElementException("Prato não encontrado com o ID: " + pedidoDTO.getIdPrato()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurantes(restaurante);
        pedido.setPlates(prato);
        pedido.setQuantity(pedidoDTO.getQuantity());
        pedido.setStatus("PEDIDO_REALIZADO");
        pedido.setDate(LocalDate.now());
        pedido.setPaymentType(pedidoDTO.getPaymentType());

        BigDecimal precoUnitario = pedido.getPlates().getPrice();
        int quantidade = pedido.getQuantity();
        BigDecimal total = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        pedido.setFullPrice(total);

        pedidoRepository.save(pedido);


    }

    public List<PedidoDetalhado> obterTodosPedidosDetalhados() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoDetalhado> pedidosDetalhados = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            PedidoDetalhado pedidoDetalhado = new PedidoDetalhado();
            pedidoDetalhado.setId(pedido.getId());
            pedidoDetalhado.setStatus("PEDIDO_REALIZADO");
            pedidoDetalhado.setFullPrice(pedido.getFullPrice());
            pedidoDetalhado.setQuantity(pedido.getQuantity());
            pedidoDetalhado.setStatus(pedido.getStatus());
            pedidoDetalhado.setDate(pedido.getDate());
            pedidoDetalhado.setPaymentType(pedido.getPaymentType());

            Plates pratoDTO = new Plates();
            pratoDTO.setId(pedido.getPlates().getId());
            pratoDTO.setName(pedido.getPlates().getName());
            pratoDTO.setDescription(pedido.getPlates().getDescription());
            pratoDTO.setImage(pedido.getPlates().getImage());
            pratoDTO.setPrice(pedido.getPlates().getPrice());
            pratoDTO.setCategory(pedido.getPlates().getCategory());
            pratoDTO.setPlateFilter(pedido.getRestaurantes().getPlateFilter());
            pedidoDetalhado.setPrato(pratoDTO);

            Restaurant restauranteDTO = new Restaurant();
            restauranteDTO.setId(pedido.getRestaurantes().getId());
            restauranteDTO.setName(pedido.getRestaurantes().getName());
            restauranteDTO.setCnpj(pedido.getRestaurantes().getCnpj());
            restauranteDTO.setPhone(pedido.getRestaurantes().getPhone());
            restauranteDTO.setImage(pedido.getRestaurantes().getImage());
            restauranteDTO.setPlateFilter(pedido.getRestaurantes().getPlateFilter());
            pedidoDetalhado.setRestaurante(restauranteDTO);

            BigDecimal precoUnitario = pedido.getPlates().getPrice();
            int quantidade = pedido.getQuantity();
            BigDecimal total = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
            pedidoDetalhado.setFullPrice(total);

            pedidosDetalhados.add(pedidoDetalhado);
        }
        return pedidosDetalhados;
    }

    @Transactional
    public ResponseEntity updatePedido(Long id, PedidoDTO data) {
        if (id != null) {
            Optional<Pedido> optionalPedido = pedidoRepository.findById(Long.valueOf(String.valueOf(id)));
            if (optionalPedido.isPresent()) {
                Pedido pedido = optionalPedido.get();
                pedido.setQuantity((data.quantity()));
                pedido.setPaymentType(data.paymentType());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public void deletePedido(Long id) {
        pedidoRepository.deleteById(Long.valueOf(String.valueOf(id)));
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
}





