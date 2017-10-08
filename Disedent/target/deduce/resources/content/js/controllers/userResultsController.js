'use strict';

angular.module('myApp').controller('UserResultsController', ['userResults', '$scope',
    function (userResults, $scope) {
    $scope.userResults = userResults.data;

}]);