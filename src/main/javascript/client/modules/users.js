(function() {
	var module = angular.module('users', []);
	module.config(function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise("/");
		$stateProvider
		.state('subscribe', {
			url: '/subscribe',
			templateUrl: 'subscribe.html',
			controller: function($scope) {
				$scope.app.setBackUrl(null);
				$scope.app.setTitle('Subscribe');
			}
		});
	});
})();