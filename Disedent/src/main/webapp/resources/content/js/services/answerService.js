'use strict';

angular.module('myApp').service('answerService', ['$http', function ($http) {

    // Get answer
    this.getAnswer = function (id) {
        return $http.get('rest/answer/'+id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Put answer
    this.putAnswer = function (answer) {
        return $http.put('rest/answer/' + answer.id, answer, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Post answer
    this.postAnswer = function (answer) {
        return $http.post('rest/answer', answer, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Delete chapter
    this.deleteAnswer = function (answer) {
        return $http.delete('rest/answer/' + answer.id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };
}]);

