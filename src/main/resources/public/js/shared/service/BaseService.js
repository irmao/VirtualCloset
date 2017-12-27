'use strict';

angular.module('virtualcloset').service('BaseService', BaseService);

BaseService.$inject = [ 'RequestService', 'toaster' ];

function BaseService(RequestService, toaster) {
	var self = this;
	
	self.baseAPI = "api/";
	
	self.defaultFailureCallback = (error) => {
		toaster.pop('error', 'Erro', error);
	};
	
	self.successMessage = function(message) {
		toaster.pop('success', message);
	}
	
	self.get = function(api, successFn) {
		self.promise = RequestService.performRequest('GET', self.baseAPI + api);
		
		self.promise.then((response) => {
			successFn(response)
		}, self.defaultFailureCallback);
	}
	
	self.post = function(api, obj, successFn) {
		self.promise = RequestService.performRequest('POST', self.baseAPI + api, undefined, obj);
		
		self.promise.then((response) => {
			successFn(response)
			self.successMessage('Adicionado com sucesso!');
		}, self.defaultFailureCallback);
	}
	
	self.delete = function(api, id, successFn) {
		self.promise = RequestService.performRequest('DELETE', self.baseAPI + api, [id], undefined);
		
		self.promise.then((response) => {
			successFn(response);
			self.successMessage('Removido com sucesso!');
		}, self.defaultFailureCallback);
	}
}