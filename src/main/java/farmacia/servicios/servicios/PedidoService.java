package farmacia.servicios.servicios;

import farmacia.servicios.daomain.Pedido;
import farmacia.servicios.response.ResponseProductoPedido;

import java.util.List;

public interface PedidoService {
    List<Pedido> pedirPedidos();
    ResponseProductoPedido pedirProductoPedido(Integer idPedido);
}
