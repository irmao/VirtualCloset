'use strict';

angular.module('virtualcloset').controller('ClosetController', ClosetController);

ClosetController.$inject = [ 'ClosetService', 'SectorService', 'ClothesService' ];

function ClosetController(ClosetService, SectorService, ClothesService) {
	var self = this;
	
	self.BODY_POSITIONS = [
		{name: 'HEAD', allowsDrop: false},
		{name: 'TOP', allowsDrop: false},
		{name: 'BOTTOM', allowsDrop: false},
		{name: 'FOOT', allowsDrop: false}
	];
	
	self.init = function() {
		self.loadSectors();
	}
	
	self.loadSectors = function() {
		SectorService.get((response) => {
			self.sectors = response.data;
			self.clothes = {};
			
			self.sectors.forEach((s) => {
				self.clothes[s.id] = [];
			});
			
			ClothesService.get((response) => {
				response.data.forEach((c) => {
					self.clothes[c.sector.id].push(c);
				}); 
			});
		});
	}
	
	self.onDragComplete = function(data, evt) {
		self.clearDropAllowed();
	}
	
	self.onDragStart = function(clothing, evt) {
		self.allowDropPositions(clothing.sector.bodyPositions);
	}
	
	self.onDragStop = function(data, evt) {
		self.clearDropAllowed();
	}
	
	self.allowDropPositions = function(bodyPositions) {
		bodyPositions.forEach((b) => {
			self.BODY_POSITIONS.forEach((bp) => {
				if (b === bp.name) {
					bp.allowsDrop = true;
				}
			});
		});
		
		console.log(self.BODY_POSITIONS);
	}
	
	
	self.clearDropAllowed = function() {
		self.BODY_POSITIONS.forEach((b) => {
			b.allowsDrop = false;
		});
		
		console.log(self.BODY_POSITIONS);
	}
}