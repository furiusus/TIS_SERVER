package farmacia.servicios.dao.impl;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import farmacia.servicios.dao.ProductoDao;
import farmacia.servicios.dao.SimpleJdbc;
import farmacia.servicios.daomain.Producto;
import farmacia.servicios.daomain.Usuario;
import farmacia.servicios.servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProductoDaoImpl extends SimpleJdbc implements ProductoDao {
    @Autowired
    private ProductoService productoService;
    @Override
    public List<Producto> obtenerProductos(Integer pagina) {
        List<Producto> listaResponse=new ArrayList<Producto>();
        try {
            Integer totalPagina = productoService.totalPagina();
            int productosFin;
            if(!pagina.equals(totalPagina)) {
                productosFin = 6;
            }else{
                productosFin = contarProductos()%6;
            }
            String sql = "SELECT ID_PRODUCTO,NOMBRE,MARCA,PRECIO,DESCRIPCION,URL_IMAGEN,VIA_INGRESO,STOCK FROM (SELECT * FROM (SELECT * FROM PRODUCTO ORDER BY NOMBRE) WHERE ROWNUM<=" + pagina * 6 + " ORDER BY ROWNUM DESC) WHERE ROWNUM<="+productosFin+" AND STOCK >0 ORDER BY NOMBRE ASC";
            List<Map<String, Object>> lista = getJdbcTemplate().queryForList(sql);
            if(lista!=null && lista.size()>0){
                for(int i =0;i<lista.size();i++){
                    Producto producto = new Producto();
                    producto.setIdProducto(((BigDecimal)lista.get(i).get("ID_PRODUCTO")).intValue());
                    producto.setNombre((String) lista.get(i).get("NOMBRE"));
                    producto.setMarca((String)lista.get(i).get("MARCA"));
                    producto.setPrecio(((BigDecimal) lista.get(i).get("PRECIO")).floatValue());
                    producto.setDescripcion((String)lista.get(i).get("DESCRIPCION"));
                    producto.setViaIngreso((String)lista.get(i).get("VIA_INGRESO"));
                    producto.setUrl((String)lista.get(i).get("URL_IMAGEN"));
                    producto.setStock(((BigDecimal)lista.get(i).get("STOCK")).intValue());
                    listaResponse.add(producto);
                }
            }
        }catch (NullPointerException | EmptyResultDataAccessException e){
            listaResponse = null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaResponse;
    }

    @Override
    public Integer contarProductos() {
        int cantidadProductos;
        try {
            String sql = "SELECT COUNT(*) FROM PRODUCTO";
            cantidadProductos=getJdbcTemplate().queryForObject(sql,Integer.class);
        }catch (NullPointerException e){
            cantidadProductos = 0;
        }

        return cantidadProductos;
    }

    @Override
    public List<Producto> buscarProducto(String informacion) {
        List<Producto> listaResponse =new ArrayList<Producto>();
        String ingresar = "%"+informacion+"%";
        try {
            String sql = "SELECT id_producto, nombre, marca, descripcion, via_ingreso, precio, stock, url_imagen FROM PRODUCTO WHERE " +
                    "NOMBRE LIKE UPPER(?) OR DESCRIPCION LIKE UPPER(?) OR MARCA LIKE UPPER(?)";
            List<Map<String, Object>> lista = getJdbcTemplate().queryForList(sql,new Object[]{ingresar,ingresar,ingresar});
            if (lista != null && lista.size() > 0) {
                for (int i = 0; i < lista.size(); i++) {
                    Producto producto = new Producto();
                    producto.setIdProducto(((BigDecimal) lista.get(i).get("ID_PRODUCTO")).intValue());
                    producto.setNombre((String) lista.get(i).get("NOMBRE"));
                    producto.setMarca((String) lista.get(i).get("MARCA"));
                    producto.setPrecio(((BigDecimal) lista.get(i).get("PRECIO")).floatValue());
                    producto.setDescripcion((String) lista.get(i).get("DESCRIPCION"));
                    producto.setViaIngreso((String) lista.get(i).get("VIA_INGRESO"));
                    producto.setUrl((String) lista.get(i).get("URL_IMAGEN"));
                    producto.setStock(((BigDecimal) lista.get(i).get("STOCK")).intValue());
                    listaResponse.add(producto);
                }
            }
        }catch (NullPointerException e){
            listaResponse = null;
        }
        return listaResponse;
    }
}
