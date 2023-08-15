(function() {
  'use strict';

  function LogoutController($http, $location) {
    var self = this;
    self.user = {};
    self.logout = function() {
      $http.post('../logout').then(self.logoutSuccess, self.logoutFailure);
    }
    self.logoutSuccess = function(response) {
      $location.path('/');
    }
    self.logoutFailure = function(response) {
      self.user.error = response.data.message;
    }
  }

  angular.module('WebApp').controller('LogoutController', [ '$http', '$location', LogoutController ]);

}());