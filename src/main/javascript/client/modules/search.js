(function() {
	var module = angular.module('search', []);
	module.config(function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise("/");
		$stateProvider
		.state('search', {
			url: '/search',
			templateUrl: 'search.html',
			controller: function($scope, $http) {
				$scope.app.setBackUrl(null);
				$scope.app.setTitle('Search');
				$scope.app.setContextButton(null);
				$scope.results = [];

				$scope.search = function() {
					$http.get(RESTAPISERVER + "/api/search/simple?title=" + $scope.research).then(function(response) {
						$scope.results = response.data;
					}, function(response) {

					});
				}

			}
		});
	});
})();
