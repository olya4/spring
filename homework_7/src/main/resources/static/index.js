// I шаг
// 'app' - название приложения
// [] - создать новое приложение без зависимостей
// 'indexController' - контроллер
// $scope - связывает js-документ с html-документом
// $http - связывает js-документ с back-end
angular.module('app', []).controller('indexController', function ($scope, $http) {

// II шаг
// корневой адрес приложения
    const contextPath = 'http://localhost:8189/app/api/v1';

// работает ли js-файл
    console.log(123);

// III шаг
// создать функцию
    $scope.loadStudents = function () {
//    отправить запрос на back-end в класс StudentController на end-point '/students'
        $http.get(contextPath + '/students')

//        когда будет получен ответ
            .then(function (response) {

//              какие данные пришли от back-end
                console.log(response.data);

// в $scope положен StudentsList, значит к нему можно обратиться из html-документа

// значение для переменной StudentsList получено из ответа на запрос '/students'
// запрос на '/students' был обработан в StudentController

// присвоить значение ответа переменной StudentsList
                $scope.StudentsList = response.data;
            });
    };

//    функция для удаления студента по id
    $scope.deleteStudent = function (studentId){
            $http.delete(contextPath + '/students/' + studentId)
//            когда ответ от back-end получен
                .then(function (response) {
// после успешного ответа от back-end перезагрузить список студентов
                $scope.loadStudents();
                });
    }

// создание объекта
        $scope.createStudent = function () {
        // что отдаст форма - какой объект сформировался
            console.log($scope.newStudent);
            // отправить post-запрос back-end
            // в теле post-запрос находится json-объект, полученный из формы ($scope.newStudent)
            $http.post(contextPath + '/students', $scope.newStudent)
                .then(function (response) {
// после успешного ответа от back-end перезагрузить список студентов
                    $scope.loadStudents();
                });
        }

    $scope.updateStudent = function () {
            // что отдаст форма - какой объект сформировался
                console.log($scope.updateStudent);
                // отправить put-запрос back-end
                // в теле put-запрос находится json-объект, полученный из формы ($scope.updateStudent)
                $http.put(contextPath + '/students', $scope.updateStudent)
                    .then(function (response) {
    // после успешного ответа от back-end перезагрузить список студентов
                        $scope.loadStudents();
              });
    }



// IV шаг
// при старте приложения вызвать функцию
    $scope.loadStudents();
});