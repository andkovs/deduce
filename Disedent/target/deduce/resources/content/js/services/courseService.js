'use strict';

angular.module('myApp').service('courseService', ['$http', function ($http) {

    // Get courses
    this.get = function () {
        return $http.get('rest/courses', {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Get course
    this.getCourse = function (id) {
        return $http.get('rest/courses/'+id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Put course
    this.putCourse = function (course) {
        return $http.put('rest/courses/' + course.id, course, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Post course
    this.postCourse = function (course) {
        return $http.post('rest/courses', course, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Delete course
    this.deleteCourse = function (course) {
        return $http.delete('rest/courses/' + course.id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

}]);
