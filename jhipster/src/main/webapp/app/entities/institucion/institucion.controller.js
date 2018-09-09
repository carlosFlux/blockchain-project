(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('InstitucionController', InstitucionController);

    InstitucionController.$inject = ['Institucion'];

    function InstitucionController(Institucion) {

        var vm = this;

        vm.institucions = [];

        loadAll();

        function loadAll() {
            Institucion.query(function(result) {
                vm.institucions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
