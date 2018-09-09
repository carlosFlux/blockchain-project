(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('InstitucionDeleteController',InstitucionDeleteController);

    InstitucionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Institucion'];

    function InstitucionDeleteController($uibModalInstance, entity, Institucion) {
        var vm = this;

        vm.institucion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Institucion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
