'use strict';

angular.module('myApp').service('courseToTestService', ['$http', function ($http) {

    // Post courseToTest
    this.postCourseToTest = function (courseToTest) {
        return $http.post('rest/courseToTest', courseToTest, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Delete courseToTest
    this.deleteCourseToTest = function (courseToTest) {
        return $http.put('rest/courseToTest/', courseToTest, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

}]);
