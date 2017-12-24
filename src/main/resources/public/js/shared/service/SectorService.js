'use strict';

angular.module('virtualcloset').service('SectorService', SectorService);

SectorService.$inject = [ 'BaseService' ];

function SectorService(BaseService) {
	var self = this;
	self.api = 'sector';
	
	self.get = (fn) => BaseService.get(self.api, fn);
}