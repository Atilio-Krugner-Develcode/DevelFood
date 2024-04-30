package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Estatus;
import br.com.develfood.develfood.Class.NovoStatusRequest;
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
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/create")
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody CriarPedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.criarPedido(pedidoDTO);
        PedidoDTO pedidoCriadoDTO = new PedidoDTO(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriadoDTO);
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

    @PutMapping("/{pedidoId}/status")
    public ResponseEntity<Void> atualizarStatusPedido(@PathVariable Long pedidoId, @RequestBody NovoStatusRequest request) {
        Estatus novoStatus = Estatus.valueOf(request.getNovoStatus());
        pedidoService.atualizarStatusPedido(pedidoId, novoStatus);
        return ResponseEntity.ok().build();
    }
}
