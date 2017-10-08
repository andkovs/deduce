'use strict';

angular.module('myApp').controller('UserOrdersController', ['orders', '$scope', 'orderService', function (orders, $scope, orderService) {
    $scope.orders = orders.data;

}]);

