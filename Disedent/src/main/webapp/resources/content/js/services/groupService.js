'use strict';

angular.module('myApp').service('groupService', ['$http', function ($http) {

    // Get groups
    this.get = function () {
        return $http.get('rest/groups', {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Get group
    this.getGroup = function (id) {
        return $http.get('rest/groups/'+id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Post group
    this.postGroup = function (group) {
        return $http.post('rest/groups', group, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Delete group
    this.deleteGroup = function (group) {
        return $http.delete('rest/groups/' + group.id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

}]);


