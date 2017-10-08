'use strict';

angular.module('myApp').controller('HomeController', ['$scope', '$state', 'rootFactory', 'sessionService',
	function ($scope, $state, rootFactory, sessionService) {

		$scope.login = {};
		$scope.invalidLoginOrPass = false;
		$scope.invalidText = "";

		$scope.getStudent = function(){
			$scope.user = rootFactory.getUser();
			$state.go('userOrders', {id: $scope.user.id});
		}

		$scope.getStudentResults = function(){
			$scope.user = rootFactory.getUser();
			$state.go('userResults', {id: $scope.user.id});
		}

		$scope.authorization = function () {
			var login = angular.copy($scope.login);
			$scope.login.login = "";
			$scope.login.password = "";
			sessionService.postLogin(login).then(function (response) {
				if(response.status === 402){
					$scope.invalidLoginOrPass = true;
					$scope.invalidText = response.data.message;
				}else{
					$scope.login.login = "";
					$scope.login.password = "";
					rootFactory.update(response.data.user, response.data.roles);
					sessionStorage.setItem('token', response.data.token);
				}
			}).catch(function (response) {
			});
		}
	}]);