package farmacia.servicios.dao;

import farmacia.servicios.daomain.Pedido;
import farmacia.servicios.daomain.ProductoPedido;


import java.util.List;

public interface PedidoDao {
    List<Pedido> pedirPedidos();
    List<ProductoPedido> pedirProductoPedido(Integer idPedido);
}
