'use strict';

angular.module('myApp').controller('OrdersController', ['orders', 'courses', '$scope', 'orderService', function (orders, courses, $scope, orderService) {
    $scope.title = "Заказы";
    $scope.orders = orders.data;
    $scope.courses = courses.data;
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
