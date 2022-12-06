// 'app' - название приложения
// [] - зависимости
// 'indexController' - контроллер
// $scope - связывает js-документ с html-документом
// $http - связывает js-документ с back-end
angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http) {

//http://localhost:8190/show/index.html

   // корневой адрес приложения
   const contextPath = 'http://localhost:8189/app/api/v1/products';

   // вывести список продуктов
   $scope.loadProducts = function () {
        $http.get(contextPath)
           .then(function successCallback(response) {
           $scope.ProductsList = response.data;
        });
    }

    // удалить продукт
   $scope.deleteProduct = function(productId){
        $http.delete(contextPath + '/' + productId)
          .then(function successCallback(response) {
           $scope.loadProducts();
        });
    }

    // добавить продукт
    $scope.addProduct = function () {
        // в теле post запроса будет передан json-объект ($scope.productDto)
        $http({
            url: contextPath,
            method: 'POST',
            // переменная productDto получена из html
            // ее поля собраны из полей формы
            data: $scope.productDto
        }).then(function (response) {
            $scope.loadProducts();
            // очистить поля формы
            $scope.productDto = null
        });
    };

    // при старте приложения вызвать функцию
    $scope.loadProducts();
});