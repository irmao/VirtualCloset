'use strict';

angular.module('virtualcloset').controller('ClosetController', ClosetController);

ClosetController.$inject = [ 'ClosetService', 'SectorService', 'ClothesService' ];

function ClosetController(ClosetService, SectorService, ClothesService) {
	var self = this;
	
	self.BODY_POSITIONS = [
		{name: 'HEAD', clothes: []}, 
		{name: 'TOP', clothes: []},
		{name: 'BOTTOM', clothes: []},
		{name: 'FOOT', clothes: []}
	];
	
	self.init = function() {
		self.loadSectors();
		self.clearDropAllowed();
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
	
	self.onDropComplete = function(bodyPosition, clothing, evt) {
		console.log(bodyPosition,clothing,evt);
		
		if (clothing.sector.bodyPositions.indexOf(bodyPosition.name) >= 0) {
			bodyPosition.clothes.push(clothing);
		}
		self.clearDropAllowed();
	}
	
	self.onDragStart = function(clothing, evt) {
		self.allowDropPositions(clothing.sector.bodyPositions);
		self.clonedData = clothing;
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
	}
	
	
	self.clearDropAllowed = function() {
		self.BODY_POSITIONS.forEach((b) => {
			b.allowsDrop = false;
		});
		
		self.clonedData = {
			name: ''
		};
	}
}