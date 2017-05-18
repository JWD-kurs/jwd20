var sajamApp = angular.module("sajamApp", ['ngRoute']);

sajamApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/',{
        templateUrl: '/static/app/html/partial/standovi.html'
    }).when('/standovi/edit/:id',{
        templateUrl: '/static/app/html/partial/edit-stand.html'
    }).otherwise({
        redirectTo: '/'
    });
}]);

sajamApp.controller("standoviCtrl", function($scope, $http, $location){

    $scope.base_url_standovi = "/api/standovi";
    $scope.base_url_sajmovi = "/api/sajmovi";


    $scope.pageNum = 0;
    $scope.totalPages = 0;

    $scope.sajmovi = [];
    $scope.standovi = [];

    $scope.noviStand = {};
    $scope.noviStand.zakupac = "";
    $scope.noviStand.povrisna = "";
    $scope.noviStand.sajamId = "";


    $scope.trazeniStand = {};
    $scope.trazeniStand.zakupac = "";
    $scope.trazeniStand.minP = "";
    $scope.trazeniStand.maxP = "";

    var getStandovi = function(){

        var config = {params: {}};

        config.params.pageNum = $scope.pageNum;

        if($scope.trazeniStand.zakupac != ""){
            config.params.zakupac = $scope.trazeniStand.zakupac;
        }

        if($scope.trazeniStand.minP != ""){
            config.params.minP = $scope.trazeniStand.minP;
        }

        if($scope.trazeniStand.maxP != ""){
            config.params.maxP = $scope.trazeniStand.maxP;
        }


        $http.get($scope.base_url_standovi, config)
            .then(function success(data){
                console.log(data.data);
                $scope.standovi = data.data;
                $scope.totalPages = data.headers('totalPages');
                //alert("Radi dobavljanje standova");

            });
    };

    var getSajmovi = function(){

        $http.get($scope.base_url_sajmovi)
            .then(function success(data){
                $scope.sajmovi = data.data;
                console.log(data.data);
                //alert("Uspesno dobavljeni sajmovi");
            });

    };

    getStandovi();
    getSajmovi();

    $scope.nazad = function(){
        if($scope.pageNum > 0) {
            $scope.pageNum = $scope.pageNum - 1;
            getStandovi();
        }
    };

    $scope.napred = function(){
        if($scope.pageNum < $scope.totalPages - 1){
            $scope.pageNum = $scope.pageNum + 1;
            getStandovi();
        }
    };

    $scope.dodaj = function(){
        $http.post($scope.base_url_standovi, $scope.noviStand)
            .then(function success(data){
                console.log(data.data);
                alert("Uspesno dodat štand u bazu.");
                getStandovi();
            });
    };

    $scope.trazi = function () {
        $scope.pageNum = 0;
        getStandovi();
    }

    $scope.izmeni = function(id){
        $location.path('/standovi/edit/' + id);
    }
});

sajamApp.controller("editStandCtrl", function($scope, $http, $routeParams, $location){

    $scope.base_url_standovi = "/api/standovi";
    $scope.base_url_sajmovi = "/api/sajmovi";

    $scope.sajmovi = [];

    $scope.stariStand = null;

    var getStariStand = function(){

        $http.get($scope.base_url_standovi + "/" + $routeParams.id)
            .then(function success(data){
               $scope.stariStand = data.data;
                
                alert(data.data.sajamId + " je id sajma za ovaj stand");
            });

    }

    var getSajmovi = function(){

        $http.get($scope.base_url_sajmovi)
            .then(function success(data){
                $scope.sajmovi = data.data;
                console.log(data.data);
                alert("Dobavio sam sajmove!");
                getStariStand();//Ispravio sam, i sada se odavde poziva! Da bih bio siguran da postoje sajmovi pre nego sto se izvrse provere u ng-selected!
            });

    };

    getSajmovi();
    
    
    $scope.izmeni = function(){
        $http.put($scope.base_url_standovi + "/" + $scope.stariStand.id, $scope.stariStand)
            .then(function success(data){
                alert("Uspešno izmenjen objekat!");
                $location.path("/");
            });
    }
});












