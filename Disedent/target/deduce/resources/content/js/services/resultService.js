'use strict';

angular.module('myApp').service('resultService', ['$http', function ($http) {

    // Get results by group id
    this.getResults = function (groupId) {
        return $http.get('rest/results/group/'+groupId, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Get results by group user id
    this.getUserResults = function (userId) {
        return $http.get('rest/results/user/'+userId, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Get results by order id
    this.getResultsByOrderId = function (orderId) {
        return $http.get('rest/results/order/'+orderId, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Get result
    this.getResult = function (id) {
        return $http.get('rest/results/'+id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Put result
    this.putResult = function (id, test) {
        return $http.put('rest/results/' + id, test, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Delete result
    this.deleteResult = function (result) {
        return $http.delete('rest/results/' + result.id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // GET results excel
    this.getResultsExcel = function (id) {
        $http({
            url: 'rest/files/excel/results/'+id,
            method: "GET",
            responseType: 'arraybuffer',
            headers: {'token': sessionStorage.getItem('token')}
        }).success(function (data, status, headers, config) {
            var blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
            saveAs(blob, "Results group#"+id);
        }).error(function (data, status, headers, config) {
            //upload failed
        });
    };

    // GET result excel
    this.getResultExcel = function (id) {
        $http({
            url: 'rest/files/excel/result/'+id,
            method: "GET",
            responseType: 'arraybuffer',
            headers: {'token': sessionStorage.getItem('token')}
        }).success(function (data, status, headers, config) {
            var blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
            saveAs(blob, "Results order#"+id);
        }).error(function (data, status, headers, config) {
            //upload failed
        });
    };

    // GET results excel
    this.getResultByIdExcel = function (id) {
        $http({
            url: 'rest/files/excel/result/'+id,
            method: "GET",
            responseType: 'arraybuffer'
        }).success(function (data, status, headers, config) {
            var blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
            saveAs(blob, "Result #"+id);
        }).error(function (data, status, headers, config) {
            //upload failed
        });
    };

}]);