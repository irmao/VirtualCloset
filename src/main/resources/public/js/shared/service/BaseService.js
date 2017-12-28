'use strict';

angular.module('virtualcloset').service('BaseService', BaseService);

BaseService.$inject = [ 'RequestService', 'toaster' ];

function BaseService(RequestService, toaster) {
	var self = this;
	
	self.baseAPI = "api/";
	
	self.defaultFailureCallback = (error) => {
		if (error.data && error.data.length) {
			error.data.forEach((e) => {
				toaster.pop('error', 'Erro', e);	
			});
		} else {
			toaster.pop('error', 'Erro', error);	
		}
		
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
	
	self.getOne = function(api, id, successFn) {
		self.promise = RequestService.performRequest('GET', self.baseAPI + api, [id], undefined);
		
		self.promise.then((response) => {
			successFn(response);
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
	
	self.put = function(api, id, obj, successFn) {
		self.promise = RequestService.performRequest('PUT', self.baseAPI + api, [id], obj);
		
		self.promise.then((response) => {
			successFn(response);
			self.successMessage('Alterado com sucesso!');
		}, self.defaultFailureCallback);
	}
}