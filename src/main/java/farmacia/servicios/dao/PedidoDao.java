package farmacia.servicios.dao;

import farmacia.servicios.daomain.Pedido;
import farmacia.servicios.daomain.ProductoPedido;
import farmacia.servicios.request.RequestCompra;


import java.util.List;

public interface PedidoDao {
    List<Pedido> pedirPedidos(Integer estado);
    List<ProductoPedido> pedirProductoPedido(Integer idPedido);
    void cambiarEstadoPedido(Integer estado,Integer idPedido);
    Integer existePedido(Integer idUsuario);
    Integer crearProductoPedido(RequestCompra requestCompra);
    List<Pedido> pedirPedidosCliente(Integer estado,Integer idCliente);
}
