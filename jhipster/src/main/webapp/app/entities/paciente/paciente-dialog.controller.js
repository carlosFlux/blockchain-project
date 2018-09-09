(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('PacienteDialogController', PacienteDialogController);

    PacienteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Paciente', 'HistoriaClinica'];

    function PacienteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Paciente, HistoriaClinica) {
        var vm = this;

        vm.paciente = entity;
        vm.clear = clear;
        vm.save = save;
        vm.historiaclinicas = HistoriaClinica.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.paciente.id !== null) {
                Paciente.update(vm.paciente, onSaveSuccess, onSaveError);
            } else {
                Paciente.save(vm.paciente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jappApp:pacienteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
