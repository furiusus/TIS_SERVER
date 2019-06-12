package farmacia.servicios.servicios.impl;

import farmacia.servicios.dao.PedidoDao;
import farmacia.servicios.dao.SimpleJdbc;
import farmacia.servicios.daomain.Pedido;
import farmacia.servicios.daomain.Producto;
import farmacia.servicios.daomain.ProductoPedido;
import farmacia.servicios.response.ResponseProductoPedido;
import farmacia.servicios.servicios.PedidoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl extends SimpleJdbc implements PedidoService {
    @Autowired
    private PedidoDao pedidoDao;
    @Override
    public List<Pedido> pedirPedidos() {
        return pedidoDao.pedirPedidos();
    }

    @Override
    public ResponseProductoPedido pedirProductoPedido(Integer idPedido) {
        ResponseProductoPedido responseProductoPedido = new ResponseProductoPedido();
        List<ProductoPedido> lista = pedidoDao.pedirProductoPedido(idPedido);
        responseProductoPedido.setListaProductoPedido(new ArrayList<ProductoPedido>());
        Float precioTotal = 0f ;
        for (ProductoPedido x:lista) {
            responseProductoPedido.getListaProductoPedido().add(x);
            precioTotal += x.getPrecio()*x.getCantidad();
        }
        responseProductoPedido.setPrecioTotal(precioTotal);
        return responseProductoPedido;
    }
}
