package farmacia.servicios.servicios.impl;

import farmacia.servicios.dao.ProductoDao;
import farmacia.servicios.daomain.Producto;
import farmacia.servicios.servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoDao productoDao;
    @Override
    public List<Producto> pedirProducto(Integer pagina) {
        return productoDao.obtenerProductos(pagina);
    }

    @Override
    public Integer totalPagina() {
        Integer cantPaginas=((Integer)productoDao.contarProductos()/6)+1;
        return cantPaginas;
    }

    @Override
    public List<Producto> pedirProducto(String informacion) {
        return productoDao.buscarProducto(informacion);
    }

    @Override
    public Boolean editarProducto(Producto producto) {
        if(producto.getDescripcion().length()<=254 && producto.getPrecio()<=999.99 && producto.getPrecio()>=0 && producto.getStock()>=0){
            productoDao.editarProducto(producto);
            return true;
        }else{
            return false;
        }

    }
}
