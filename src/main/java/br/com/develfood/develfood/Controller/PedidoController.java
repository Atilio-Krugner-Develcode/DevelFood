package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Pedido.CriarPedidoDTO;
import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Class.Pedido.PedidoDetalhado;
import br.com.develfood.develfood.Record.PedidoDTO;
import br.com.develfood.develfood.Record.PlateDTO;
import br.com.develfood.develfood.Services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {


    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/create")
    public ResponseEntity<String> criarPedido(@RequestBody CriarPedidoDTO pedidoDTO) {
        pedidoService.criarPedido(pedidoDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Pedido criado com sucesso!");
    }

    @GetMapping("/list")
    public ResponseEntity<List<PedidoDetalhado>> obterTodosPedidos() {
        List<PedidoDetalhado> pedidosDetalhados = pedidoService.obterTodosPedidosDetalhados();
        return ResponseEntity.ok(pedidosDetalhados);
    }
    @PutMapping("/{id}")
    public ResponseEntity updatePedido(@PathVariable Long id, @RequestBody @Validated PedidoDTO data) {
        return pedidoService.updatePedido(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}
