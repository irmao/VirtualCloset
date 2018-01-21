'use strict';

angular.module('virtualcloset').controller('LoginController', LoginController);

LoginController.$inject = [ '$http', 'toaster', '$state' ];

function LoginController($http, toaster, $state) {
	var self = this;
	
	self.init = function() {
		self.credentials = {};
		self.loadCsrf();
	}
	
	self.login = function() {
	  let data = "username=" + self.credentials.username + "&password=" + self.credentials.password 
			+ "&_csrf=" + self.csrf;
	  
	  self.promise = $http(self.buildHttpConfig('POST', '/api/authentication', data));
	  
	  self.promise.then((response) => {
		  window.location = "/";
	  }, (error) => {
		  toaster.pop('error', 'Usuário ou senha inválidos');
	  });
	}
	
	self.loadCsrf = function() {
		self.promise = $http(this.buildHttpConfig('GET','/csrf'));
		
		self.promise.then((response) => {
			self.csrf = response.data.token;
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