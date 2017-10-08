'use strict';

angular.module('myApp').controller('UserTestResultController', ['result', '$scope',
    function (result, $scope) {
        $scope.title = "Результат тестирования";
        $scope.result = result.data;

        $scope.time = moment.utc(moment($scope.result.finishTime,"DD-MM-YYYY HH:mm").diff(moment($scope.result.startTime,"DD-MM-YYYY HH:mm"))).format("HH:mm")

    }]);
