'use strict';

angular.module('virtualcloset').controller('NewUserController', NewUserController);

NewUserController.$inject = [ 'RequestService', 'toaster', '$state' ];

function NewUserController(RequestService, toaster, $state) {
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
		
		self.promise = RequestService.performRequest('POST', '/api/authentication/newuser', undefined, data);
		self.promise.then(() => {
			toaster.pop('success', 'Usuário criado com sucesso!');
			$state.go('/login')
			
		}, (error) => {
			error.data.forEach((message) => {
				toaster.pop('error', message);
			});
			
		});
	}
}