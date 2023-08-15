(function() {
  'use strict';

  function CustomerService($resource) {
    return $resource('../api/v1/customers/:id');
  }

  angular.module('WebApp').factory('CustomerService', [ '$resource', CustomerService ]);

}());