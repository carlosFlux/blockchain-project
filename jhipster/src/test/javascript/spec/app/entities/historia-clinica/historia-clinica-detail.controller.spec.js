'use strict';

describe('Controller Tests', function() {

    describe('HistoriaClinica Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockHistoriaClinica, MockMedico, MockInstitucion, MockPaciente, MockSintoma;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockHistoriaClinica = jasmine.createSpy('MockHistoriaClinica');
            MockMedico = jasmine.createSpy('MockMedico');
            MockInstitucion = jasmine.createSpy('MockInstitucion');
            MockPaciente = jasmine.createSpy('MockPaciente');
            MockSintoma = jasmine.createSpy('MockSintoma');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'HistoriaClinica': MockHistoriaClinica,
                'Medico': MockMedico,
                'Institucion': MockInstitucion,
                'Paciente': MockPaciente,
                'Sintoma': MockSintoma
            };
            createController = function() {
                $injector.get('$controller')("HistoriaClinicaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jappApp:historiaClinicaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
