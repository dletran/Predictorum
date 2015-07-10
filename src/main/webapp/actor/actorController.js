/**
 * 
 */

var actorController = angular.module('predictorum.actorController', ['predictorum.actorService']);

actorController.controller("actorController", function($scope,actorService){
	
	$scope.actors = [{username: 'pedro'},{username: 'ana'},{username: 'migue'},{username:'david'},{username:'luisa'}]
	
});