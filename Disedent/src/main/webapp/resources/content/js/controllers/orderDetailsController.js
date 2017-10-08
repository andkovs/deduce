angular.module('myApp').controller('OrderDetailsController', ['$state','$filter','$scope', '$http', '$timeout', '$interval', 'order', 'users', 'courses', 'orderService',
    function ($state, $filter, $scope, $http, $timeout, $interval, order, users, courses, orderService) {
    $scope.title = "Создание/Редактирование заказа";
    $scope.order = order.data;
    $scope.users = users.data;
    $scope.courses = courses.data;
    $scope.flag = false;

    $scope.tmp = angular.copy($scope.order);

    $scope.saveOrder = function (order, form) {
        order.userId = $scope.person.selected.id;
        order.courseId = $scope.course.selected.id;
        console.log('saveOrder', order);
        if(order.id == 0) {
            orderService.postOrder(order).then(function (response) {
                $state.go('orderDetails', {id: response.data.id});
                alert('Новый заказ создан!');
            }, function () {
            });
        }
        else{
            orderService.putOrder(order).then(function () {
                alert('Все изменения успешно сохранены!');
                form.$setPristine();
            }, function () {
            });
        }
        $scope.tmp = angular.copy($scope.order);
    };

    $scope.resetOrder = function (form) {
        $scope.order = angular.copy($scope.tmp);
        setPersonAndCourse();
        $scope.flag = false;
        form.$setPristine();
    };

    //User SEARCH
    $scope.person = {};
    $scope.course = {};

    function setPersonAndCourse(){
        if($scope.order.id == 0){
            $scope.person = {};
            $scope.course = {};
            console.log('in set person', $scope.person, $scope.course);
        }
        else {
            $scope.person.selected = $filter('getById')($scope.users, $scope.order.userId);
            $scope.course.selected = $filter('getById')($scope.courses, $scope.order.courseId);
            console.log('in set person', $scope.person, $scope.course)
        }
    }

    setPersonAndCourse();



    //Date
    var dtf = 'DD-MM-YYYY HH:mm';

    var defaultBegin = moment($scope.order.beginDate, dtf);
    $('#beginDate').datetimepicker({format: dtf, defaultDate: defaultBegin});
    $('#beginDate').on("dp.change", function () {
        $scope.order.beginDate = $("#beginDate input").val();
        $scope.flag = true;
        $scope.$apply();
    });

    var defaultEnd = moment($scope.order.endDate, dtf);
    $('#endDate').datetimepicker({format: dtf, defaultDate: defaultEnd});
    $('#endDate').on("dp.change", function () {
        $scope.order.endDate = $("#endDate input").val();
        $scope.flag = true;
        $scope.$apply();
    });

    $scope.courses.unshift({id: 0, title: 'All'});
    $scope.currentCourse = 0;

    //Sort and Search
    $scope.sortType     = 'id'; // set the default sort type
    $scope.sortReverse  = false;  // set the default sort order
    $scope.search   = '';     // set the default search/filter term


    $scope.removeOrder = function (order) {
        if (confirm('Вы уверены, что хотите удалить заказ ' + order.id + '?')) {
            orderService.deleteOrder(order).then(function (response) {
                $scope.orders = _.without($scope.orders, order);
            }, function (response) {
            });
        }
    };

}]);



