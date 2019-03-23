(function () {
    'use strict';

    angular.module('registration', ['ui.router'])
        .config(function ($stateProvider, $urlRouterProvider) {
            var login = {
                name: 'login',
                url: '/login',
                templateUrl: 'login.html',
                controller: 'LoginController'
            };

            var user = {
                name: 'user',
                url: '/user',
                templateUrl: 'user.html',
                controller: 'UserController',
                resolve:{
                    userLogged: function($state, $rootScope){
                        if(!$rootScope.me){
                            console.log('Not authorized!!')
                            $state.go('login');
                        }
                    }
                }
            };

            $urlRouterProvider.otherwise('/login');

            $stateProvider.state(login);
            $stateProvider.state(user);
        })

        .controller('LoginController', function ($scope, $http, $rootScope, $state) {

            $scope.credentials = {username: '', password: ''};

            $scope.login = function () {
                $http.post('api/login', $scope.credentials).then(function (response) {
                    $rootScope.me = response.data;
                    $http.defaults.headers.common['Authorization'] = $rootScope.me.token;
                    $state.go('user');
                }, function (error) {
                    alert('Error authentication...');
                    console.log(error);
                });
            }
        })

        .controller('UserController', function ($scope, $http) {

            $scope.user = {username: '', password: ''};

            $http.get('api/user').then(function (response) {
                $scope.users = response.data;
            });

            $scope.save = function () {
                $http.post('api/user', $scope.user).then(function (response) {
                    $scope.users.push(response.data);
                });

                $scope.user = {username: '', password: ''};
                $scope.statusPassword = {};
            };

            $scope.passwordValidate = function () {
                $scope.statusPassword = {};

                if ($scope.user.password && $scope.user.password.length >= 6) {
                    $scope.statusPassword.cssClass = 'has-success';
                    $scope.statusPassword.icon = 'glyphicon-ok';
                    $scope.statusPassword.msg = 'Senha forte';
                } else {
                    $scope.statusPassword.cssClass = 'has-error';
                    $scope.statusPassword.icon = 'glyphicon-remove';
                    $scope.statusPassword.msg = 'Senha fraca';
                }
            };
        });

})();

