package farmacia.servicios.servicios;

import farmacia.servicios.daomain.Producto;

import java.util.List;

public interface ProductoService {
    public List<Producto> pedirProducto (Integer pagina);
    Integer totalPagina ();
    List<Producto> pedirProducto(String informacion);
    Boolean editarProducto(Producto producto);
}
