angular.module('myApp').controller('TopicViewController', ['$scope', 'topic', '$http', '$sce',
    function ($scope, topic, $http, $sce) {
        $scope.topic = topic.data;


       function fileView(id) {
           $http({
               url: 'rest/files/pdf/'+id,
               method: "GET",
               params: {id: id},
               responseType: 'arraybuffer',
               headers: {'token': sessionStorage.getItem('token')}
           }).success(function (data, status, headers, config) {
               var file = new Blob([data], {type: 'application/pdf'});
               var fileURL = URL.createObjectURL(file);
               //$scope.pdfUrl = URL.createObjectURL(file);
               //$scope.pdfName = $scope.topic.title;
               //$scope.scroll = 0;
               //$scope.loading = 'loading';
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
               //$scope.loading = 'loading';
               $scope.content = $sce.trustAsResourceUrl(fileURL);
           }).error(function () {
           });
       }
        fileView($scope.topic.id);
    }]);