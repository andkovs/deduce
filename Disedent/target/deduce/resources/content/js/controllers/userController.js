'use strict';

angular.module('myApp').controller('UsersController', ['users', '$scope', 'userService', function (users, $scope, userService) {
    $scope.title = "Пользователи";
    $scope.users = users.data;

    $scope.search   = '';

    $scope.removeUser = function (user) {
        if (confirm('Вы уверены, что хотите удалить пользователя ' + user.login + '?')) {
            userService.deleteUser(user).then(function (response) {
                $scope.users = _.without($scope.users, user);
            }, function (response) {
            });
        }
    };

    $scope.enableUser = function(user){
        userService.enableUser(user.id).then(function (response) {
            user.enabled = true;
        }, function (response) {
        });
        // form.$setPristine();
    };

    $scope.disableUser = function(user){
        userService.disableUser(user.id).then(function (response) {
            user.enabled = false;
        }, function (response) {
        });
        // form.$setPristine();
    };


    // $scope.preview = function(id) {
    //     var getUser = userService.getUser(id);
    //     getUser.then(function(response){
    //         $scope.previewData = response.data;
    //         $('#userPreview').modal();
    //     });
    // };
    //
    // $('#userPreview').on('hidden.bs.modal', function () {
    //     $scope.previewData = {};
    // })

}]);