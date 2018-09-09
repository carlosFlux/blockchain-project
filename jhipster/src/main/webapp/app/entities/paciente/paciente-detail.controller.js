(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('PacienteDetailController', PacienteDetailController);

    PacienteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Paciente', 'HistoriaClinica'];

    function PacienteDetailController($scope, $rootScope, $stateParams, previousState, entity, Paciente, HistoriaClinica) {
        var vm = this;

        vm.paciente = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jappApp:pacienteUpdate', function(event, result) {
            vm.paciente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
