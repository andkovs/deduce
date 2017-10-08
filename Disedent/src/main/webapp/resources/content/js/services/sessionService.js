angular.module('myApp').service('sessionService', ['$http', function ($http) {

    // Get session
    this.getSession = function () {
        return $http.get('rest/session', {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // // Get logout
    // this.getLogout = function () {
    //     return $http.get('rest/session/logout');
    // };

    // Post login
    this.postLogin = function (login) {
        console.log(login);
        return $http.post('rest/session/login', login);
    };

}]);