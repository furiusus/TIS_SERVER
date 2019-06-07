package farmacia.servicios.dao.impl;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import farmacia.servicios.dao.ProductoDao;
import farmacia.servicios.dao.SimpleJdbc;
import farmacia.servicios.daomain.Producto;
import farmacia.servicios.daomain.Usuario;
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
    @Override
    public List<Producto> obtenerProductos(Integer pagina) {
        List<Producto> listaResponse=new ArrayList<Producto>();
        try {
            String sql = "SELECT ID_PRODUCTO,NOMBRE,MARCA,PRECIO,DESCRIPCION,URL_IMAGEN,VIA_INGRESO FROM (SELECT * FROM (SELECT * FROM PRODUCTO ORDER BY NOMBRE) WHERE ROWNUM<="+pagina*6 +" ORDER BY ROWNUM DESC) WHERE ROWNUM<=6 AND STOCK >0";
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
}
