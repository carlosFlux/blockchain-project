(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('HistoriaClinicaController', HistoriaClinicaController);

    HistoriaClinicaController.$inject = ['HistoriaClinica'];

    function HistoriaClinicaController(HistoriaClinica) {

        var vm = this;

        vm.historiaClinicas = [];

        loadAll();

        function loadAll() {
            HistoriaClinica.query(function(result) {
                vm.historiaClinicas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
