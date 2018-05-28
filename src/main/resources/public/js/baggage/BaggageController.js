'use strict';

angular.module('virtualcloset').controller('BaggageController', BaggageController);

BaggageController.$inject = [ 'ClosetService', 'SectorService', 'ClothesService', '$uibModal', '$state' ];

function BaggageController(ClosetService, SectorService, ClothesService, $uibModal, $state) {
	var self = this;
	
	self.init = function(_createNew) {
		self.createNew = _createNew ? true : false;

		if (self.createNew) {
			self.loadSectors();			
		} else {
			self.loadClosets();	
		}
		
		self.clearDropAllowed();
		self.removeClothing();
		
		self.currentClosetClothing = [];
	}
	
	self.loadClosets = function() {
		ClosetService.getBaggages((response) => {
			self.closets = response.data;
		});
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
	
	self.backButtonClick = function() {
		if (self.createNew) {
			self.init(false);
			
		} else {
			$state.go('home');
		}
	}
	
	self.onDropComplete = function(bodyPosition, clothing, evt) {
		self.currentClosetClothing.push({
			clothing: clothing,
			zIndex: 0
		});
		self.clearDropAllowed();
	}
	
	self.onDragStart = function(clothing, evt) {
		self.allowsDrop = true;
		self.clonedData = clothing;
	}
	
	self.onDragStop = function(data, evt) {
		self.clearDropAllowed();
	}
	
	self.saveCloset = function() {
		var modalInstance = $uibModal.open({
	      animation: true,
	      templateUrl: 'js/shared/save-name-modal/save-name-modal.html',
	      controllerAs: 'modalCtrl',
	      controller: function($uibModalInstance) {
	    	var self = this;	    		
	    	self.modalTitle = 'Salvar mala';
	    	self.save = () => { $uibModalInstance.close(self.name); }
	    	self.cancel = () => { $uibModalInstance.dismiss('cancel'); }
	      }
	    });
		
		modalInstance.result.then((closetName) => {
			self.executeSave(closetName);
		}, () => { /* cancel action: none */ });
  	}
	
	self.executeSave = function(closetName) {
		let closetObj = {
			name: closetName,
			bodyPositionOverlap: true,
			closetClothing: self.currentClosetClothing,
			category: ClosetService.CATEGORY_BAGGAGE
		};
		
		ClosetService.post(closetObj, () => {
			self.init(false);
		});
	}
	
	self.loadCloset = function(closet) {
		self.removeClothing();
		self.currentClosetClothing = closet.closetClothing;
		self.closetLoaded = closet;
	}
	
	self.removeClothing = function() {
		self.closetLoaded = null;
		self.currentClosetClothing = [];
	}
	
	self.clearDropAllowed = function() {
		self.allowsDrop = false;
		
		self.clonedData = {
			name: ''
		};
	}
	
	self.deleteClosetLoaded = function() {
		let parentController = self;
		
		var modalInstance = $uibModal.open({
	      animation: true,
	      templateUrl: 'js/shared/confirm-modal/confirm-modal.html',
	      controllerAs: 'modalCtrl',
	      controller: function($uibModalInstance) {
	    	var self = this;	    		
	    	self.modalTitle = 'Deseja mesmo remover a mala "' + parentController.closetLoaded.name + '"?';
	    	self.yes = () => { $uibModalInstance.close(parentController.closetLoaded.id); }
	    	self.no  = () => { $uibModalInstance.dismiss('cancel'); }
	      }
	    });
		
		modalInstance.result.then((idToDelete) => {
			ClosetService.delete(idToDelete, () => {
				self.init(false);
			});
		}, () => { /* cancel action: none */ });
	}
}