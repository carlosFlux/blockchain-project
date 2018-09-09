(function() {
    'use strict';
    angular
        .module('jappApp')
        .factory('Institucion', Institucion);

    Institucion.$inject = ['$resource'];

    function Institucion ($resource) {
        var resourceUrl =  'api/institucions/:id';

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
