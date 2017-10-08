angular.module('myApp').controller('TestDetailsController', ['$scope', 'test', 'testService', 'questionService', '$state', function ($scope, test, testService, questionService, $state) {
    $scope.title = "Создание/Редактирование теста";
    $scope.test = test.data;
    $scope.questions = test.data.questions;
    $scope.newQuestion = {};

    $scope.search   = '';

    $scope.tmp = angular.copy($scope.test);

    $scope.saveTest = function (test, form) {
        if(test.id==0){
            testService.postTest(test).then(function (response) {
                $state.go('testDetails', {id: response.data.id});
            }, function (response) {});
        } else{
            testService.putTest(test).then(function () {
                form.$setPristine();
                $scope.tmp = angular.copy(test);
            }, function () {});
        }
    };

    $scope.resetTest = function (form) {
        $scope.test = angular.copy($scope.tmp);
        form.$setPristine();
    };

    $scope.addQuestion = function (question) {
        question.testId = $scope.test.id;
        questionService.postQuestion(question).then(function (response) {
            var newQuestion = response.data;
            $scope.questions.push(newQuestion);
            $scope.newQuestion = {};
        }, function (response) {
            $scope.newQuestion = {};
        });
    };

    $scope.removeQuestion = function (question) {
        if (confirm('Вы уверены, что хотите удалить вопрос ' + question.title + '?')) {
            questionService.deleteQuestion(question).then(function (response) {
                $scope.questions = _.without($scope.questions, question);
            }, function (response) {
            });
        }
    };

    $scope.preview = function(id) {
        var getCourse = questionService.getQuestion(id);
        getCourse.then(function(response){
            $scope.previewData = response.data;
            $('#questionPreview').modal();
        });
    };

    $('#questionPreview').on('hidden.bs.modal', function () {
        $scope.previewData = {};
    })

}]);

