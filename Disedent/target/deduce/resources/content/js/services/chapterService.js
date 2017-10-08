'use strict';

angular.module('myApp').service('chapterService', ['$http', function ($http) {

    // Get chapter
    this.getChapter = function (id) {
        return $http.get('rest/chapter/'+id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Put chapter
    this.putChapter = function (chapter) {
        return $http.put('rest/chapter/' + chapter.id, chapter, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Post chapter
    this.postChapter = function (chapter) {
        return $http.post('rest/chapter', chapter, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Delete chapter
    this.deleteChapter = function (chapter) {
        return $http.delete('rest/chapter/' + chapter.id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };


}]);

