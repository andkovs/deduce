angular.module('myApp').controller('UserDetailsController', ['$scope', 'user', 'roles', 'userService', '$state',
    function ($scope, user, roles, userService, $state) {
        $scope.title = "Создание/Редактирование пользователя";
        $scope.user = user.data;
        $scope.roles = roles.data;



        console.log($scope.user, $scope.roles);

        $scope.tmp = angular.copy($scope.user);

        $scope.saveUser = function (user, form) {
            if (user.id == 0) {
                user.enabled = true;
                userService.postUser(user).then(function (response) {
                    $state.go('userDetails', {id: response.data.id});
                }, function () {
                });
            }
            else {
                userService.putUser(user).then(function () {
                    form.$setPristine();
                }, function () {
                });
            }
            $scope.tmp = angular.copy($scope.user);
        };

        $scope.resetUser = function (form) {
            $scope.user = angular.copy($scope.tmp);
            form.$setPristine();
        };

        // //checkbox  required
        // var selectedRole = {};
        //
        // var calculateSomeSelected = function() {
        //     $scope.someSelected = Object.keys(selectedRole).some(function (key) {
        //         return selectedRole[key];
        //     });
        // };
        //
        // $scope.formData = {
        //     selectedRole: selectedRole
        // };
        //
        // $scope.checkboxChanged = calculateSomeSelected;
        //
        // calculateSomeSelected();

    }]);


