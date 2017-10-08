angular.module('myApp').controller('TestViewController', ['$scope', 'test', function ($scope, test) {
    $scope.test = test.data;
    $scope.questions = test.data.questions;
    $scope.totalItems = $scope.questions.length;
    $scope.currentQuestion = 1;
    $scope.maxSize = 10;

    $scope.setCorrectAnswer = function (answer) {
        $scope.answers.forEach(function(a) {
            a.isCorrect = answer === a;
        });
    };

    $scope.finishTest = function () {

    };

    var testTimesLabel = $('#testTime');

    function startTimer() {
        var time = $scope.test.timeLimit * 60;
        testTimesLabel.text(timeText(time));
        var timeInterval = setInterval(function(){
            time--;
            if (time >= 0) {
                testTimesLabel.text(timeText(time));
            }
            else {
                clearInterval(timeInterval)
                $scope.finishTest();
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

    startTimer();

}]);
