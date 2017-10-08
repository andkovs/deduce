'use strict';

angular.module('myApp').filter('courseFilter', function() {
    return function( items, courseId ) {
        var filtered = [];
        if (courseId) {
            angular.forEach(items, function(item) {
                if (item.courseId == courseId) {
                    filtered.push(item);
                }
            });
        } else {
            filtered = items;
        }
        return filtered;
    };
});

