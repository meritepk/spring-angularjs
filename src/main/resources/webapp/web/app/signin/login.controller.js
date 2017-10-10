(function() {
  'use strict';

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

  angular.module('WebApp').controller('LoginController', [ '$http', '$location', LoginController ]);

}());