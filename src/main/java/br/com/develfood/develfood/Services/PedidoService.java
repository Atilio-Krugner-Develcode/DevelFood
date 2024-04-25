package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.*;
import br.com.develfood.develfood.Record.PedidoDTO;
import br.com.develfood.develfood.Repository.ClientRepository;
import br.com.develfood.develfood.Repository.PedidoRepository;

import br.com.develfood.develfood.Repository.PlateRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
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

    public Pedido criarPedido(CriarPedidoDTO pedidoDTO) {
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
        pedido.setQuantidade(pedidoDTO.getQuantidade());
        pedido.setEstatus(pedido.getEstatus());

        return pedidoRepository.save(pedido);
    }

    public PedidoDTO obterPedidoDetalhadoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado com o ID: " + id));

        Cliente cliente = pedido.getCliente();
        Plates prato = pedido.getPlates();

        BigDecimal total;
        if (prato != null) {
            total = prato.getPreco().multiply(BigDecimal.valueOf(pedido.getQuantidade()));
        } else {

            total = BigDecimal.ZERO;
        }

        PedidoDTO pedidoDetalhadoDTO = new PedidoDTO(
                pedido.getId(),
                total,
                pedido.getQuantidade(),
                "Em preparação",
                prato,
                cliente
        );

        return pedidoDetalhadoDTO;
    }


}



