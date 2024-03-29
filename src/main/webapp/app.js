// Creación del módulo

var predictorum = angular.module('predictorum', [ 'ngAnimate','ngRoute','ngCookies',
		'pascalprecht.translate', 'smoothScroll', 'predictorum.sessionService', 'predictorum.teamController','predictorum.actorController']);

// Configuración

predictorum.config(['$routeProvider','$locationProvider','$translateProvider','$httpProvider',function($routeProvider, $locationProvider,
		$translateProvider,$httpProvider) {
	
	//Llamadas de distintos dominios
	
	$httpProvider.defaults.useXDomain = true;

	//idiomas
	  
	$translateProvider.useStaticFilesLoader({
		prefix : 'i18n/',
		suffix : '.json'
	});
	$translateProvider.preferredLanguage('es');
	$translateProvider.useCookieStorage();
	
	// Enable escaping of HTML
	$translateProvider.useSanitizeValueStrategy('escaped');
	  
	//rutas
	
	$routeProvider
	
	.when('/team/list', {
		templateUrl : 'team/views/list.html',
		controller : 'teamController'
	})
	
	.when('/team/favorites', {
		templateUrl : 'team/views/list.html',
		controller : 'teamController'
	})
	
	.when('/actor/followers', {
		templateUrl : 'actor/views/list.html',
		controller : 'actorController'
	})

	.otherwise({
		redirectTo : '/'
	});

}]);

predictorum.controller('indexController', function($scope, $location, $translate, sessionService) {

	$scope.$on('$routeChangeStart', function(next, current) { 
		$scope.isWelcome = $location.path() === '/';
	 });
	
	//Login
	
	$scope.login={
			user: "",
			password: "",
			result: ""
	}

	$scope.goLogin = function(){
		sessionService.login($scope.login.user,$scope.login.password).success(function(data) {
			if (data!=="") {
				$scope.login.result = "ERROR";
			} else {
				$scope.login.result = "OK";
				$scope.showMenu = true;
				$scope.toggleMenu();
			};
		});
	}
	
	//Sign up
	
	$scope.goSignUp = function(){
		$scope.signUpSubmitted = true;
		$scope.blankError = false;
		$scope.passwordMatchError = false;
		sessionService.signUp($scope.signUp).then(function(result){
			if(!result.data.success){
				if(result.data.errors.username){
					$scope.blankError = result.data.errors.username.includes('empty')
				}
				if(result.data.errors.password){
					$scope.passwordMatchError = result.data.errors.password.includes('match');
					$scope.blankError = result.data.errors.password.includes('empty') || $scope.blankError;
				}
				if(result.data.errors.rpassword){ 
					$scope.blankError = result.data.errors.rpassword.includes('empty') || $scope.blankError;
				}
				if(result.data.errors.notUnique){
					$scope.notUniqueError = !$scope.passwordMatchError; //para que no se muestren ambos errores a la vez
				}
			}else{
				//se ha registrado y lo logueamos
				sessionService.login($scope.login.user,$scope.login.password).success(function(data) {
						$scope.showMenu = true;
						$scope.toggleMenu();
				});
			}
		});
	}
	
	//Languages
	
	$scope.switchLanguage = function(lang){
		 $translate.use(lang);
	}
	
	//SVG Menu
	$scope.showMenu = sessionService.getPrincipal()!==undefined;
	var svg = document.getElementById('svg-menu'),
    items = svg.querySelectorAll('.item'),
    trigger = document.getElementById('trigger'),
    label = trigger.querySelectorAll('#label')[0],
    open = false;

    	//first scale the elements down
    TweenLite.set(items, {scale:0, visibility:"visible"});
    svg.style.pointerEvents = "none";
	
	$scope.toggleMenu = function(){
	    open = !open;
	    if (open) {
	        TweenMax.staggerTo(items, 0.7, {scale:1, ease:Elastic.easeOut}, 0.05);
	        label.innerHTML = "-";
	      svg.style.pointerEvents = "auto";
	    } else {
	        TweenMax.staggerTo(items, .3, {scale:0, ease:Back.easeIn}, 0.05);
	        label.innerHTML = "+";
	      svg.style.pointerEvents = "none";
	    }
	}

	$scope.predictionsNumber = 5650;

});