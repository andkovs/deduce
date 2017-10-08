'use strict';

angular.module('myApp').controller('TestsController', ['tests', '$scope', 'testService', function (tests, $scope, testService) {
    $scope.title = "Тесты";
    $scope.tests = tests.data;

    $scope.search   = '';

    $scope.removeTest = function (test) {
        if (confirm('Вы уверены, что хотите удалить курс ' + test.title + '?')) {
            testService.deleteTest(test).then(function (response) {
                $scope.tests = _.without($scope.tests, test);
            }, function (response) {
            });
        }
    };

    $scope.preview = function(id) {
        var getCourse = testService.getTest(id);
        getCourse.then(function(response){
            $scope.previewData = response.data;
            $('#testPreview').modal();
        });
    };

    $('#testPreview').on('hidden.bs.modal', function () {
        $scope.previewData = {};
    })

}]);