(function() {
    'use strict';
    angular
        .module('jappApp')
        .factory('HistoriaClinica', HistoriaClinica);

    HistoriaClinica.$inject = ['$resource'];

    function HistoriaClinica ($resource) {
        var resourceUrl =  'api/historia-clinicas/:id';

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
