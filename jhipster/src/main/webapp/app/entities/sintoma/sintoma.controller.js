(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('SintomaController', SintomaController);

    SintomaController.$inject = ['Sintoma'];

    function SintomaController(Sintoma) {

        var vm = this;

        vm.sintomas = [];

        loadAll();

        function loadAll() {
            Sintoma.query(function(result) {
                vm.sintomas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
