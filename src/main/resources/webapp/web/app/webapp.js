(function() {
  'use strict';

  function WebAppConfig($locationProvider, $routeProvider) {
    $locationProvider.hashPrefix('!');
    $routeProvider.when('/login', {
      templateUrl : 'app/signin/login.html',
      controller : 'LoginController as $ctrl'
    }).when('/customers', {
      templateUrl : 'app/customer/customer-list.html',
      controller : 'CustomerListController as $ctrl'
    }).when('/', {
      templateUrl : 'welcome.html'
    }).otherwise('/');
  }

  angular.module('WebApp', [ 'ngRoute', 'ngResource', 'ng' ])

  .config([ '$locationProvider', '$routeProvider', WebAppConfig ]);

}());