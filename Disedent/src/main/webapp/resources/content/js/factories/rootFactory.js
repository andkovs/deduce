'use strict';

angular.module('myApp').factory('rootFactory', function() {
    return {
        data: {
            user: {},
            roles: []
        },
        update: function(user, roles) {
            this.data.user = angular.copy(user);
            this.data.roles = angular.copy(roles);
        },
        getUser: function () {
            return this.data.user;
        },
        getRoles: function () {
            return this.data.roles;
        }
    };
});
