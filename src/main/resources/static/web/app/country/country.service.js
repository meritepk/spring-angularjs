(function() {
  'use strict';

  function CountryService($resource) {
    return $resource('../api/v1/countries/:id');
  }

  angular.module('WebApp').factory('CountryService', [ '$resource', CountryService ]);

}());