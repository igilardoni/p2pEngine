(function() {
	var module = angular.module('search', []);
	module.config(function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise("/");
		$stateProvider
		.state('search', {
			url: '/search',
			templateUrl: 'search.html',
			controller: function($scope, $http, Oboe) {
				$scope.app.setBackUrl(null);
				$scope.app.setTitle('Search');
				$scope.app.setContextButton(null);
				$scope.results = [];


				$scope.search = function() {

					Oboe({
	            url: RESTAPISERVER + "/api/search/simple?title=" + $scope.research,
	            pattern: '*',
	            start: function(stream) {
	                // handle to the stream
	                $scope.stream = stream;
	                $scope.status = 'started';
	            },
	            done: function(parsedJSON) {
	                $scope.status = 'done';
	            }
	        }).then(function() {
	            // promise is resolved
	        }, function(error) {
	            // handle errors
	        }, function(node) {
	            // node received
							if(node.length != 0)
	            $scope.results.push(node);
	            if($scope.results.length === 1000 || node.length == 0) {
	                $scope.stream.abort();
	            }
	        });

					/*$http.get(RESTAPISERVER + "/api/search/simple?title=" + $scope.research).then(function(response) {
						$scope.results = response.data;
					}, function(response) {

					});*/
				}

			}
		});
	});
})();
