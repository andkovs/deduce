'use strict';

// App

angular.module('myApp', ['ui.router', 'checklist-model', 'ngSanitize', 'ui.select', 'file-model', 'ui.bootstrap', 'disableAll', 'pdf']).run(function($rootScope) {
    $rootScope.isLoading = true;
});

// Routing

angular.module('myApp').config([
    '$stateProvider',
    '$urlRouterProvider',
    '$locationProvider',
    '$sceProvider',
    function ($stateProvider, $urlRouterProvider, $locationProvider, $sceProvider) {
        $stateProvider.state('home', {
            url: '/',
            controller: 'HomeController',
            templateUrl: 'resources/partials/home.html'
            // resolve: {
            //     session: ['sessionService', function (sessionService) {
            //         return sessionService.get();
            //     }]
            // }
        }).state('userResults', {
            url: '/userResults/:id',
            controller: 'UserResultsController',
            resolve: {
                userResults: ['resultService', '$stateParams', function (resultService, $stateParams) {
                    return resultService.getUserResults($stateParams.id);
                }]
            },
            templateUrl: 'resources/partials/userResults.html'
        }).state('userOrders', {
            url: '/userOrders/:id',
            controller: 'UserOrdersController',
            resolve: {
                orders: ['orderService', '$stateParams', function (orderService, $stateParams) {
                    return orderService.getUserOrders($stateParams.id);
                }]
            },
            templateUrl: 'resources/partials/userOrders.html'
        }).state('student', {
            url: '/student/:studentId',
            controller: 'StudentController',
            resolve: {
                student: ['studentService', '$stateParams', function (studentService, $stateParams) {
                    return studentService.get($stateParams.studentId);
                }]
            },
            templateUrl: 'resources/partials/student.html'
        }).state('student.topicView', {
            url: '/topicView/:id',
            controller: 'TopicViewController',
            resolve: {
                topic: ['topicService','$stateParams', function (topicService, $stateParams) {
                    return topicService.getTopic($stateParams.id);
                }]
            },
            templateUrl: 'resources/partials/topicView.html'
        }).state('student.userTestResult', {
            url: '/userTestResult/:id',
            controller: 'UserTestResultController',
            resolve: {
                result: ['resultService','$stateParams', function (resultService, $stateParams) {
                    return resultService.getResult($stateParams.id);
                }]
            },
            templateUrl: 'resources/partials/userTestResult.html'
        }).state('student.testInfo', {
            url: '/testInfo/:id',
            controller: 'TestInfoController',
            resolve: {
                test: ['testService','$stateParams', function (testService, $stateParams) {
                    return testService.getTestInfo($stateParams.studentId, $stateParams.id);
                }]
            },
            templateUrl: 'resources/partials/testInfo.html'
        }).state('student.testView', {
            url: '/testView/:id',
            controller: 'TestViewController',
            resolve: {
                test: ['testService','$stateParams', function (testService, $stateParams) {
                    return testService.getTestView($stateParams.id);
                }]
            },
            templateUrl: 'resources/partials/testView.html'
        }).state('tests', {
            url: '/tests',
            controller: 'TestsController',
            resolve: {
                tests: ['testService', function (testService) {
                    return testService.get();
                }]
            },
            templateUrl: 'resources/partials/tests.html'
        }).state('groups', {
            url: '/groups',
            controller: 'GroupsController',
            resolve: {
                groups: ['groupService', function (groupService) {
                    return groupService.get();
                }]
            },
            templateUrl: 'resources/partials/groups.html'
        }).state('groupDetails', {
            url: '/groups/details/:id',
            controller: 'GroupDetailsController',
            resolve: {
                group: ['groupService', '$stateParams', function (groupService, $stateParams) {
                    return groupService.getGroup($stateParams.id);
                }],
                users: ['userService', function (userService) {
                    return userService.get();
                }],
                courses: ['courseService', function (courseService) {
                    return courseService.get();
                }]
            },
            templateUrl: 'resources/partials/groupDetails.html'
        }).state('orderDetails', {
            url: '/order/details/:id',
            controller: 'OrderDetailsController',
            resolve: {
                order: ['orderService', '$stateParams', function (orderService, $stateParams) {
                    return orderService.getOrder($stateParams.id);
                }],
                users: ['userService', function (userService) {
                    return userService.get();
                }],
                courses: ['courseService', function (courseService) {
                    return courseService.get();
                }]
            },
            templateUrl: 'resources/partials/orderDetails.html'
        }).state('users', {
            url: '/users',
            controller: 'UsersController',
            resolve: {
                users: ['userService', function (userService) {
                    return userService.get();
                }]
            },
            templateUrl: 'resources/partials/users.html'
        }).state('userDetails', {
            url: '/user/details/:id',
            controller: 'UserDetailsController',
            resolve: {
                user: ['userService', '$stateParams', function (userService, $stateParams) {
                    return userService.getUser($stateParams.id);
                }],
                roles: ['roleService', function (roleService) {
                    return roleService.get();
                }]
            },
            templateUrl: 'resources/partials/userDetails.html'
        }).state('questionDetails', {
            url: '/question/details/:id',
            controller: 'QuestionDetailsController',
            resolve: {
                question: ['questionService', '$stateParams', function (questionService, $stateParams) {
                    return questionService.getQuestion($stateParams.id);
                }]
            },
            templateUrl: 'resources/partials/questionDetails.html'
        }).state('testDetails', {
            url: '/test/details/:id',
            controller: 'TestDetailsController',
            resolve: {
                test: ['testService', '$stateParams', function (testService, $stateParams) {
                    return testService.getTest($stateParams.id);
                }]
            },
            templateUrl: 'resources/partials/testDetails.html'
        }).state('courseDetails', {
            url: '/courses/details/:id',
            controller: 'CourseDetailsController',
            resolve: {
                course: ['courseService', '$stateParams', function (courseService, $stateParams) {
                    return courseService.getCourse($stateParams.id);
                }],
                tests: ['testService', function (testService) {
                    return testService.get();
                }]
            },
            templateUrl: 'resources/partials/courseDetails.html'
        }).state('chapterDetails', {
            url: '/chapters/details/:id',
            controller: 'ChapterDetailsController',
            resolve: {
                chapter: ['chapterService', '$stateParams', function (chapterService, $stateParams) {
                    return chapterService.getChapter($stateParams.id);
                }]
            },
            templateUrl: 'resources/partials/chapterDetails.html'
        }).state('topicDetails', {
            url: '/topic/details/:id',
            controller: 'TopicDetailsController',
            resolve: {
                topic: ['topicService', '$stateParams', function (topicService, $stateParams) {
                    return topicService.getTopic($stateParams.id);
                }]
            },
            templateUrl: 'resources/partials/topicDetails.html'
        }).state('courses', {
            url: '/courses',
            controller: 'CoursesController',
            resolve: {
                courses: ['courseService', function (courseService) {
                    return courseService.get();
                }]
            },
            templateUrl: 'resources/partials/courses.html'
        });
        $urlRouterProvider.otherwise('/');
        $sceProvider.enabled(false);

    }
]);

angular.module('myApp')
    .factory('myHttpInterceptor', ['$q', '$rootScope', '$injector', 'rootFactory',
        function($q, $rootScope, $injector, rootFactory) {
        return {
            request: function(config) {
                $rootScope.isLoading = true;
                return config;
            },
            requestError: function(config) {
                $rootScope.isLoading = false;
                return config;
            },
            response: function(res) {
                console.log('response succes', res);
                $rootScope.isLoading = false;
                return res;
            },
            responseError: function(res) {
                console.log('response error', res);
                $rootScope.isLoading = false;
                if (res.status === 400) {
                    alert(res.data.message);
                    return $q.reject(res.data.message);
                }
                if(res.status === 401) {
                    rootFactory.update(null, null);
                    sessionStorage.setItem('token', "");
                    $injector.get('$state').transitionTo('home');
                    return $q.reject(res);
                }
                return res;
            }
        }
    }])
    .config(function($httpProvider) {
        $httpProvider.interceptors.push('myHttpInterceptor');
    });











