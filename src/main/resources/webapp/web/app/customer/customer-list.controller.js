(function() {
  'use strict';

  function CustomerListController($q, customerService, countryService, languageService) {
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

  angular.module('WebApp').controller('CustomerListController',
      [ '$q', 'CustomerService', 'CountryService', 'LanguageService', CustomerListController ]);

}());