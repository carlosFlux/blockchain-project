(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('MedicoDetailController', MedicoDetailController);

    MedicoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Medico', 'HistoriaClinica'];

    function MedicoDetailController($scope, $rootScope, $stateParams, previousState, entity, Medico, HistoriaClinica) {
        var vm = this;

        vm.medico = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jappApp:medicoUpdate', function(event, result) {
            vm.medico = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
