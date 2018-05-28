'use strict';

angular.module('virtualcloset').service('ClosetService', ClosetService);

ClosetService.$inject = [ 'BaseService' ];

function ClosetService(BaseService) {
	var self = this;
	self.api = 'closet';
	
	self.CATEGORY_LOOK = 'LOOK';
	self.CATEGORY_BAGGAGE = 'BAGGAGE';
	
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
		let queryStr = "?fancy=" + (options.fancy ? "true" : "false");
		
		if (options.closetId) {
			queryStr += "&closetId=" + options.closetId;
		}
		
		params.push(queryStr);
		
		return BaseService.getOne(self.api, 'random', params, fn);
	}
	
	self.getLooks = (fn) => BaseService.get(self.api, fn, '?category='+self.CATEGORY_LOOK);
	self.getBaggages = (fn) => BaseService.get(self.api, fn, '?category='+self.CATEGORY_BAGGAGE);
}