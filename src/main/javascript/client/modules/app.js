RESTAPISERVER = 'http://localhost:8080';

(function() {
	var module = angular.module('app', ['ui.router', 'ngAnimate', 'ngResource', 'services.rest', 'app.myItems', 'search', 'messages', 'users']);
	module.config(function($stateProvider, $urlRouterProvider) {
		$stateProvider
		.state('home', {
			url: '/',
			templateUrl: 'home.html',
			controller: function($scope) {
				$scope.app.setBackUrl(null);
				$scope.app.setContextButton('search');
				$scope.app.setTitle('SXP network');
			}
		});
	});

	module.controller('appController', function($rootScope) {
		var self = this;
		this.backUrl = null;
		$rootScope.apiUrl = 'http://localhost:8080';
		this.contextButton = null;
		this.title = "SXP network";
		this.contextId = 0;
		this.setBackUrl = function(url) {
			self.backUrl = url;
		}
		this.getBackUrl = function() {
			return self.backUrl;
		}
		this.showBackUrl = function() {
			return self.backUrl != null;
		}

		this.setTitle = function(title) {
			self.title = title;
		}

		this.getTitle = function() {
			return self.title;
		}
		this.toggleMenu = function() {
			$("#wrapper").toggleClass("toggled");
		};
		this.showContextButton = function(button) {
			return button === self.contextButton;
		};
		this.setContextButton = function(button) {
			self.contextButton = button;
		};

		this.setContextId = function(id) {
			self.contextId = id;
		}

		this.getContextId = function() {
			return self.contextId;
		}

		this.configHeader = function(obj) {
			if(obj.hasOwnProperty('back')) {
				self.setBackUrl(obj.back == true ? "true":null);
			} else {
				self.setBackUrl(null);
			}
			if(obj.hasOwnProperty('title')) {
				self.setTitle(obj.title);
			} else {
				self.setTitle("SXP network");
			}
			if(obj.hasOwnProperty('contextButton')) {
				self.setContextButton(obj.contextButton);
			} else {
				self.setContextButton(null);
			}

			if(obj.hasOwnProperty('contextId')) {
				self.setContextId(obj.contextId);
			}
		};
	});

	module.directive('navbar', function() {
		return {
			restrict: 'E',
			templateUrl: 'navbar.html'
		};
	});

	module.directive('sidemenu', function() {
		return {
			restrict: 'E',
			templateUrl: 'sidemenu.html'
		};
	});

	module.directive('contextButton', function() {
		return {
			restrict: 'E',
			templateUrl: 'contextButtons.html'
		};
	});

})();
