(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('InstitucionDialogController', InstitucionDialogController);

    InstitucionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Institucion', 'HistoriaClinica'];

    function InstitucionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Institucion, HistoriaClinica) {
        var vm = this;

        vm.institucion = entity;
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
            if (vm.institucion.id !== null) {
                Institucion.update(vm.institucion, onSaveSuccess, onSaveError);
            } else {
                Institucion.save(vm.institucion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jappApp:institucionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
