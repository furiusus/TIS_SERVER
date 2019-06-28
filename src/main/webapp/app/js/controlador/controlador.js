/**
 * Created by Alumno on 20/05/2017.
 */

var controlador = app.controller('MyController',function($scope,$http){

    // VENTANAS
    $scope.inicioV=true;
    $scope.registroV=false;
    $scope.loginV=false;
    $scope.catalogoProductoClienteV=false;
    $scope.listaPedidoClienteV=false;
    $scope.listaPedidoFarmaceuticoV=false;
    $scope.editarProductoV=false;

    // MOVIMIENTO ENTRE VENTANAS
    $scope.ocultarV = function(){
        $scope.inicioV=false;
        $scope.registroV=false;
        $scope.loginV=false;
        $scope.catalogoProductoClienteV=false;
        $scope.listaPedidoClienteV=false;
        $scope.listaPedidoFarmaceuticoV=false;
        $scope.editarProductoV=false;
    };
    $scope.irInicioV = function(){
        $scope.ocultarV();
        $scope.inicioV=true;
    };

    $scope.irRegistroV = function(){
        $scope.ocultarV();
        $scope.registroV=true;
    };

    $scope.irLoginV = function(){
        $scope.ocultarV();
        $scope.loginV=true;
    };

    $scope.irCatalogoProductoClienteV = function(){
        $scope.ocultarV();
        $scope.catalogoProductoClienteV=true;
        if($scope.busquedaProducto.busqueda) {

        }else{
            $scope.contarPagina();
            $scope.cargarListaProducto($scope.pagina);
        }
    };

    $scope.irListaPedidoClienteV= function(){
        $scope.ocultarV();
        $scope.pedirPedidoClienteVigente();
        $scope.listaPedidoClienteV=true;
    };

    $scope.irListaPedidoFarmaceuticoV= function(){
        $scope.ocultarV();
        $scope.listaPedidoFarmaceuticoV=true;
        $scope.pedirPedido();
    };

    $scope.irEditarProductoV= function(){
        $scope.ocultarV();
        $scope.editarProductoV=true;
        $scope.contarPagina();
        $scope.cargarListaProducto($scope.pagina);
    };

    // VARIABLES

    $scope.definirVariables=function(){
        $scope.mensajeLogin = null;
        $scope.mensajeRegistro = null;
        $scope.listaProducto = null;
        $scope.paginas = [];
        $scope.ubicacion = {
            paginaUp : 11,
            paginaDown : 1,
            paginas:null
        };
        $scope.pagina = 1;
        $scope.productoEditar = 0;
        $scope.busquedaProducto = {
            busqueda:false,
            informacion:null,
        };
        $scope.listaPedidos = null;
        $scope.usuario = {
            idUsuario:null,
            dni:null,
            nombres:null,
            apellidos:null,
            clave2:"",
            correo:"",
            clave:"",
            telefono:""
        };
        $scope.producto={
            idProducto:null,
            nombre:null,
            descripcion:null,
            marca:null,
            precio:null,
            url:null,
            stock:null,
            cantidad:null
        };
        $scope.pedido={
            idPedido:null,
            fecha:null,
            cliente:null,
            telefono:null,
            dni:null
        };
        $scope.productoPedido={
            nombre:null,
            descripcion:null,
            precio:null,
            cantidad:null
        };
        $scope.compra = {
            idProducto:null,
            idCliente:null,
            cantidad:null
        };
        $scope.precioTotal=null;
        $scope.listaProductoPedido=null;
        $scope.countP = 0;
    };
    $scope.definirVariables();


    // CONFIGURACION DE CONEXION

    $scope.config_json = {
        headers : {
            'Content-Type': 'application/json;charset=utf-8;'
        }
    };
    $scope.config_form = {
        headers : {
            'Content-Type': ' application/x-www-form-urlencoded;'
        }
    };

    $scope.noVacio = function(i){
        var respuesta =  (i==="" || i==='' || i==null);
        return !respuesta;
    };
    //FUNCIONES DE OPERACION
    $scope.validarDatos = function () {
        var data  = $scope.usuario;
        if($scope.noVacio($scope.usuario.correo) && $scope.noVacio($scope.usuario.clave) ){
            var res = $http.post('/verificarUsuario', data, $scope.config_json);
            res.then(function (response) {
                var respuesta = response.data;
                if (respuesta.existe) {
                    $scope.usuario = response.data.usuarioResponse;
                    if (respuesta.admin) {
                        $scope.irListaPedidoFarmaceuticoV();
                    } else {
                        $scope.irCatalogoProductoClienteV();
                    }
                } else {
                    $scope.mensajeLogin = "La contraseña no pertenece a la cuenta o cuenta no existe";
                }
            });
        }else{
            $scope.mensajeLogin = "Rellene los casilleros en blanco";
        }
    };

    $scope.registrarCliente = function () {
        if($scope.noVacio($scope.usuario.nombres) && $scope.noVacio($scope.usuario.apellidos) &&
            $scope.noVacio($scope.usuario.correo) && $scope.noVacio($scope.usuario.telefono) &&
            $scope.noVacio($scope.usuario.dni) && $scope.noVacio($scope.usuario.clave) &&
            $scope.noVacio($scope.usuario.clave2)) {
            if ($scope.usuario.clave === $scope.usuario.clave2) {
                var data = $scope.usuario;
                var res = $http.post('/agregarCliente', data, $scope.config_json);
                res.then(function (response) {
                    var data = response.data;
                    if (!data) {
                        $scope.mensajeRegistro = "Este correo o dni ya esta registrado";
                    } else {
                        alert("Usuario registrado correctamente");
                        $scope.irCatalogoProductoClienteV();
                    }
                })
            } else {
                $scope.mensajeRegistro = "Contraseñas no coinciden";
            }
        }else {
            $scope.mensajeRegistro = "Complete todos los campos";
        }
    };

    $scope.cargarListaProducto=function(p){
        $scope.pagina=p;
        if (p>=$scope.ubicacion.paginaDown+($scope.ubicacion.paginaUp-$scope.ubicacion.paginaDown)/2){
            $scope.aumentarPaginaUp();
        }else{
            $scope.disminuriPaginaDown();
        }
        var data = $scope.pagina;
        $http.post('/productosPag','pagina='+data,$scope.config_form).then(function (value) {
            $scope.listaProducto=value.data;
        });
    };

    $scope.buscarProducto =  function (){
        if($scope.busquedaProducto.informacion !="" && $scope.busquedaProducto.informacion.length>=3) {
            $scope.busquedaProducto.busqueda = true;
            var data = $scope.busquedaProducto.informacion;
            $http.post('/buscarProducto', 'informacion=' + data, $scope.config_form).then(function (value) {
                $scope.listaProducto = value.data;
            });
        }else{
            $scope.cargarListaProducto($scope.pagina);
        }
    };
    $scope.enviarProducto = function(producto){
        var data = producto;
        $http.post('/editarProducto',data,$scope.config_json).then(function (value) {
            if(value.data){
                $scope.productoEditar=0;
            }
        });
    };

    $scope.contarPagina = function(){
        $http.post('/totalPagina',{}).then(function (value) {
            var paginas = value.data;
            $scope.ubicacion.paginas = value.data;
            $scope.actualizarPaginado(paginas);
        });
    };
    $scope.actualizarPaginado = function (paginas){
        $scope.paginas = [];
        for(var p=1;p<=paginas;p++){
            if(p<=$scope.ubicacion.paginaUp && p>=$scope.ubicacion.paginaDown){
                $scope.paginas.splice(p-1,0,p);
            }

        }
    };
    $scope.siguiente = function(){
        if($scope.paginas.length!=$scope.pagina){
            $scope.pagina=$scope.pagina+1;
            $scope.cargarListaProducto($scope.pagina);
            $scope.aumentarPaginaUp();
            $scope.actualizarPaginado($scope.ubicacion.paginas);
        }
    };
    $scope.anterior = function(){
        if($scope.pagina!=1){
            $scope.pagina=$scope.pagina-1;
            $scope.cargarListaProducto($scope.pagina);
            $scope.disminuriPaginaDown();
            $scope.actualizarPaginado($scope.ubicacion.paginas);
        }

    };
    $scope.aumentarPaginaUp = function(){
        $scope.ubicacion.paginaUp =$scope.ubicacion.paginaUp+1;
        $scope.ubicacion.paginaDown =$scope.ubicacion.paginaDown+1;
        $scope.actualizarPaginado($scope.ubicacion.paginas);
    };
    $scope.disminuriPaginaDown = function(){
        $scope.ubicacion.paginaUp =$scope.ubicacion.paginaUp-1;
        $scope.ubicacion.paginaDown =$scope.ubicacion.paginaDown-1;
        $scope.actualizarPaginado($scope.ubicacion.paginas);
    };
    $scope.editarProducto=function(idProd){
        $scope.productoEditar = idProd;
    };

    $scope.pedirPedido =function(){
        $http.post('/pedirPedidoFarmaceutico',$scope.config_json).then(function (value) {
            $scope.listaPedidos = value.data;
        })
    };

    $scope.pedirProductoPedido=function(idPedido){
        $http.post('/pedirProductoPedido','idPedido='+idPedido,$scope.config_form).then(function (value) {
           $scope.listaProductoPedido =value.data.listaProductoPedido;
           $scope.precioTotal = value.data.precioTotal;
        });
    };

    $scope.entregarPedido = function (idPedido) {
        $http.post('/entregarPedido','idPedido='+idPedido,$scope.config_form).then(function (value) {
            $scope.pedirPedido(idPedido);
        })
    };
    $scope.eliminarPedido = function (idPedido) {
        $http.post('/eliminarPedido','idPedido='+idPedido,$scope.config_form).then(function (value) {
            $scope.pedirPedido(idPedido);
        })
    };
    $scope.agregarCompra=function(idProducto,cantidad){
        if(cantidad>0) {
            $scope.compra.idCliente = $scope.usuario.idUsuario;
            $scope.compra.idProducto = idProducto;
            $scope.compra.cantidad = cantidad;
            var data = $scope.compra;
            $http.post('/agregarCompra', data, $scope.config_json).then(function (value) {
                if(value.data==0){
                    alert("PRODUCTO AGREGADO");
                }else{
                    alert("NO DISPONEMOS DE LA CANTIDAD SOLICITADA\nCONTAMOS CON "+value.data+" UNIDADES");
                }
            });
        }
    };

    $scope.pedirPedidoClienteVigente=function(){
        $http.post('/pedirPedidoClienteVigente','idCliente='+$scope.usuario.idUsuario,$scope.config_form).then(function (value) {
            console.log(value.data);
            $scope.listaProductoPedido =value.data.listaProductoPedido;
            $scope.precioTotal = value.data.precioTotal;
        }).catch(function (value) {
            alert("Sin pedido");
            $scope.listaProductoPedido =null;
        });
    };
    $scope.enviarPedido = function(){
        var r = confirm("¿QUIERE ENVIAR PEDIDO?");
        if(r==true){
            $http.post('/enviarPedido','idCliente='+$scope.usuario.idUsuario,$scope.config_form).then(function (value) {
                console.log(value.data);
                if(value.data == 0){
                    let t= alert("PEDIDO ENVIADO, POR FAVOR ACERCARCE A RECOGER SU PEDIDO EN MENOS DE 30 MIN ;)");
                    $scope.irListaPedidoClienteV();
                }else{
                    let t= alert("PEDIDO NO ENVIADO; por favor agrege productos al pedido");
                }
            });
        }else{
        }
    };
    $scope.borrarPedido = function(){
        alert("PEDIDO BORRADO :'c");
    };

    $scope.cerrarSesion = function () {
        $scope.irInicioV();
        $scope.definirVariables();
    }
});

    function soloNumeros(e)
    {
        var keynum = window.event ? window.event.keyCode : e.which;
        if ((keynum == 8) || (keynum == 46))
            return true;
        return /\d/.test(String.fromCharCode(keynum));
    }
function soloLetras(e)
{
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum == 8) || (keynum == 46))
        return true;
    return !/\d/.test(String.fromCharCode(keynum));
}