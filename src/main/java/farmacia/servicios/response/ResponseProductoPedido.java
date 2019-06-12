package farmacia.servicios.response;

import farmacia.servicios.daomain.ProductoPedido;

import java.util.List;

public class ResponseProductoPedido {
    List<ProductoPedido> listaProductoPedido;
    Float precioTotal;

    public List<ProductoPedido> getListaProductoPedido() {
        return listaProductoPedido;
    }

    public void setListaProductoPedido(List<ProductoPedido> listaProductoPedido) {
        this.listaProductoPedido = listaProductoPedido;
    }

    public Float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Float precioTotal) {
        this.precioTotal = precioTotal;
    }
}
