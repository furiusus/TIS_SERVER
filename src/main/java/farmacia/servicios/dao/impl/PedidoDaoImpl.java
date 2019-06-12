package farmacia.servicios.dao.impl;

import farmacia.servicios.dao.PedidoDao;
import farmacia.servicios.dao.SimpleJdbc;
import farmacia.servicios.daomain.Pedido;
import farmacia.servicios.daomain.ProductoPedido;
import farmacia.servicios.request.RequestCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PedidoDaoImpl extends SimpleJdbc implements PedidoDao {
    @Override
    public List<Pedido> pedirPedidos(Integer estado) {
        List<Pedido> listaPedido;
        try{
            listaPedido = new ArrayList<Pedido>();
            String sql = "select p.id_pedido ID_PEDIDO,TO_CHAR(p.fecha_pedido,'DD/MM/YYYY HH:MI') FECHA_PEDIDO,u.nombres NOMBRES,u.telefono TELEFONO,u.dni DNI from pedido p inner join usuario u on p.id_cliente=u.id_usuario where p.estado=?";
            List<Map<String,Object>> lista = getJdbcTemplate().queryForList(sql,new Object[]{estado});
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

    @Override
    public void cambiarEstadoPedido(Integer estado,Integer idPedido){
        try{
            String sql = "update pedido set estado=? where ID_PEDIDO = ?";
            getJdbcTemplate().update(sql,new Object[]{estado,idPedido});
        }catch (NullPointerException e){

        }
    }

    @Override
    public Integer existePedido(Integer idUsuario) {
        String sql ="select UNIQUE ID_PEDIDO from pedido where id_cliente=? AND ESTADO=?";
        Integer a;
        try {
            a = getJdbcTemplate().queryForObject(sql,new Object[]{idUsuario,3},Integer.class);
        }catch (NullPointerException | EmptyResultDataAccessException e){
            a=0;
        }
        return a;
    }

    @Override
    public Integer crearProductoPedido(RequestCompra requestCompra) {
        Integer idPedido = existePedido(requestCompra.getIdCliente());
        Integer stock = getJdbcTemplate().queryForObject("SELECT STOCK FROM PRODUCTO WHERE ID_PRODUCTO = ?",new Object[]{requestCompra.getIdProducto()},Integer.class);
        if(requestCompra.getCantidad()<=stock) {
            if (idPedido== 0) {
                idPedido = getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM PEDIDO",Integer.class)+1;
                String sql = "insert into pedido(ID_PEDIDO, FECHA_PEDIDO, ID_CLIENTE, ID_FARMACEUTICO,ESTADO) values(?,null,?,1,3)";
                getJdbcTemplate().update(sql, new Object[]{idPedido, requestCompra.getIdCliente()});
            }
            String sqlInsterProducto = "INSERT INTO COMPRA(ID_COMPRA,ID_PEDIDO,ID_PRODUCTO,PRECIO,CANTIDAD) VALUES (?,?,?,?,?)";
            Float precio = getJdbcTemplate().queryForObject("SELECT PRECIO FROM PRODUCTO WHERE ID_PRODUCTO=?", new Object[]{requestCompra.getIdProducto()}, Float.class);
            Integer idCompra = getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM COMPRA", Integer.class) + 1;
            getJdbcTemplate().update(sqlInsterProducto, new Object[]{idCompra, idPedido, requestCompra.getIdProducto(), precio, requestCompra.getCantidad()});
            return 0;
        }else{
            return stock;
        }
    }

    @Override
    public List<Pedido> pedirPedidosCliente(Integer estado, Integer idCliente) {
        List<Pedido> listaPedido;
        try{
            listaPedido = new ArrayList<Pedido>();
            String sql = "select p.id_pedido ID_PEDIDO,TO_CHAR(p.fecha_pedido,'DD/MM/YYYY HH:MI') FECHA_PEDIDO,u.nombres NOMBRES,u.telefono TELEFONO,u.dni DNI from pedido p inner join usuario u on p.id_cliente=u.id_usuario where p.estado=? AND P.ID_CLIENTE=?";
            List<Map<String,Object>> lista = getJdbcTemplate().queryForList(sql,new Object[]{estado,idCliente});
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
}
