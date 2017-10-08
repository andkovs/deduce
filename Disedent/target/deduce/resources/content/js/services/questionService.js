'use strict';

angular.module('myApp').service('questionService', ['$http', function ($http) {

    // Get question
    this.getQuestion = function (id) {
        return $http.get('rest/questions/'+id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Put question
    this.putQuestion = function (question) {
        return $http.put('rest/questions/' + question.id, question, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Post question
    this.postQuestion = function (question) {
        return $http.post('rest/questions', question, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Delete question
    this.deleteQuestion = function (question) {
        return $http.delete('rest/questions/' + question.id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };


}]);

