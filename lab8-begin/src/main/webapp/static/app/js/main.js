var wafepaApp = angular.module('wafepaApp', ['ngRoute']);

wafepaApp.config(['$routeProvider', function($routeProvider) {
    $routeProvider
    .when('/', {
        templateUrl : '/static/app/html/partial/proba.html'
    })
    .when('/activities', {
        templateUrl : '/static/app/html/partial/activities.html'
    })
    .when('/activity/:id', {
        templateUrl : '/static/app/html/partial/activity.html'
    })
    .otherwise({
        redirectTo: '/'
    });
}]);

wafepaApp.controller('activityCtrl', function($scope, $http, $routeParams, $location){
    console.log($routeParams.id);
    var loadActivity = function () {
        $http.get('/api/activities/'+$routeParams.id)
            .then(function (resp) {
                $scope.activity = resp.data; 
            });
    }
    loadActivity();
    $scope.save = function () {
        $http.put('/api/activities/'+$scope.activity.id, $scope.activity)
            .then(function () {
                $location.path('/activities')
            });            
    }
});

wafepaApp.controller('activitiesCtrl', function($scope, $http, $location){
    var loadActivities = function () {
        var activitiesPromise = $http.get('/api/activities');
        activitiesPromise.then(function (resp) {
            console.log('resp:',resp);
            $scope.activities = resp.data;
        }, function (resp) {
            console.log('resp:',resp);
        });
    }

    $scope.showInNewPage = function (id) {
        $location.path('/activity/'+id);
    }

    loadActivities();

    $scope.prepForUpdate = function (activity) {
        $scope.newActivity = angular.copy(activity);
    }

    $scope.save = function () {
        if(!$scope.newActivity.id){
            $http.post('/api/activities/',$scope.newActivity)
                .then(loadActivities);
        }
        else{
            $http.put('/api/activities/'+$scope.newActivity.id, $scope.newActivity)
                .then(function () {
                    loadActivities();
                    $scope.newActivity={};
                });            
        }
    }

    $scope.del = function (id) {
        console.log('delete activity id:',id);
        $http.delete('/api/activities/'+id).then(function  () {
            loadActivities();
        });
    }
});
