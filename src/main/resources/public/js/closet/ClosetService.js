'use strict';

angular.module('virtualcloset').service('ClosetService', ClosetService);

ClosetService.$inject = [ 'BaseService' ];

function ClosetService(BaseService) {
	var self = this;
	self.api = 'closet';
	
	self.get  = (fn) => BaseService.get(self.api, fn);
	self.post = (obj, fn) => BaseService.post(self.api, obj, fn);
	self.delete = (id, fn) => BaseService.delete(self.api, id, fn);
}