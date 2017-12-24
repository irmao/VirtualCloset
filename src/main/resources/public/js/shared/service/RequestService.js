'use strict';

angular.module('virtualcloset').service('RequestService', RequestService);

RequestService.$inject = [ '$http' ];
		
function RequestService ($http) {
	var REQUEST_TIMEOUT = 10000;
	
	var self = this;
		
	/**
	 * Function responsible for build httpConfig
	 *
	 * @param {string} method method used by this request (DELETE, GET, PUT, etc)
	 * @param {string} api api name to be reached
	 * @param {string} params parameter list belonging to the request
	 * @param {object} data request´s body
	 * @param {string} timeout
	 * @returns {object} httpConfig to be use in $http()
	 */
	self.buildHttpConfig = function(method, api, params, data, _timeout) {
	
	  var timeout;
	  if (_timeout !== undefined){
		  timeout = _timeout; 
	  }
	  else {
		  timeout = REQUEST_TIMEOUT
	  }
	    // build the request configuration
	var config = {
	      method: method,
	      url: api,
	      timeout: timeout,
	      headers: {
	                'Content-Type': 'application/json',
	                'Cache-Control': 'private, no-cache, no-store, must-revalidate',
	                'Expires': '-1',
	                'Pragma': 'no-cache'
	      }
	};
	if (params !== undefined) {
	    params.forEach(function(param){
	      config.url = config.url + '/' + param;
	        });
	      }
	      config.data = data;
	    
	    return config;
	};     
	
	/**
	 * Function responsible for executing a BDI server request
	 *
	 * @param {string} method method used by this request (DELETE, GET, PUT, etc)
	 * @param {string} api API name to be reached
	 * @param {string} params parameter list belonging to the request
	 * @param {object} data request´s body
	 * @returns {promise} a promise with success and error methods using function(data, status, headers, config)
	 */
	self.performRequest = function(method, api, params, data){
	    return $http(this.buildHttpConfig(method,api,params,data));
	};
	
	/**
	 * Function responsible for executing a BDI server request
	 *
	 * @param {string} method method used by this request (DELETE, GET, PUT, etc)
	 * @param {string} api API name to be reached
	 * @param {string} params parameter list belonging to the request
	 * @param {object} data request´s body
	 * @param {string} timeout
	 * @returns {promise} a promise with success and error methods using function(data, status, headers, config)
	 */
	self.performRequestWithTimeout = function(method, api, params, data, timeout){
	    return $http(this.buildHttpConfig(method,api,params,data, timeout));
	};
}