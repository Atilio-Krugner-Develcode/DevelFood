package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.*;
import br.com.develfood.develfood.Class.Pedido.CriarPedidoDTO;
import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Class.Pedido.PedidoDetalhado;
import br.com.develfood.develfood.Record.PedidoDTO;
import br.com.develfood.develfood.Record.PlatesDTO;
import br.com.develfood.develfood.Record.RequestRestaurant;
import br.com.develfood.develfood.Repository.ClientRepository;
import br.com.develfood.develfood.Repository.PedidoRepository;

import br.com.develfood.develfood.Repository.PlateRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
        pedido.setData(LocalDate.now());
        pedido.setFormaPagamento(pedidoDTO.getFormaPagamento());

        BigDecimal precoUnitario = pedido.getPlates().getPreco();
        int quantidade = pedido.getQuantidade();
        BigDecimal total = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }

    public List<PedidoDetalhado> obterTodosPedidosDetalhados() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoDetalhado> pedidosDetalhados = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            PedidoDetalhado pedidoDetalhado = new PedidoDetalhado();
            pedidoDetalhado.setId(pedido.getId());
            pedidoDetalhado.setTotal(pedido.getTotal());
            pedidoDetalhado.setQuantidade(pedido.getQuantidade());
            pedidoDetalhado.setEstatus("Em preparação");
            pedidoDetalhado.setData(pedido.getData());
            pedidoDetalhado.setFormaPagamento(pedido.getFormaPagamento());

            Plates pratoDTO = new Plates();
            pratoDTO.setId(pedido.getPlates().getId());
            pratoDTO.setNome(pedido.getPlates().getNome());
            pratoDTO.setDescricao(pedido.getPlates().getDescricao());
            pratoDTO.setFoto(pedido.getPlates().getFoto());
            pratoDTO.setPreco(pedido.getPlates().getPreco());
            pratoDTO.setCategoria(pedido.getPlates().getCategoria());
            pratoDTO.setPlateFilter(pedido.getRestaurantes().getPlateFilter());
            pedidoDetalhado.setPrato(pratoDTO);

            Restaurant restauranteDTO = new Restaurant();
            restauranteDTO.setId(pedido.getRestaurantes().getId());
            restauranteDTO.setNome(pedido.getRestaurantes().getNome());
            restauranteDTO.setCpf(pedido.getRestaurantes().getCpf());
            restauranteDTO.setTelefone(pedido.getRestaurantes().getTelefone());
            restauranteDTO.setFoto(pedido.getRestaurantes().getFoto());
            restauranteDTO.setPlateFilter(pedido.getRestaurantes().getPlateFilter());
            pedidoDetalhado.setRestaurante(restauranteDTO);

            BigDecimal precoUnitario = pedido.getPlates().getPreco();
            int quantidade = pedido.getQuantidade();
            BigDecimal total = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
            pedidoDetalhado.setTotal(total);

            pedidosDetalhados.add(pedidoDetalhado);
        }
        return pedidosDetalhados;
    }
}



