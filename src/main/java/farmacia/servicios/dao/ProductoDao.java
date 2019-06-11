package farmacia.servicios.dao;

import farmacia.servicios.daomain.Producto;

import java.util.List;

public interface ProductoDao {
    public List<Producto> obtenerProductos(Integer pagina);
    Integer contarProductos();
    List<Producto> buscarProducto(String informacion);
    void editarProducto(Producto producto);
}
