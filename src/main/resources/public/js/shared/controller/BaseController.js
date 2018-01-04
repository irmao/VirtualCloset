'use strict';

angular.module('virtualcloset').controller('BaseController', BaseController);

BaseController.$inject = [ 'BaseService' ];

function BaseController(BaseService) {
	var self = this;
	self.getPromise = () => BaseService.promise;
}