'use strict';

angular.module('jobvacancyApp')
    .controller('JobOfferController', function ($scope, JobOffer, ParseLinks) {
        $scope.jobOffers = [];
        $scope.page = 0;
	document.getElementById("searchActive").disabled=true;
	document.getElementById("searchExpired").disabled=false;
	$scope.searchExpired = function() { 
    			$scope.viewExpired();
		};
	$scope.searchActive = function() { 
    			$scope.viewActive();
		};
        $scope.loadActive = function() {
            JobOffer.query({page: $scope.page, size: 20, view: true}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
		document.getElementById("searchActive").disabled=true;
		document.getElementById("searchExpired").disabled=false;		
                $scope.jobOffers = result;
            });
        };
	$scope.loadExpired = function() {
            JobOffer.query({page: $scope.page, size: 20, view:false}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
		document.getElementById("searchActive").disabled=false;
		document.getElementById("searchExpired").disabled=true;	
		$scope.clear();               
		$scope.jobOffers = result;
	
            });
        };
	$scope.viewActive = function(page) {
            $scope.page = page;
            $scope.loadActive();
        };
        $scope.viewExpired = function(page) {
            $scope.page = page;
            $scope.loadExpired();
        };
        $scope.loadActive();

        $scope.delete = function (id) {
            JobOffer.get({id: id}, function(result) {
                $scope.jobOffer = result;
                $('#deleteJobOfferConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JobOffer.delete({id: id},
                function () {
                    $scope.loadActive();
                    $('#deleteJobOfferConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.jobOffer = {title: null, location: null, description: null, id: null, experiencia:null};
        };
    });
