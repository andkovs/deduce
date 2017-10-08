'use strict';

angular.module('myApp').service('orderService', ['$http', function ($http) {

    // Get orders
    this.get = function () {
        return $http.get('rest/orders', {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Get orders by userId
    this.getUserOrders = function (id){
        return $http.get('rest/orders/user/'+id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    }

    // Get order
    this.getOrder = function (id) {
        return $http.get('rest/orders/'+id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Put order
    this.putOrder = function (order) {
        return $http.put('rest/orders/' + order.id, order, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Post order
    this.postOrder = function (order) {
        return $http.post('rest/orders', order, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Delete order
    this.deleteOrder = function (order) {
        return $http.delete('rest/orders/' + order.id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

}]);

