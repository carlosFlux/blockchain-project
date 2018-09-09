(function() {
    'use strict';

    angular
        .module('jappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('historia-clinica', {
            parent: 'entity',
            url: '/historia-clinica',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'HistoriaClinicas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/historia-clinica/historia-clinicas.html',
                    controller: 'HistoriaClinicaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('historia-clinica-detail', {
            parent: 'historia-clinica',
            url: '/historia-clinica/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'HistoriaClinica'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/historia-clinica/historia-clinica-detail.html',
                    controller: 'HistoriaClinicaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'HistoriaClinica', function($stateParams, HistoriaClinica) {
                    return HistoriaClinica.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'historia-clinica',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('historia-clinica-detail.edit', {
            parent: 'historia-clinica-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_INSTITUCION']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historia-clinica/historia-clinica-dialog.html',
                    controller: 'HistoriaClinicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HistoriaClinica', function(HistoriaClinica) {
                            return HistoriaClinica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('historia-clinica.new', {
            parent: 'historia-clinica',
            url: '/new',
            data: {
                authorities: ['ROLE_INSTITUCION', 'ROLE_MEDICO']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historia-clinica/historia-clinica-dialog.html',
                    controller: 'HistoriaClinicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('historia-clinica', null, { reload: 'historia-clinica' });
                }, function() {
                    $state.go('historia-clinica');
                });
            }]
        })
        .state('historia-clinica.edit', {
            parent: 'historia-clinica',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_INSTITUCION', 'ROLE_MEDICO']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historia-clinica/historia-clinica-dialog.html',
                    controller: 'HistoriaClinicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HistoriaClinica', function(HistoriaClinica) {
                            return HistoriaClinica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('historia-clinica', null, { reload: 'historia-clinica' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('historia-clinica.delete', {
            parent: 'historia-clinica',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_INSTITUCION']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/historia-clinica/historia-clinica-delete-dialog.html',
                    controller: 'HistoriaClinicaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['HistoriaClinica', function(HistoriaClinica) {
                            return HistoriaClinica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('historia-clinica', null, { reload: 'historia-clinica' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
