'use strict';

angular.module('myApp').service('topicService', ['$http', function ($http) {

    // Get topic
    this.getTopic = function (id) {
        return $http.get('rest/topic/'+id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Put topic
    this.putTopic = function (topic) {
        return $http.put('rest/topic/' + topic.id, topic, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Post topic
    this.postTopic = function (topic) {
        return $http.post('rest/topic', topic, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // Delete chapter
    this.deleteTopic = function (topic) {
        return $http.delete('rest/topic/' + topic.id, {
            headers: {'token': sessionStorage.getItem('token')}
        });
    };

    // // Get previewTopic
    // this.getPreview = function (id) {
    //     return $http.get('rest/previewTopic/'+id);
    // };


}]);

