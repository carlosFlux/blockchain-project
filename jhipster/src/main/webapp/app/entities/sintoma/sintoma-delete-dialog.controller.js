(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('SintomaDeleteController',SintomaDeleteController);

    SintomaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sintoma'];

    function SintomaDeleteController($uibModalInstance, entity, Sintoma) {
        var vm = this;

        vm.sintoma = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sintoma.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
