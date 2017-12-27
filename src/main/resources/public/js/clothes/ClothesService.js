'use strict';

angular.module('virtualcloset').service('ClothesService', ClothesService);

ClothesService.$inject = [ 'BaseService' ];

function ClothesService(BaseService) {
	var self = this;
	self.api = 'clothing';
	
	self.get  = (fn) => BaseService.get(self.api, fn);
	self.post = (obj, fn) => BaseService.post(self.api, obj, fn);
	self.delete = (id, fn) => BaseService.delete(self.api, id, fn);
}