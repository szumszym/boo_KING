(function () {

    angular
        .module('app')
        .controller('ProfileController', ['$scope', 'Upload', '$timeout', function ($scope, Upload, $timeout) {
            var vm = this;

            vm.user = {
                title: 'Admin',
                email: 'contact@flatlogic.com',
                firstName: '',
                lastName: '',
                company: 'FlatLogic Inc.',
                address: 'Fabritsiusa str, 4',
                city: 'Minsk',
                state: '',
                biography: 'We are young and ambitious full service design and technology company. ' +
                'Our focus is JavaScript development and User Interface design.',
                postalCode: '220007'
            };

            $scope.upload = function (type, dataUrl) {
                Upload.upload({
                    url: '/api/upload',
                    data: {
                        type: type,
                        file: Upload.dataUrltoBlob(dataUrl)
                    }
                }).then(function (response) {
                    $timeout(function () {
                        $scope.result = response.data;
                    });
                }, function (response) {
                    if (response.status > 0) $scope.errorMsg = response.status
                        + ': ' + response.data;
                }, function (evt) {
                    $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
                });
            }
        }]);

})();
