'use strict';

angular.module('virtualcloset').controller('ClosetController', ClosetController);

ClosetController.$inject = [ 'ClosetService', 'SectorService', 'ClothesService', '$uibModal', '$state' ];

function ClosetController(ClosetService, SectorService, ClothesService, $uibModal, $state) {
	var self = this;
	
	self.BODY_POSITIONS = [
		{name: 'HEAD', closetClothings: []}, 
		{name: 'TOP', closetClothings: []},
		{name: 'BOTTOM', closetClothings: []},
		{name: 'FOOT', closetClothings: []}
	];
	
	self.init = function(_createNew) {
		self.createNew = _createNew ? true : false;
		
		if (self.createNew) {
			self.loadSectors();
			
		} else {
			self.loadClosets();	
		}
		
		
		self.clearDropAllowed();
		self.removeClothing();
	}
	
	self.loadClosets = function() {
		ClosetService.get((response) => {
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
		if (clothing.sector.bodyPositions.indexOf(bodyPosition.name) >= 0) {
			let closetClothing = {
				clothing: clothing,
				zIndex: bodyPosition.closetClothings.length
			};
			bodyPosition.closetClothings.push(closetClothing);
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
	
	self.saveCloset = function() {
		var modalInstance = $uibModal.open({
	      animation: true,
	      templateUrl: 'js/shared/save-name-modal/save-name-modal.html',
	      controllerAs: 'modalCtrl',
	      controller: function($uibModalInstance) {
	    	var self = this;	    		
	    	self.modalTitle = 'Salvar look';
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
			bodyPositionOverlap: false,
			closetClothing: []
		};
		
		self.BODY_POSITIONS.forEach((b) => {
			b.closetClothings.forEach((cc) => {
				closetObj.closetClothing.push(cc);
			});
		});
		
		ClosetService.post(closetObj, () => {
			self.init(false);
		});
	}
	
	self.loadCloset = function(closet) {
		self.removeClothing();
		
		closet.closetClothing.forEach((cc) => {
			let bodyPosition = cc.clothing.sector.bodyPositions[0];
			
			self.BODY_POSITIONS.forEach((b) => {
				if (b.name === bodyPosition)  {
					b.closetClothings.push(cc);
				}
			}); 
		});
		
		self.closetLoaded = closet;
	}
	
	self.removeClothing = function() {
		self.BODY_POSITIONS.forEach((b) => {
			b.closetClothings = [];
		});
		
		self.closetLoaded = null;
	}
	
	self.clearDropAllowed = function() {
		self.BODY_POSITIONS.forEach((b) => {
			b.allowsDrop = false;
		});
		
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
	    	self.modalTitle = 'Deseja mesmo remover o look "' + parentController.closetLoaded.name + '"?';
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