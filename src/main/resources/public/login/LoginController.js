'use strict';

angular.module('virtualcloset.login').controller('LoginController', LoginController);

LoginController.$inject = [ '$http', 'toaster', '$location' ];

function LoginController($http, toaster, $location) {
	var self = this;
	
	self.init = function() {
		self.loadCsrf();
	}
	
	self.loadCsrf = function() {
		self.promise = $http(this.buildHttpConfig('GET','/csrf'));
		
		self.promise.then((response) => {
			self.csrf = response.data.token;
		});
	}
	
	self.postCredentials = function() {
		let data = "username=" + self.username + "&password=" + self.password 
			+ "&_csrf=" + self.csrf;
		
		self.promise = $http(this.buildHttpConfig('POST', '/login', data));
		self.promise.then((response) => {
			window.location.pathname = response.data;
		}, (error) => {
			toaster.pop('error', 'Usuário ou senha inválidos');
		});
	}
	
	self.buildHttpConfig = function(method, api, data) {
		let timeout = 5000;
		let config = {
		      method: method,
		      url: api,
		      timeout: timeout,
		      headers: {
		    	  'Content-Type': 'application/x-www-form-urlencoded'
		      }
		};
		config.data = data;
		return config;
	}
	
}