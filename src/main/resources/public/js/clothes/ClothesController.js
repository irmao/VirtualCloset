'use strict';

angular.module('virtualcloset').controller('ClothesController', ClothesController);

ClothesController.$inject = [ 'ClothesService', 'SectorService', '$uibModal' ];

function ClothesController(ClothesService, SectorService, $uibModal) {
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
	
	self.removeClothing = function(clothing) {
		var modalInstance = $uibModal.open({
	      animation: true,
	      templateUrl: 'js/shared/confirm-modal/confirm-modal.html',
	      controllerAs: 'modalCtrl',
	      controller: function($uibModalInstance) {
	    	var self = this;	    		
	    	self.modalTitle = 'Deseja mesmo remover a linda roupa "' + clothing.name + '"?';
	    	self.modalMessage = 'Cuidado: ao remover essa peça de roupa, todos os looks que a utilizam também serão removidos';
	    	self.yes = () => { $uibModalInstance.close(); }
	    	self.no  = () => { $uibModalInstance.dismiss('cancel'); }
	      }
	    });
		
		modalInstance.result.then(() => {
			ClothesService.delete(clothing.id, () => {
				self.init();
			});
		}, () => { /* cancel action: none */ });
	}
	
	self.editClothing = function(clothing) {
		var modalInstance = $uibModal.open({
	      animation: true,
	      templateUrl: 'js/shared/save-name-modal/save-name-modal.html',
	      controllerAs: 'modalCtrl',
	      controller: function($uibModalInstance) {
	    	var self = this;	    		
	    	self.modalTitle = 'Alterar nome';
	    	self.save = () => { $uibModalInstance.close(self.name); }
	    	self.cancel = () => { $uibModalInstance.dismiss('cancel'); }
	      }
	    });
		
		modalInstance.result.then((newName) => {
			let newClothing = angular.copy(clothing);
			newClothing.name = newName;
			
			ClothesService.put(newClothing.id, newClothing, () => {
				self.init();
			});
		}, () => { /* cancel action: none */ });
	}
}