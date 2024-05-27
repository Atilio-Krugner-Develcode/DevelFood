package br.com.develfood.develfood.Controller;

import br.com.develfood.develfood.Class.Estatus;
import br.com.develfood.develfood.Class.NovoStatusRequest;
import br.com.develfood.develfood.Class.Pedido.*;
import br.com.develfood.develfood.Services.PedidoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {


    @Autowired
    private PedidoService pedidoService;


    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criarPedido(@Valid @RequestBody CriarPedidoDTO pedidoDTO, HttpServletRequest request) {
        try {
            Long pedidoId = pedidoService.criarPedido(pedidoDTO, request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Pedido criado com sucesso. ID do pedido: " + pedidoId);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar o pedido: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedido(@PathVariable Long id) {
        try {
            PedidoResponseDTO pedido = pedidoService.buscarPedido(id);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pratos")
    public ResponseEntity<List<PedidoListDTO>> listarPratosDoCliente(HttpServletRequest request) {
        try {
           var pedidoResponseDTO = pedidoService.pedidoResponseDTO(request);
           return  ResponseEntity.ok().body(pedidoResponseDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{pedidoId}/status")
    public ResponseEntity<Void> atualizarStatusPedido(@PathVariable Long pedidoId, @RequestBody NovoStatusRequest request) {
        Estatus novoStatus = Estatus.valueOf(request.getNovoStatus());
        pedidoService.atualizarStatusPedido(pedidoId, novoStatus);
        return ResponseEntity.ok().build();
    }
}


