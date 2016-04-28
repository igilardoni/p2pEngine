(function() {
	var module = angular.module('items', []);

	module.config(function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise("/");
		$stateProvider
		.state('myitems', {
			url: '/myitems',
			templateUrl: 'myitems.html',
			controller: function($scope) {
				$scope.app.setBackUrl(null);
				$scope.app.setContextButton('addItem');
				$scope.app.setTitle('Items');
			}
		})
		.state('addItem', {
			url: '/addItem',
			templateUrl: 'addItem.html',
			controller: function($scope, $http) {
				$scope.app.setTitle('Add item');
				$scope.app.setContextButton(null);
				$scope.app.setBackUrl('true');
				$scope.submit = function() {
					var item = new Item({
						title: $scope.form.title,
						description: $scope.form.description
					});
					item.$save();
					/*var req = 'http://localhost:8080/api/item/add?title=' + $scope.form.title + '&description=' + $scope.form.description;
					$http.get(req).then(function(response) {

					}, function(response) {

					});*/

				};
			},
			controllerAs: 'addItem'
		})
		.state('editItem', {
			url: '/editItem/{id:int}',
			templateUrl: 'addItem.html',
			controller: function($scope, $stateParams, $http) {
					$scope.app.setBackUrl('previous');
					$scope.app.setTitle('Edit item');
					$scope.app.setContextButton(null);
					alert($scope.items);
					$scope.itemIndex = $stateParams.id;
					$scope.submit = function() {
						alert($scope.form.title);
						var req = 'http://localhost:8080/api/item/edit?id=' + $scope.form.id + '&title=' + $scope.form.title + '&description=' + $scope.form.description;
						$http.get(req).then(function(response) {
							//
						}, function(response) {
							//
						});
					}
				//	$scope.form.title = $scope.items.getItemView($stateParams.id).title;
				//	$scope.form.description = $scope.items.getItemView($stateParams.id).description;
			},
			controllerAs: 'editItem'
		})
		.state('item', {
			url: '/item/{id:int}',
			templateUrl: 'item.html',
			controller: function($scope, $stateParams) {
				$scope.app.setBackUrl('previous');
				$scope.app.setTitle('Items');
				$scope.app.setContextButton('editItem');
				$scope.app.setContextId($stateParams.id)
				$scope.itemIndex = $stateParams.id;
			}
		});
	});

	module.directive('items', ['$http', function($http) {
		return {
			restrict: 'A',
			controller: function($scope) {
				var self = this;
				this.test = "lol";
				this.itemView = null;
				this.items = [];

				$http.get('http://localhost:8080/api/item/getAll').then(function (response) {
					self.items = response.data;
				}, function(response) {

				});

				this.getItems = function() {
					return self.items;
				};

				this.getItemView = function(id) {
					return self.items[id];
				}

				this.setItemView = function(id) {
					self.itemView = id;
				}
			},
			controllerAs: 'items'
		};
	}]);

	module.directive('item', function() {
		return {
			restrict: 'E',
			templateUrl: "itemItem.html"
		}
	});
})();
