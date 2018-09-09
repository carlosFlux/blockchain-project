(function() {
    'use strict';

    angular
        .module('jappApp')
        .controller('SintomaDetailController', SintomaDetailController);

    SintomaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sintoma', 'HistoriaClinica'];

    function SintomaDetailController($scope, $rootScope, $stateParams, previousState, entity, Sintoma, HistoriaClinica) {
        var vm = this;

        vm.sintoma = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jappApp:sintomaUpdate', function(event, result) {
            vm.sintoma = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
