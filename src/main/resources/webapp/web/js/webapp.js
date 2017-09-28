(function() {
  'use strict';

  function WebAppConfig($locationProvider, $routeProvider) {
    $locationProvider.hashPrefix('!');
    $routeProvider.when('/login', {
      templateUrl : 'html/login.html',
      controller : 'LoginController as $ctrl'
    }).when('/customers', {
      templateUrl : 'html/customers.html',
      controller : 'CustomerController as $ctrl'
    }).otherwise('/login');
  }

  function LoginController($http, $location) {
    var self = this;
    self.user = {};
    self.login = function() {
      $http.post('../login',
          'username=' + self.user.name + '&password=' + self.user.password + '&remember-me=' + self.user.rememberme, {
            headers : {
              "Content-Type" : "application/x-www-form-urlencoded"
            }
          }).then(self.loginSuccess, self.loginFailure);
    }
    self.loginSuccess = function(response) {
      $location.path('/customers');
    }
    self.loginFailure = function(response) {
      self.user.error = response.data.message;
    }
  }

  function CustomerController($q, customerService, countryService, languageService) {
    var self = this;
    self.customers = [];
    self.countries = {};
    self.languages = {};
    $q.all([ customerService.get().$promise, countryService.get().$promise, languageService.get().$promise ]).then(
        function(result) {
          self.customers = result[0].data;
          var countries = result[1].data;
          for (var index = 0; index < countries.length; index++) {
            self.countries[countries[index].id] = countries[index];
          }
          var languages = result[2].data;
          for (var index = 0; index < languages.length; index++) {
            self.languages[languages[index].id] = languages[index];
          }
          for (var index = 0; index < self.customers.length; index++) {
            self.customers[index].country = self.countries[self.customers[index].countryId];
            self.customers[index].language = self.languages[self.customers[index].languageId];
          }
        });
  }

  function CustomerService($resource) {
    return $resource('../webservices/customers/:id');
  }

  function CountryService($resource) {
    return $resource('../webservices/countries/:id');
  }

  function LanguageService($resource) {
    return $resource('../webservices/languages/:id');
  }

  angular.module('WebApp', [ 'ngRoute', 'ngResource', 'ng' ])

  .config([ '$locationProvider', '$routeProvider', WebAppConfig ])

  .factory('CustomerService', [ '$resource', CustomerService ])

  .factory('CountryService', [ '$resource', CountryService ])

  .factory('LanguageService', [ '$resource', LanguageService ])

  .controller('LoginController', [ '$http', '$location', LoginController ])

  .controller('CustomerController',
      [ '$q', 'CustomerService', 'CountryService', 'LanguageService', CustomerController ]);

}());