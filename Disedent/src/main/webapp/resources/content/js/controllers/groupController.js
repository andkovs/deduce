'use strict';

angular.module('myApp').controller('GroupsController', ['groups', '$scope', 'groupService', 'resultService',
    function (groups, $scope, groupService, resultService) {
        $scope.title = "Группы";
        $scope.groups = groups.data;
        $scope.newGroup = {};

        //Sort and Search
        $scope.sortType = 'id'; // set the default sort type
        $scope.sortReverse = true;  // set the default sort group
        $scope.search = '';     // set the default search/filter term
        $scope.previewSearch = '';


        $scope.addGroup = function (group) {
            groupService.postGroup(group).then(function (response) {
                var newGroup = response.data;
                $scope.groups.push(newGroup);
                $scope.newGroup = {};
            }, function (response) {
                $scope.newGroup = {};
            });
        };

        $scope.removeGroup = function (group) {
            if (confirm('Вы уверены, что хотите удалить группу ' + group.id + '?')) {
                groupService.deleteGroup(group).then(function () {
                    $scope.groups = _.without($scope.groups, group);
                }, function (response) {
                });
            }
        };

        $scope.getResults = function(group) {
            var getResults = resultService.getResults(group.id);
            getResults.then(function(response){
                $scope.previewData = {};
                $scope.results = response.data;
                $scope.previewData.title = group.title;
                $scope.previewData.id = group.id;
                console.log($scope.previewData.id);
                $scope.previewData.creationDate = group.creationDate;
                $('#groupResults').modal();
            });
        };

        $scope.removeResult = function (result) {
            if (confirm('Вы уверены, что хотите удалить группу результат?')) {
                resultService.deleteResult(result).then(function () {
                    $scope.results = _.without($scope.results, result);
                }, function (response) {
                });
            }
        };

        $scope.getResultsExcel = function(id){
            resultService.getResultsExcel(id);
        };

        $('#groupResults').on('hidden.bs.modal', function () {
            $scope.previewData = {};
        })

    }]);
