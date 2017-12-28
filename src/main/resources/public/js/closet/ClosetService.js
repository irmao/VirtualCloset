'use strict';

angular.module('virtualcloset').service('ClosetService', ClosetService);

ClosetService.$inject = [ 'BaseService' ];

function ClosetService(BaseService) {
	var self = this;
	self.api = 'closet';
	
	self.BODY_POSITIONS = [
		{name: 'HEAD', closetClothings: []}, 
		{name: 'TOP', closetClothings: []},
		{name: 'BOTTOM', closetClothings: []},
		{name: 'FOOT', closetClothings: []}
	];
	
	self.get  = (fn) => BaseService.get(self.api, fn);
	self.post = (obj, fn) => BaseService.post(self.api, obj, fn);
	self.delete = (id, fn) => BaseService.delete(self.api, id, fn);
	
	self.getRandom = (options, fn) => {
		let params = [];
		let fancyStr = "?fancy=" + (options.fancy ? "true" : "false");
		params.push(fancyStr);
		
		return BaseService.getOne(self.api, 'random', params, fn);
	}
}