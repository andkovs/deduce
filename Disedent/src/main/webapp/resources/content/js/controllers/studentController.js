'use strict';

angular.module('myApp').controller('StudentController', ['student', '$scope', '$state', 'studentService',
    function (student, $scope, $state, studentService) {
        $scope.student = student.data;
        $scope.course = student.data.course;
        $scope.tests = student.data.course.tests;
        $scope.orderId = student.data.order.id;

        $scope.$state = $state;

        //for disable then test go on
        $scope.flag = 'info';


    }]);
