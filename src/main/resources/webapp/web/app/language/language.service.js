(function() {
  'use strict';

  function LanguageService($resource) {
    return $resource('../webservices/languages/:id');
  }

  angular.module('WebApp').factory('LanguageService', [ '$resource', LanguageService ]);

}());