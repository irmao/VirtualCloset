'use strict';

angular.module('virtualcloset').controller('StylistController', StylistController);

StylistController.$inject = [ 'ClosetService' ];

function StylistController(ClosetService) {
	var self = this;
	
	self.BODY_POSITIONS = angular.copy(ClosetService.BODY_POSITIONS);
	
	self.init = function() {
		self.isFancy = false;
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
	
	self.generateRandomCloset = function() {
		let options = {
			fancy: self.isFancy	
		};
		
		ClosetService.getRandom(options, (response) => {
			self.loadCloset(response.data);
		});
	}
}