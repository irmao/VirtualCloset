'use strict';

angular.module('virtualcloset').controller('HomeController', HomeController);

HomeController.$inject = [ 'RequestService', 'toaster' ];

function HomeController(RequestService, toaster) {
	var self = this;
	
	self.init = function() {
	}
	
	self.logout = function() {
		self.promise = RequestService.performRequest('POST', '/logout');
		
		self.promise.then((response) => {
			window.location.pathname = response.data;
		}, (error) => {
			console.log(error);
			toaster.pop('error', 'Falha ao efetuar logout');
		});
	}
	
}