(function() {
    'use strict';

    angular
        .module('visualizer2App')
        .controller('GraphDialogController', GraphDialogController);

    GraphDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Graph', 'User'];

    function GraphDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Graph, User) {
        var vm = this;

        vm.graph = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.graph.id !== null) {
                Graph.update(vm.graph, onSaveSuccess, onSaveError);
            } else {
                Graph.save(vm.graph, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('visualizer2App:graphUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
