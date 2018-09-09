(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('InstitucionDetailController', InstitucionDetailController);

    InstitucionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Institucion', 'HistoriaClinica'];

    function InstitucionDetailController($scope, $rootScope, $stateParams, previousState, entity, Institucion, HistoriaClinica) {
        var vm = this;

        vm.institucion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jappApp:institucionUpdate', function(event, result) {
            vm.institucion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
