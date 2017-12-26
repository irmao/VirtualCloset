'use strict';

angular.module('virtualcloset').controller('ClosetController', ClosetController);

ClosetController.$inject = [ 'ClosetService', 'SectorService', 'ClothesService', '$uibModal', '$controller' ];

function ClosetController(ClosetService, SectorService, ClothesService, $uibModal, $controller) {
	var self = this;
	
	self.BODY_POSITIONS = [
		{name: 'HEAD', clothes: []}, 
		{name: 'TOP', clothes: []},
		{name: 'BOTTOM', clothes: []},
		{name: 'FOOT', clothes: []}
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
	
	self.onDropComplete = function(bodyPosition, clothing, evt) {
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
			b.clothes.forEach((c) => {
				closetObj.closetClothing.push({
					zIndex: 0,
					clothing: c
				});
			});
		});
		
		ClosetService.post(closetObj, () => {
			self.init(false);
		});
	}
	
	self.removeClothing = function() {
		self.BODY_POSITIONS.forEach((b) => {
			b.clothes = [];
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