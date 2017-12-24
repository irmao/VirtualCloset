'use strict';

/**
 * Registers the application modules
 */
angular.module('virtualcloset', ['cgBusy', 'ui.router', 
	'isteven-multi-select', 'toaster', 'ngDraggable']);

//configure cgBusy component for application
angular.module('virtualcloset').value('cgBusyDefaults', {
	message:'To terminando...', 
	backdrop: true, 
	delay: 0, 
	minDuration: 500,
	templateUrl: 'lib/angular-busy/4.1.4/angular-busy.html'
});