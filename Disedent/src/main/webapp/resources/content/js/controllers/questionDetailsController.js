angular.module('myApp').controller('QuestionDetailsController', ['$scope', 'question', 'questionService', '$state',
    function ($scope, question, questionService, $state) {
    $scope.title = "Редактирование вопроса";
    $scope.question = question.data;
    //$scope.answers = question.data.answers;
    $scope.newAnswer = {};
    $scope.flag = false;

    $scope.tmp = angular.copy($scope.question);

    $scope.saveQuestion = function (question, form) {
        questionService.putQuestion(question).then(function () {
            questionService.getQuestion(question.id).then(function (response) {
                $scope.question = response.data;
                //$scope.answers = response.data.answers;
                $scope.newAnswer = {};
                $scope.flag = false;
                $scope.tmp = angular.copy(question);
                form.$setPristine();
            }, function () {
            });
            //console.log('in putQuestion', question.id);
            //$state.go('questionDetails', {id: question.id});

            // $scope.flag = false;
        }, function () {
        });
    };

     $scope.resetQuestion = function (form) {
         $scope.question = angular.copy($scope.tmp);
         //$scope.answers = angular.copy($scope.question.answers);
         $scope.flag = false;
         form.$setPristine();
    };

    $scope.removeAnswer = function (answer) {
        console.log('answer', answer);
        $scope.question.answers = _.without($scope.question.answers, answer);
        console.log('$scope.question', $scope.question);
        $scope.flag = true;
    };

    $scope.addAnswer = function (newAnswer) {
        newAnswer.isCorrect = false;
        console.log('isCorrect', newAnswer.isCorrect);
        $scope.question.answers.push(newAnswer);
        $scope.newAnswer = {};
        $scope.flag = true;
    };

    $scope.setCorrectAnswer = function (answer) {
        $scope.question.answers.forEach(function(a) {
            a.isCorrect = answer === a;
            $scope.flag = true;
        });
    };


}]);