'use strict';

angular.module('virtualcloset.login').controller('NewUserController', NewUserController);

NewUserController.$inject = [ '$http', 'toaster', '$state' ];

function NewUserController($http, toaster, $state) {
	var self = this;
	
	self.init = function() {
	}
	
	self.createNewUser = function() {
		if (self.password !== self.repeatPassword) {
			toaster.pop('error', 'As duas senhas não são iguais');
			return;
		}
		
		let data = {
			name: self.username,
			password: self.password,
			email: self.email
		};
		
		self.promise = $http(this.buildHttpConfig('POST', '/login/api/newuser', data));
		self.promise.then(() => {
			toaster.pop('success', 'Usuário criado com sucesso!');
			$state.go('/login')
			
		}, (error) => {
			error.data.forEach((message) => {
				toaster.pop('error', message);
			});
			
		});
	}
	
	self.buildHttpConfig = function(method, api, data) {
		let timeout = 5000;
		let config = {
		      method: method,
		      url: api,
		      timeout: timeout,
		      headers: {
		    	  'Content-Type': 'application/json'
		      }
		};
		config.data = data;
		return config;
	}
	
}