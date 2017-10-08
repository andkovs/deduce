'use strict';

angular.module('myApp').service('studentService', ['$http', function ($http) {

    // Get tests
    this.get = function (id) {
        return $http.get('rest/student/'+id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };


}]);
