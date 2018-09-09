(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('SintomaDialogController', SintomaDialogController);

    SintomaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sintoma', 'HistoriaClinica'];

    function SintomaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sintoma, HistoriaClinica) {
        var vm = this;

        vm.sintoma = entity;
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
            if (vm.sintoma.id !== null) {
                Sintoma.update(vm.sintoma, onSaveSuccess, onSaveError);
            } else {
                Sintoma.save(vm.sintoma, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jappApp:sintomaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
