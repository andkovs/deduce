'use strict';

angular.module('myApp').service('fileUploadService', ['$http', function ($http) {

    this.uploadFileToUrl = function(file, id, name, type){
        var fd = new FormData();
        fd.append('file', file);
        fd.append('id', id);
        fd.append('name', name);
        fd.append('type', type);
        fd.append('token', sessionStorage.getItem('token'));
        return $http.post('rest/files/pdf', fd,{
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
    }

    this.deleteFile = function(id){
        return $http.delete('rest/files/pdf/'+id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    }

}]);
