angular.module('myApp').controller('TestInfoController', ['$scope', 'test', 'testService', '$stateParams', 'resultService', '$state',
    function ($scope, test, testService, $stateParams, resultService, $state) {
    $scope.test = test.data;
    $scope.$parent.flag = true;

    $scope.questions = [];
    $scope.totalItems = 0;
    $scope.currentQuestion = 1;
    $scope.maxSize = 10;

    $scope.setCorrectAnswer = function (i, answer) {
        $scope.questions[i].answers.forEach(function(a) {
            a.isCorrect = answer === a;
        });
    };

    $scope.startTest = function(){
        if (confirm('Во время тестирования не перезагружайте страницу.\n' +
                'Перегрузка страницы приведет к потере попытки.\n\n' +
                'Начать тестирование ' + $scope.test.title + '?')) {
            testService.getTestView($scope.orderId, $scope.test.id).then(function (response) {
                $scope.questions = angular.copy(response.data.questions);
                $scope.resultId = response.data.resultId;
                $scope.totalItems = $scope.questions.length;
                $scope.$parent.flag = false;
                startTimer();
            }, function (response) {
            });
        }
    }

    $scope.finishTest = function () {
        if (confirm('Завершить тестирование?')) {
            var test = {
                id: $scope.test.id,
                resultId: $scope.resultId,
                orderId: $scope.orderId,
                title: $scope.test.title,
                questionsAmount: $scope.test.questionsAmount,
                timeLimit: $scope.test.timeLimit,
                questions: $scope.questions
            };
            resultService.putResult($scope.resultId, test).then(function () {
                clearInterval(timeInterval);
                $scope.$parent.flag = true;
                $state.go('student.userTestResult', {id: $scope.resultId});
            }, function (response) {
            });
        }



    };

    var testTimesLabel = $('#testTime');
        var timeInterval = null;

    function startTimer() {
        var time = $scope.test.timeLimit * 60;
        testTimesLabel.text(timeText(time));
        timeInterval = setInterval(function(){
            time--;
            if (time >= 0) {
                testTimesLabel.text(timeText(time));
            }
            else {
                clearInterval(timeInterval);
                var test = {
                    id: $scope.test.id,
                    resultId: $scope.resultId,
                    orderId: $scope.orderId,
                    title: $scope.test.title,
                    questionsAmount: $scope.test.questionsAmount,
                    timeLimit: $scope.test.timeLimit,
                    questions: $scope.questions
                };
                resultService.putResult($scope.resultId, test).then(function () {
                    clearInterval(timeInterval);
                    $scope.$parent.flag = true;
                    $state.go('student.userTestResult', {id: $scope.resultId});
                }, function (response) {
                });
            }
        }, 1000);
    };

    function timeText(time) {
        var seconds = time % 60;
        var minutes = Math.floor(time/60);
        var result = "";
        if (seconds >= 10)
            result = minutes + ":" + seconds;
        else
            result = minutes + ":0" + seconds;
        return result;
    };

}]);


