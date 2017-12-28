'use strict';

/**
 * Registers the routes
 */
angular.module('virtualcloset').config(ConfigRouter);

ConfigRouter.$inject = [ '$stateProvider', '$urlRouterProvider' ];

function ConfigRouter($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/');
	
	$stateProvider
		.state('home', {
			url: '/',
			templateUrl: 'js/home/home.html',
			controller: 'HomeController',
			controllerAs: 'ctrl'
		})
		.state('clothes', {
			url: '/clothes',
			templateUrl: 'js/clothes/clothes.html',
			controller: 'ClothesController',
			controllerAs: 'ctrl'
		})
		.state('closet', {
			url: '/closet',
			templateUrl: 'js/closet/closet.html',
			controller: 'ClosetController',
			controllerAs: 'ctrl'
		})
		.state('stylist', {
			url: '/stylist',
			templateUrl: 'js/stylist/stylist.html',
			controller: 'StylistController',
			controllerAs: 'ctrl'
		});
}
