package farmacia.servicios.dao.impl;

import farmacia.servicios.dao.PedidoDao;
import farmacia.servicios.dao.SimpleJdbc;
import farmacia.servicios.daomain.Pedido;
import farmacia.servicios.daomain.ProductoPedido;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PedidoDaoImpl extends SimpleJdbc implements PedidoDao {
    @Override
    public List<Pedido> pedirPedidos() {
        List<Pedido> listaPedido;
        try{
            listaPedido = new ArrayList<Pedido>();
            String sql = "select p.id_pedido ID_PEDIDO,TO_CHAR(p.fecha_pedido,'DD/MM/YYYY HH:MI') FECHA_PEDIDO,u.nombres NOMBRES,u.telefono TELEFONO,u.dni DNI from pedido p inner join usuario u on p.id_cliente=u.id_usuario where p.estado=0";
            List<Map<String,Object>> lista = getJdbcTemplate().queryForList(sql);
            if(lista!=null && lista.size()>0){
                for(int i = 0 ; i<lista.size();i++){
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(((BigDecimal)lista.get(i).get("ID_PEDIDO")).intValue());
                    pedido.setFecha((String) lista.get(i).get("FECHA_PEDIDO"));
                    pedido.setCliente((String)lista.get(i).get("NOMBRES"));
                    pedido.setTelefono((String)lista.get(i).get("TELEFONO"));
                    pedido.setDni((String)lista.get(i).get("DNI"));
                    listaPedido.add(pedido);
                }
            }
        }catch(NullPointerException e){
            listaPedido = null;
        }
        return listaPedido;
    }

    @Override
    public List<ProductoPedido> pedirProductoPedido(Integer idPedido) {
        List<ProductoPedido> listaCompraProducto ;
        try{
            listaCompraProducto = new ArrayList<ProductoPedido>();
            String sql ="SELECT P.NOMBRE NOMBRE,P.DESCRIPCION DESCRIPCION,C.PRECIO PRECIO,C.CANTIDAD CANTIDAD FROM COMPRA C LEFT JOIN PRODUCTO P ON C.ID_PRODUCTO = P.ID_PRODUCTO WHERE C.ID_PEDIDO = ?";
            List<Map<String,Object>> lista = getJdbcTemplate().queryForList(sql,new Object[]{idPedido});
            if(lista!=null && lista.size()>0){
                for (int i = 0; i < lista.size(); i++) {
                    ProductoPedido productoPedido = new ProductoPedido();
                    productoPedido.setNombre((String)lista.get(i).get("NOMBRE"));
                    productoPedido.setCantidad(((BigDecimal)lista.get(i).get("CANTIDAD")).intValue());
                    productoPedido.setDescripcion((String)lista.get(i).get("DESCRIPCION"));
                    productoPedido.setPrecio(((BigDecimal)lista.get(i).get("PRECIO")).floatValue());
                    listaCompraProducto.add(productoPedido);
                }
            }
        }catch(NullPointerException e){
            listaCompraProducto = null;
            System.out.println("Null");
        }
        return listaCompraProducto;
    }
}
