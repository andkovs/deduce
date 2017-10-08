'use strict';

angular.module('myApp').controller('CoursesController', ['courses', '$scope', 'courseService', function (courses, $scope, courseService) {
    $scope.title = "Курсы";
    $scope.courses = courses.data;

    $scope.search   = '';

    $scope.removeCourse = function (course) {
        if (confirm('Вы уверены, что хотите удалить курс ' + course.title + '?')) {
            courseService.deleteCourse(course).then(function (response) {
                $scope.courses = _.without($scope.courses, course);
            }, function (response) {
            });
        }
    };

    $scope.preview = function(id) {
        var getCourse = courseService.getCourse(id);
        getCourse.then(function(response){
            $scope.previewData = response.data;
            $('#coursePreview').modal();
        });
    };

    $('#coursePreview').on('hidden.bs.modal', function () {
        $scope.previewData = {};
    })

}]);