(function() {
  'use strict';

  function CountryService($resource) {
    return $resource('../webservices/countries/:id');
  }

  angular.module('WebApp').factory('CountryService', [ '$resource', CountryService ]);

}());