package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Pedido.CriarPedidoDTO;
import br.com.develfood.develfood.Class.Pedido.Pedido;
import br.com.develfood.develfood.Class.Pedido.PedidoDetalhado;
import br.com.develfood.develfood.Record.PedidoDTO;
import br.com.develfood.develfood.Services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {


    @Autowired
    private PedidoService pedidoService;

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
}
