package br.com.develfood.develfood.Services;

import br.com.develfood.develfood.Class.*;
import br.com.develfood.develfood.Repository.ClientRepository;
import br.com.develfood.develfood.Repository.PedidoRepository;

import br.com.develfood.develfood.Repository.PlateRepository;
import br.com.develfood.develfood.Repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PedidoService {


    private final PedidoRepository pedidoRepository;
    private final ClientRepository clientRepository;
    private final PlateRepository plateRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClientRepository clientRepository,
                         PlateRepository plateRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clientRepository = clientRepository;
        this.plateRepository = plateRepository;
    }


    public void criarPedido(CriarPedidoDTO pedidoDTO) {
        Cliente cliente = clientRepository.findById(pedidoDTO.getIdCliente())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o ID fornecido: " + pedidoDTO.getIdCliente()));

        Plates prato = plateRepository.findById(String.valueOf(pedidoDTO.getIdPrato()))
                .orElseThrow(() -> new NoSuchElementException("Prato não encontrado com o ID fornecido: " + pedidoDTO.getIdPrato()));

        if (pedidoDTO.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade do pedido deve ser maior que zero");
        }

        String status = "Em preparação";

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setPlates(prato);
        pedido.setQuantidade(pedidoDTO.getQuantidade());
        pedido.setEstatus(status);

        pedidoRepository.save(pedido);
    }
    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        for (Pedido pedido : pedidos) {
            pedido.setPlates(plateRepository.findById(String.valueOf(pedido.getPlates().getId()))
                    .orElseThrow(() -> new NoSuchElementException("Prato não encontrado com o ID: " + pedido.getPlates().getId())));
        }

        return pedidos;
    }




        public Pedido buscarPedidoPorId(Long id) {
            return pedidoRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado"));
        }

        public void atualizarPedido(Long id, Pedido pedidoAtualizado) {
            Pedido pedido = buscarPedidoPorId(id);
            pedido.setId(pedidoAtualizado.getId());
            pedidoRepository.save(pedido);
        }

        public void deletarPedido(Long id) {
            pedidoRepository.deleteById(id);
        }
    }


