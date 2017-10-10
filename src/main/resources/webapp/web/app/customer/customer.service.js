(function() {
  'use strict';

  function CustomerService($resource) {
    return $resource('../webservices/customers/:id');
  }

  angular.module('WebApp').factory('CustomerService', [ '$resource', CustomerService ]);

}());