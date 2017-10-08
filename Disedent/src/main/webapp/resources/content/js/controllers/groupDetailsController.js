angular.module('myApp').controller('GroupDetailsController', ['$state','$filter','$scope', '$http', '$timeout', '$interval', 'group', 'users', 'courses', 'orderService', 'resultService',
    function ($state, $filter, $scope, $http, $timeout, $interval, group, users, courses, orderService, resultService) {
        $scope.group = group.data;
        console.log( $scope.group);
        $scope.users = users.data;
        $scope.courses = courses.data;
        $scope.orders = group.data.orders;
        $scope.flag = false;
        $scope.order = {id: 0, userId: 0,
        courseId: 0, groupId: $scope.group.id,
        beginDate: moment().format('DD-MM-YYYY HH:mm'), endDate: moment().format('DD-MM-YYYY HH:mm'), creationDate: ''};

        $scope.coursesSearch = angular.copy(courses.data);
        $scope.coursesSearch.unshift({id: 0, title: 'Все курсы'});
        $scope.currentCourse = 0;

        $scope.tmp = angular.copy($scope.order);

        $scope.saveOrder = function (order, form) {
            order.userId = $scope.person.selected.id;
            order.courseId = $scope.course.selected.id;
            orderService.postOrder(order).then(function (response) {
                $scope.orders.push(response.data);
                alert('Новый заказ создан!');
                $scope.resetOrder(form);
            }, function () {
            });
            //$scope.order = angular.copy($scope.tmp);
        };

        $scope.removeOrder = function (order) {
            if (confirm('Вы уверены, что хотите удалить заказ ' + order.id + '?')) {
                orderService.deleteOrder(order).then(function (response) {
                    $scope.orders = _.without($scope.orders, order);
                }, function (response) {
                });
            }
        };

        $scope.resetOrder = function (form) {
            $scope.order = angular.copy($scope.tmp);
            setPersonAndCourse();
            $scope.flag = false;
            $('#endDate').data('DateTimePicker').minDate(moment().format('DD-MM-YYYY HH:mm'));
            $('#endDate').data('DateTimePicker').defaultDate(moment().format('DD-MM-YYYY HH:mm'));
            form.$setPristine();
        };

        //User SEARCH
        $scope.person = {};
        $scope.course = {};

        function setPersonAndCourse(){
            if($scope.order.id == 0){
                $scope.person = {};
                $scope.course = {};
            }
            else {
                $scope.person.selected = $filter('getById')($scope.users, $scope.order.userId);
                $scope.course.selected = $filter('getById')($scope.courses, $scope.order.courseId);
                console.log('in set person', $scope.person, $scope.course)
            }
        }

        setPersonAndCourse();

        //Date
        var dtf = 'DD-MM-YYYY HH:mm';

        var defaultBegin = moment($scope.order.beginDate, dtf);
        var defaultEnd = moment($scope.order.endDate, dtf);


        $('#beginDate').datetimepicker({format: dtf,
            defaultDate: defaultBegin,
            minDate: defaultBegin
        });


        $('#endDate').datetimepicker({format: dtf,
            defaultDate: defaultBegin,
            minDate: defaultBegin
        });

        $('#beginDate').on("dp.change", function (e) {
            $scope.order.beginDate = $("#beginDate input").val();
            $('#endDate').data('DateTimePicker').minDate(e.date);
            $('#endDate').data('DateTimePicker').defaultDate(e.date);
            $("#endDate input").val($('#endDate').data('DateTimePicker').minDate().format('DD-MM-YYYY HH:mm'));
            $scope.flag = true;
            $scope.$apply();
        });

        $('#endDate').on("dp.change", function () {
            $scope.order.endDate = $("#endDate input").val();
            $scope.flag = true;
            $scope.$apply();
        });

        //hover title

        $(document).ready(function(){
            $('[data-toggle="tooltip"]').tooltip();
        });

        //results modal

        $scope.getResults = function(order) {
            var getResults = resultService.getResultsByOrderId(order.id);
            getResults.then(function(response){
                $scope.previewData = {};
                $scope.results = response.data;
                $scope.previewData.lastName = order.userLastName;
                $scope.previewData.firstName = order.userFirstName;
                $scope.previewData.middleName = order.userMiddleName;
                $scope.previewData.login = order.userLogin;
                $scope.previewData.beginDate = order.beginDate;
                $scope.previewData.endDate = order.endDate;
                $scope.previewData.courseTitle = order.courseTitle
                $('#orderResults').modal();
            });
        };

        $scope.getResultExcel = function(id){
            resultService.getResultExcel(id);
        };

        $('#orderResults').on('hidden.bs.modal', function () {
            $scope.previewData = {};
        })

    }]);




