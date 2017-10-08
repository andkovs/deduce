angular.module('myApp')
    .factory('fileFactory', function ($http) {
        return {
            downloadPdf: function (id) {
                return $http({
                    url: '/distanceTestApplication/download/'+id,
                    method: "GET",
                    params: {id: id},
                    responseType: 'arraybuffer'
                }).success(function (data, status, headers, config) {
                    // var file = new Blob([data], {type: 'application/pdf'});
                    // saveAs(file);
                    return data;
                }).error(function () {
                    console.log('download failed');
                });
            }
        };
    });
