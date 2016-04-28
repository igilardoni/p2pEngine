(function() {
	var module = angular.module('search', []);
	module.config(function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise("/");
		$stateProvider
		.state('search', {
			url: '/search',
			templateUrl: 'search.html',
			controller: function($scope) {
				$scope.app.setBackUrl(null);
				$scope.app.setTitle('Search');
				$scope.app.setContextButton(null);
			}
		});
	});
})();