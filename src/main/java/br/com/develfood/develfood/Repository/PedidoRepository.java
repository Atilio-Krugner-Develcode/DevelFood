package br.com.develfood.develfood.Repository;

import br.com.develfood.develfood.Class.Pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
