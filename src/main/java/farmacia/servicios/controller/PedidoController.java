package farmacia.servicios.controller;


import farmacia.servicios.daomain.Pedido;
import farmacia.servicios.daomain.Producto;
import farmacia.servicios.response.ResponseProductoPedido;
import farmacia.servicios.response.ResponseVerificarUsuario;
import farmacia.servicios.daomain.Usuario;
import farmacia.servicios.servicios.PedidoService;
import farmacia.servicios.servicios.ProductoService;
import farmacia.servicios.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
//@CrossOrigin

//@RequestMapping("pedidoController")

public class PedidoController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private PedidoService pedidoService;

    @RequestMapping(value = "/verificarUsuario",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseVerificarUsuario verificarUsuario (@RequestBody Usuario usuario){
        return usuarioService.verificarUsuario(usuario);
    }

    @RequestMapping(value = "/agregarCliente",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Boolean agregarCliente(@RequestBody Usuario usuario){
        return usuarioService.agregarCliente(usuario);
    }


    @RequestMapping(value = "/productosPag", method = RequestMethod.POST)
    public @ResponseBody
    List<Producto> productosPag(@RequestParam Integer pagina){
        return productoService.pedirProducto(pagina);
    }

    @RequestMapping(value = "/totalPagina", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Integer totalPaginas(){
        return productoService.totalPagina();
    }

    @RequestMapping(value = "/buscarProducto",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<Producto> buscarProducto(@RequestParam String informacion){
        return productoService.pedirProducto(informacion);
    }

    @RequestMapping(value = "/editarProducto",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Boolean editarProducto(@RequestBody Producto producto){
        return productoService.editarProducto(producto);
    }

    @RequestMapping(value = "/pedirPedido",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<Pedido> pedirPedido(){
        return  pedidoService.pedirPedidos();
    }

    @RequestMapping(value = "/pedirProductoPedido",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseProductoPedido pedirProductoPedido(@RequestParam Integer idPedido){
        return pedidoService.pedirProductoPedido(idPedido);
    }
}
