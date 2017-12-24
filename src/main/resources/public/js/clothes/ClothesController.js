'use strict';

angular.module('virtualcloset').controller('ClothesController', ClothesController);

ClothesController.$inject = [ 'ClothesService', 'SectorService' ];

function ClothesController(ClothesService, SectorService) {
	var self = this;
	
	self.FANCY_OPTIONS = [
		{label: 'Normal', value: false},
		{label: 'Chique', value: true}
	];
	
	self.init = function() {
		self.clothing = {
			name: '',
			fancy: false,
			sector: undefined
		};
		
		self.loadSectors();
		self.loadClothes();
	}
	
	self.loadClothes = function() {
		ClothesService.get((response) => {
			self.clothes = response.data;
		});
	}
	
	self.loadSectors= function() {
		SectorService.get((response) => {
			self.sectors = response.data;
			self.clothing.sector = self.sectors[0];
		});
	}
	
	self.addClothing = function() {
		ClothesService.post(self.clothing, self.init);
	}

}