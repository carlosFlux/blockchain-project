(function() {
    'use strict';

    angular
        .module('jappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('institucion', {
            parent: 'entity',
            url: '/institucion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Institucions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/institucion/institucions.html',
                    controller: 'InstitucionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('institucion-detail', {
            parent: 'institucion',
            url: '/institucion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Institucion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/institucion/institucion-detail.html',
                    controller: 'InstitucionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Institucion', function($stateParams, Institucion) {
                    return Institucion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'institucion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('institucion-detail.edit', {
            parent: 'institucion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/institucion/institucion-dialog.html',
                    controller: 'InstitucionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Institucion', function(Institucion) {
                            return Institucion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('institucion.new', {
            parent: 'institucion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/institucion/institucion-dialog.html',
                    controller: 'InstitucionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('institucion', null, { reload: 'institucion' });
                }, function() {
                    $state.go('institucion');
                });
            }]
        })
        .state('institucion.edit', {
            parent: 'institucion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/institucion/institucion-dialog.html',
                    controller: 'InstitucionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Institucion', function(Institucion) {
                            return Institucion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('institucion', null, { reload: 'institucion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('institucion.delete', {
            parent: 'institucion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/institucion/institucion-delete-dialog.html',
                    controller: 'InstitucionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Institucion', function(Institucion) {
                            return Institucion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('institucion', null, { reload: 'institucion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
