// I шаг
// 'app' - название приложения
// [] - зависимости
// 'indexController' - контроллер
// $scope - связывает js-документ с html-документом
// $rootScope - глобальный контекст, к которому можно обращаться из любого контроллера
// $http - связывает js-документ с back-end
// $localStorage - ссылка на локально хранилище данных (браузера)
angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http, $localStorage) {

// II шаг
// корневой адрес приложения
    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.loadProducts = function () {
        $http.get(contextPath + '/products')
           .then(function successCallback(response) {
        // в теле ответа будет json-объект корзинки
        // состоит из списка продуктов (items) - массив строк корзинки
        // и суммарной стоимости (totalPrice)
            $scope.ProductsList = response.data;
         });
        };

    $scope.loadCart = function(){
        $http.get(contextPath + '/cart')
           .then(function successCallback(response) {
        // в теле ответа будет json-объект корзинки
        // состоит из списка продуктов (items) - массив строк корзинки
        // и суммарной стоимости (totalPrice)
            $scope.cart = response.data;
        });
    }

    // добавить продукт в корзинку
    $scope.addToCart = function(productId){
        $http.get(contextPath + '/cart/add/' + productId)
          .then(function successCallback(response) {
            $scope.loadCart();
         });
    }

        // удалить товар из корзинки
        $scope.removeFromCart = function(productId){
            $http.get(contextPath + '/cart/remove/' + productId)
              .then(function successCallback(response) {
                $scope.loadCart();
             });
        }

        // очистить корзинку
        $scope.clearCart = function(){
            $http.get(contextPath + '/cart/clear')
              .then(function successCallback(response) {
                $scope.loadCart();
             });
        }

            // оформить заказ
            $scope.createOrder = function () {
            // в теле post запроса будет передан json-объект ($scope.orderDto)
                $http({
                    url: contextPath + '/order',
                    method: 'POST',
                    // переменная orderDto получена из html
                    // ее поля собраны из полей формы
                    data: $scope.orderDto
                }).then(function (response) {
                    // загрузить корзинку
                    $scope.loadCart();
                    // очистить поля формы
                    $scope.orderDetails = null
                });
            };

    // показать статистику
    $scope.showStatistic = function(){
        $http.get(contextPath + '/statistic')
           .then(function successCallback(response) {
           // переменная statistic = AspectResponseDto, полученная с back
            $scope.statistic = response.data;
        });
    }

     // для авторизации
     $scope.tryToAuth = function () {
     // в тело post запроса зашить $scope.user
     // $scope.user - два поля из index.html из формы аторизации (user.username, user.password)
         $http.post('http://localhost:8189/app/auth', $scope.user)
         // если вернулся ожидаемый ответ
             .then(function successCallback(response) {
                // если в ожидаемом ответе пришел токен
                 if (response.data.token) {
                    // сформировать стандартный заголовок Authorization
                     $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                     // положить информацию о пользователе в хранилище браузера
                     // чтобы после обновления страницы пользователю не надо было снова авторизироваться
                     $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                     // очистить поля в форме
                     $scope.user.username = null;
                     $scope.user.password = null;
                 }
                 // если вернулся другой ответ - кинуть красный алерт, что авторизация не удалась
             }, function errorCallback(response) {
             });
     };

     // для выхода из приложения
     $scope.tryToLogout = function () {
     // удалить пользователя из памяти браузера
         $scope.clearUser();
         if ($scope.user.username) {
            // очистить в форме поле логин
             $scope.user.username = null;
         }
         if ($scope.user.password) {
            // очистить в форме поле пароль
             $scope.user.password = null;
         }
     };

     // удалить пользователя из памяти браузера
     $scope.clearUser = function () {
         // удалить пользователя из памяти браузера
         delete $localStorage.springWebUser;
         // очистить стандартный заголовок Authorization
         $http.defaults.headers.common.Authorization = '';
     };

    // авторизован ли пользователь
     $rootScope.isUserLoggedIn = function () {
         if ($localStorage.springWebUser) {
             return true;
         } else {
             return false;
         }
     };

     // при обновлении страницы
     if ($localStorage.springWebUser) {
            // восстановить пользователя из локального хранилища в браузере
             $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
         }

    // показать информацию о текущем пользователе
     $scope.showCurrentUserInfo = function () {
         $http.get('http://localhost:8189/app/api/v1/profile')
             // если пользователь авторизован
             .then(function successCallback(response) {
                 alert('MY NAME IS: ' + response.data.username);
                 // если пользователь не авторизован
             }, function errorCallback(response) {
                 alert('UNAUTHORIZED');
             });
     }

// IV шаг
// при старте приложения вызвать функцию
    $scope.loadProducts();
    $scope.loadCart();
});