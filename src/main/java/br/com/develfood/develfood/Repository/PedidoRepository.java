package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Cliente;
import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Class.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {



    List<Pedido> findByCliente(Cliente cliente);

    List<Pedido> findByClienteId(Long clienteId);
}
