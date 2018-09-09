(function() {
    'use strict';

    angular
        .module('jappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sintoma', {
            parent: 'entity',
            url: '/sintoma',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Sintomas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sintoma/sintomas.html',
                    controller: 'SintomaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('sintoma-detail', {
            parent: 'sintoma',
            url: '/sintoma/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Sintoma'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sintoma/sintoma-detail.html',
                    controller: 'SintomaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Sintoma', function($stateParams, Sintoma) {
                    return Sintoma.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sintoma',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sintoma-detail.edit', {
            parent: 'sintoma-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sintoma/sintoma-dialog.html',
                    controller: 'SintomaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sintoma', function(Sintoma) {
                            return Sintoma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sintoma.new', {
            parent: 'sintoma',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sintoma/sintoma-dialog.html',
                    controller: 'SintomaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descripcion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sintoma', null, { reload: 'sintoma' });
                }, function() {
                    $state.go('sintoma');
                });
            }]
        })
        .state('sintoma.edit', {
            parent: 'sintoma',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sintoma/sintoma-dialog.html',
                    controller: 'SintomaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sintoma', function(Sintoma) {
                            return Sintoma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sintoma', null, { reload: 'sintoma' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sintoma.delete', {
            parent: 'sintoma',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sintoma/sintoma-delete-dialog.html',
                    controller: 'SintomaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sintoma', function(Sintoma) {
                            return Sintoma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sintoma', null, { reload: 'sintoma' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
