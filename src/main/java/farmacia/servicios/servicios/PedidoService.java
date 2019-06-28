package farmacia.servicios.servicios;

import farmacia.servicios.daomain.Pedido;
import farmacia.servicios.daomain.Producto;
import farmacia.servicios.request.RequestCompra;
import farmacia.servicios.response.ResponseProductoPedido;

import java.util.List;

public interface PedidoService {
    List<Pedido> pedirPedidosFarmaceutico();
    ResponseProductoPedido pedirProductoPedido(Integer idPedido);
    void cambiarEstadoPedido(Integer estado,Integer idPedido);
    Integer crearCompra(RequestCompra requestCompra);
    ResponseProductoPedido pedirPedidosClienteVigente(Integer idCliente);
    Integer enviarPedido(Integer idCliente);
}
