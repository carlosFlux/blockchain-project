(function() {
    'use strict';
    angular
        .module('jappApp')
        .factory('Sintoma', Sintoma);

    Sintoma.$inject = ['$resource'];

    function Sintoma ($resource) {
        var resourceUrl =  'api/sintomas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
