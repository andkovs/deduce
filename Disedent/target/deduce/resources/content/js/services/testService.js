'use strict';

angular.module('myApp').service('testService', ['$http', function ($http) {

	// Get tests
	this.get = function () {
		return $http.get('rest/tests', {
			headers: {'token': sessionStorage.getItem('token')}
		});
	};

	// Get test
	this.getTest = function (id) {
		return $http.get('rest/tests/'+id, {
			headers: {'token': sessionStorage.getItem('token')}
		});
	};

	// Get test info
	this.getTestInfo = function (orderId, id) {
		return $http.get('rest/tests/info/'+id, {
			params: {orderId: orderId},
			headers: {'token': sessionStorage.getItem('token')}
		});
	};

	// Get test view
	this.getTestView = function (orderId, id) {
		return $http.get('rest/tests/view/'+id, {
				params: {orderId: orderId},
			headers: {'token': sessionStorage.getItem('token')}
			});
	};

	// Put test
	this.putTest = function (test) {
		console.log("in service", test);
		return $http.put('rest/tests/' + test.id, test, {
			headers: {'token': sessionStorage.getItem('token')}
		});
	};

	// Post test
	this.postTest = function (test) {
		return $http.post('rest/tests', test, {
			headers: {'token': sessionStorage.getItem('token')}
		});
	};

	// Delete test
	this.deleteTest = function (test) {
		return $http.delete('rest/tests/' + test.id, {
			headers: {'token': sessionStorage.getItem('token')}
		});
	};

}]);