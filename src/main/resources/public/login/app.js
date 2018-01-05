'use strict';

/**
 * Registers the application modules
 */
angular.module('virtualcloset.login', ['cgBusy', 'ui.router', 'toaster', 'ngAnimate', 'ui.bootstrap']);

//configure cgBusy component for application
angular.module('virtualcloset.login').value('cgBusyDefaults', {
	message:'To terminando...', 
	backdrop: true, 
	delay: 0, 
	minDuration: 500,
	templateUrl: '/lib/angular-busy/4.1.4/angular-busy.html'
});