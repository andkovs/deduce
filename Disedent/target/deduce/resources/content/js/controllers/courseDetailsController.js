angular.module('myApp').controller('CourseDetailsController', ['$scope', 'course', 'tests', 'courseService', 'chapterService', 'courseToTestService', '$state',
    function ($scope, course, tests, courseService, chapterService, courseToTestService, $state) {
    $scope.title = "Создание/Редактирование курса";
    $scope.course = course.data;
    $scope.chapters = course.data.chapters;
    $scope.courseTests = course.data.tests;
    $scope.tests = tests.data;
    $scope.newChapter = {};

    minusTestsList();

    //for search
    $scope.test = {};

    $scope.tmp = angular.copy($scope.course);

    $scope.saveCourse = function (course, form) {
        if(course.id==0){
            courseService.postCourse(course).then(function (response) {
                $state.go('courseDetails', {id: response.data.id});
            }, function (response) {
            });
        } else{
            courseService.putCourse(course).then(function () {
                form.$setPristine();
                $scope.tmp = angular.copy($scope.course);
                alert('Все изменения успешно сохранены!');
            }, function () {
            });
        }
    };

    $scope.resetCourse = function (form) {
        $scope.course = angular.copy($scope.tmp);
        form.$setPristine();
    };

    $scope.addChapter = function (chapter) {
        chapter.courseId = $scope.course.id;
        chapterService.postChapter(chapter).then(function (response) {
            var newChapter = response.data;
            $scope.chapters.push(newChapter);
            $scope.newChapter = {};
        }, function (response) {
            $scope.newChapter = {};
        });
    };

    $scope.removeChapter = function (chapter) {
        if (confirm('Вы уверены, что хотите удалить главу ' + chapter.title + '?')) {
            chapterService.deleteChapter(chapter).then(function (response) {
                $scope.chapters = _.without($scope.chapters, chapter);
            }, function (response) {
            });
        }
    };

    $scope.addTest = function (test) {
        courseToTestService.postCourseToTest({courseId: $scope.course.id, testId: test.id}).then(function () {
            $scope.courseTests.push({id: test.id, title: test.title});
            minusTestsList();
            $scope.test = {};
        }, function (response) {
            $scope.test = {};
        });
    };

    $scope.removeTest = function (test) {
            courseToTestService.deleteCourseToTest({courseId: $scope.course.id, testId: test.id}).then(function () {
                removeTestFromArray(test.id);
                minusTestsList();
            }, function (response) {
            });
    };

    function minusTestsList(){
        var myArray = angular.copy($scope.tests);
        var toRemove = angular.copy($scope.courseTests);
        for( var i=myArray.length - 1; i>=0; i--){
            for( var j=0; j<toRemove.length; j++){
                if(myArray[i] && (myArray[i].id === toRemove[j].id)){
                    myArray.splice(i, 1);
                }
            }
        }
        $scope.filteredTests = angular.copy(myArray);
    }
    
    function removeTestFromArray(id) {
        for (var i = 0; i < $scope.courseTests.length; i++)
            if ($scope.courseTests[i].id === id) {
                $scope.courseTests.splice(i, 1);
                break;
            }
    }

    $scope.preview = function(id) {
        var getCourse = chapterService.getChapter(id);
        getCourse.then(function(response){
            $scope.previewData = response.data;
            $('#chapterPreview').modal();
        });
    };

    $('#chapterPreview').on('hidden.bs.modal', function () {
        $scope.previewData = {};
    })

}]);
