app.directive('inicioApp',function () {
    return{
        restrict:'E',
        templateUrl:'app/js/templates/inicio.html'
    }
});

app.directive('registroApp',function () {
    return{
        restrict:'E',
        templateUrl:'app/js/templates/registro.html'
    }
});

app.directive('loginApp',function () {
    return{
        restrict:'E',
        templateUrl:'app/js/templates/login.html'
    }
});

app.directive('barraCliente',function () {
    return{
        restrict:'E',
        templateUrl:'app/js/templates/barraCliente.html'
    }
});

app.directive('barraFarmaceutico',function () {
    return{
        restrict:'E',
        templateUrl:'app/js/templates/barraFarmaceutico.html'
    }
});

app.directive('catalogoProductoCliente',function () {
    return{
        restrict:'E',
        templateUrl:'app/js/templates/catalogoProductoCliente.html'
    }
});

app.directive('listaPedidoCliente',function () {
    return{
        restrict:'E',
        templateUrl:'app/js/templates/listaPedidoCliente.html'
    }
});

app.directive('listaPedidoFarmaceutico',function () {
    return{
        restrict:'E',
        templateUrl:'app/js/templates/listaPedidoFarmaceutico.html'
    }
});

app.directive('editarProducto',function () {
    return{
        restrict:'E',
        templateUrl:'app/js/templates/editarProducto.html'
    }
});