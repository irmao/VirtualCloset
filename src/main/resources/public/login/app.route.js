'use strict';

/**
 * Registers the routes
 */
angular.module('virtualcloset.login').config(ConfigRouter);

ConfigRouter.$inject = [ '$stateProvider', '$urlRouterProvider' ];

function ConfigRouter($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/login');
	
	$stateProvider
		.state('/login', {
			url: '/login',
			templateUrl: 'login.html',
			controller: 'LoginController',
			controllerAs: 'ctrl'
		})
		.state('/newuser', {
			url: '/login/newuser',
			templateUrl: 'newuser.html',
			controller: 'NewUserController',
			controllerAs: 'ctrl'
		});
}
