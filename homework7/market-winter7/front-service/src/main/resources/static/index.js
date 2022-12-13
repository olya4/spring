// I шаг
// 'app' - название приложения
// [] - зависимости
// 'indexController' - контроллер
// $scope - связывает js-документ с html-документом
// $rootScope - глобальный контекст, к которому можно обращаться из любого контроллера
// $http - связывает js-документ с back-end
// $localStorage - ссылка на локально хранилище данных (браузера)
angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http, $localStorage) {

   $scope.loadProducts = function () {
          $http.get('http://localhost:5555/winter-market-app/api/v1/products')
             .then(function successCallback(response) {
          // в теле ответа будет json-объект корзинки
          // состоит из списка продуктов (items) - массив строк корзинки
          // и суммарной стоимости (totalPrice)
              $scope.ProductsList = response.data;
           });
          };

   $scope.loadCart = function(){
        $http.get('http://localhost:5555/winter-market-carts/api/v1/cart')
           .then(function successCallback(response) {
        // в теле ответа будет json-объект корзинки
        // состоит из списка продуктов (items) - массив строк корзинки
        // и суммарной стоимости (totalPrice)
            $scope.cart = response.data;
        });
   }

    // добавить продукт в корзинку
   $scope.addToCart = function(productId){
        $http.get('http://localhost:5555/winter-market-carts/api/v1/cart/add/' + productId)
          .then(function successCallback(response) {
            $scope.loadCart();
         });
   }

    // удалить продукт из корзинки
   $scope.removeFromCart = function(productId){
        $http.get('http://localhost:5555/winter-market-carts/api/v1/cart/remove/' + productId)
          .then(function successCallback(response) {
            $scope.loadCart();
         });
    }

   // очистить корзинку
   $scope.clearCart = function(){
            $http.get('http://localhost:5555/winter-market-carts/api/v1/cart/clear')
              .then(function successCallback(response) {
                $scope.loadCart();
             });
   }

   // оформить заказ
   $scope.createOrder = function(){
            $http.post('http://localhost:5555/winter-market-app/api/v1/orders')
              .then(function successCallback(response) {
                alert('Заказ оформлен');
                $scope.loadCart();
             });
   }


     // для авторизации
     $scope.tryToAuth = function () {
     // в тело post запроса на получение токена зашить $scope.user
     // $scope.user - два поля из index.html из формы авторизации (user.username, user.password)
         $http.post('http://localhost:5555/winter-market-auth/auth', $scope.user)
             // если вернулся ожидаемый ответ
             .then(function successCallback(response) {
                // если в ожидаемом ответе пришел токен
                 if (response.data.token) {
                    // сформировать стандартный заголовок Authorization для всех запросов клиента
                    // и подшить в этот заголовок токен доступа
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
     // если пользователь есть в локальном хранилище браузера
         if ($localStorage.springWebUser) {
             return true;
         } else {
             return false;
         }
     };

   // при запуске приложения
   // проверить есть ли пользователь в локальном хранилище браузера
   if($localStorage.springWebUser){
      // проверка времени жизни токена
      try{
                // достать токен из локального хранилища
                let jwt = $localStorage.springWebUser.token;
                // разделить токен на составные части по точке: [0] - header, [1] - payload,  [2] - подпись
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                // получить текущуее время жизни токена
                let currentTime = parseInt(new Date().getTime() / 1000);
                console.log("Общее время жизни токена: " + payload.exp);
                console.log("Текущее время жизни токена: " + currentTime);
                console.log("Токену осталось жить: " + (payload.exp - currentTime));
                // если текущуее время жизни токена превышает общее время жизни токена
                if (currentTime > payload.exp) {
                    // вывести в консоль браузера
                    console.log("Token is expired!!!");
                    // удалить пользователя из локального хранилища браузера
                    delete $localStorage.springWebUser;
                    // очистить стандартный заголовок Authorization
                    $http.defaults.headers.common.Authorization = '';
          }
      }catch(e){
      }
      // если пользователь есть в локальном хранилище браузера
      // восстановить пользователя из локального хранилища в браузере
      $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
   }

// IV шаг
// при старте приложения вызвать функцию
    $scope.loadProducts();
    $scope.loadCart();
});