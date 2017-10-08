angular.module('myApp').controller('ChapterDetailsController', ['$http','fileFactory', '$sce','$scope', 'chapter', 'chapterService', 'topicService', 'fileUploadService',
    function ($http, fileFactory, $sce, $scope, chapter, chapterService, topicService, fileUploadService) {
    $scope.title = "Редактирование главы";
    $scope.chapter = chapter.data;
    $scope.topics = chapter.data.topics;
    $scope.newTopic = {};

    $scope.tmp = angular.copy($scope.chapter);

    $scope.saveChapter = function (chapter, form) {
        chapterService.putChapter(chapter).then(function () {
            $scope.tmp = angular.copy($scope.chapter);
            form.$setPristine();
        }, function () {
        });
    };

    $scope.resetChapter = function (form) {
        $scope.chapter = angular.copy($scope.tmp);
        form.$setPristine();
    };

    $scope.removeTopic = function (topic) {
        if (confirm('Вы уверены, что хотите удалить раздел ' + topic.title + '?')) {
            topicService.deleteTopic(topic).then(function (response) {
                $scope.topics = _.without($scope.topics, topic);
            }, function (response) {
            });
        }
    };

    $scope.addTopic = function (topic) {
        topic.chapterId = $scope.chapter.id;
        topicService.postTopic(topic).then(function (response) {
            var newTopic = response.data;
            $scope.topics.push(newTopic);
            $scope.newTopic = {};
        }, function (response) {
            $scope.newTopic = {};
        });
    };

    //preview modal

        // $scope.getNavStyle = function(scroll) {
        //     if(scroll > 100) return 'pdf-controls fixed';
        //     else return 'pdf-controls';
        // }
        //
        // $scope.onError = function(error) {
        //     console.log(error);
        // }
        //
        // $scope.onLoad = function() {
        //     $scope.loading = '';
        // }
        //
        // $scope.onProgress = function (progressData) {
        //     console.log(progressData);
        // };


    $scope.preview = function(id) {
        var getTopic = topicService.getTopic(id);
        getTopic.then(function(response){
            $scope.previewData = response.data;
        });
        $http({
            url: 'rest/files/pdf/'+id,
            method: "GET",
            params: {id: id},
            responseType: 'arraybuffer',
            headers: {'token': sessionStorage.getItem('token')}
        }).success(function (data) {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = URL.createObjectURL(file);
            // $scope.pdfUrl = URL.createObjectURL(file);
            // $scope.pdfName = $scope.previewData.title;
            // $scope.scroll = 0;
            // $scope.loading = 'loading';
            // $scope.getNavStyle = function(scroll) {
            //     if(scroll > 100) return 'pdf-controls fixed';
            //     else return 'pdf-controls';
            // };
            // $scope.onError = function(error) {
            //     console.log(error);
            // };
            // $scope.onLoad = function() {
            //     $scope.loading = '';
            // };
            // $scope.onProgress = function (progressData) {
            //     //console.log(progressData);
            // };
            /*
             //if pdf has password
             $scope.onPassword = function (updatePasswordFn, passwordResponse) {
             if (passwordResponse === PDFJS.PasswordResponses.NEED_PASSWORD) {
             updatePasswordFn($scope.pdfPassword);
             } else if (passwordResponse === PDFJS.PasswordResponses.INCORRECT_PASSWORD) {
             console.log('Incorrect password')
             }
             };
             */
            $scope.content = $sce.trustAsResourceUrl(fileURL);
        }).error(function () {
            console.log('download failed');
        });
        $('#topicPreview').modal();
    };

        $('#topicPreview').on('hidden.bs.modal', function () {
            $scope.previewData = {};
            $scope.content = {};
        })


}]);