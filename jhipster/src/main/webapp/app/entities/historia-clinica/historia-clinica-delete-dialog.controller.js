(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('HistoriaClinicaDeleteController',HistoriaClinicaDeleteController);

    HistoriaClinicaDeleteController.$inject = ['$uibModalInstance', 'entity', 'HistoriaClinica'];

    function HistoriaClinicaDeleteController($uibModalInstance, entity, HistoriaClinica) {
        var vm = this;

        vm.historiaClinica = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            HistoriaClinica.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
