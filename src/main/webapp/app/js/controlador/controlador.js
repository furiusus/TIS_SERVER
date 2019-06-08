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
        $scope.cargarListaProducto();
    };

    $scope.irListaPedidoClienteV= function(){
        $scope.ocultarV();
        $scope.listaPedidoClienteV=true;
    };

    $scope.irListaPedidoFarmaceuticoV= function(){
        $scope.ocultarV();
        $scope.listaPedidoFarmaceuticoV=true;
    };

    $scope.irEditarProductoV= function(){
        $scope.ocultarV();
        $scope.editarProductoV=true;
    };

    // VARIABLES

    $scope.definirVariables=function(){
        $scope.mensajeLogin = null;
        $scope.mensajeRegistro = null;
        $scope.listaProducto = null;
        $scope.pagina = 1;
        $scope.class= null;
        $scope.is=[1,2,3];
        $scope.usuario = {
            id:null,
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
            url:null
        };

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

    //FUNCIONES DE OPERACION
    $scope.validarDatos = function () {
        var data  = $scope.usuario;
        if($scope.usuario.correo!="" && $scope.usuario.clave!="") {
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
        var data  = $scope.usuario;
        if($scope.usuario.clave==$scope.usuario.clave2) {
            var res = $http.post('/agregarCliente', data, $scope.config_json);
            res.then(function (response) {
                var data = response.data;
                if (!data) {
                    $scope.mensajeRegistro = "Este correo o dni ya esta registrado";
                }else{
                    $scope.irCatalogoProductoClienteV();
                }
            })
        }else{
            $scope.mensajeRegistro = "Contraseñas no coinciden";
        }
    };

    $scope.cargarListaProducto=function(){
        var data = $scope.pagina;
        $http.post('/productosPag','pagina='+data,$scope.config_form).then(function (value) {
            $scope.listaProducto=value.data;
            console.log($scope.listaProducto);
        });
    };

    $scope.irPag = function (x){
        var gaa = "lk-"+x;
        document.getElementById(gaa).setAttribute("class","activate");
    };

    $scope.cerrarSesion = function () {
        $scope.irInicioV();
        $scope.definirVariables();
    }
});