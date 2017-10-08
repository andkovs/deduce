angular.module('myApp').controller('TopicDetailsController', ['$scope', 'topic', 'topicService', 'fileUploadService',
    function ($scope, topic, topicService, fileUploadService) {
    $scope.title = "Редактирование раздела";
    $scope.topic = topic.data;
    $scope.tmp = angular.copy($scope.topic);

    $scope.saveTopic = function (topic, form) {
        topicService.putTopic(topic).then(function () {
            $scope.tmp = angular.copy(topic);
            form.$setPristine();
        }, function () {
        });
    };

    $scope.resetTopic = function (form) {
        $scope.topic = angular.copy($scope.tmp);
        form.$setPristine();
    };

    // Upload file

    $scope.uploadFile = function(){
        console.log('in uploadFile 1');
        var file = $scope.myFile;
        console.log('in uploadFile 2 file', file);
        var name = document.getElementById('fileUpl').files[0].name;
        console.log('in uploadFile 3', file, $scope.topic.id, name);
        fileUploadService.uploadFileToUrl(file, $scope.topic.id, name, 'on').then(function (response) {
            var newFile = response.data;
            $scope.topic.file = true;
        }, function (response) {
            console.log('add error', response);
        });;
    };

    $scope.removeFile = function (id) {
        if (confirm('Вы уверены, что хотите удалить файл?')) {
            fileUploadService.deleteFile(id).then(function () {
                $scope.topic.file = false;
            }, function () {
                console.log('delete error');
            });
        }
    }

    $scope.getFile = function (file){
        fileUploadService.getPDFFile(file.id, $scope.order.id, file.name).then(function () {
        }, function () {
        });
    }

}]);

