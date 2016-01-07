angular.module('boo_KING', ['ngRoute', 'ngMaterial', 'ngFileUpload', 'ngImgCrop'])
    .controller("UploadController", function ($scope, Upload, $timeout) {
        $scope.upload = function (type, dataUrl) {
            Upload.upload({
                url: '/api/upload',
                data: {
                    type: type,
                    file: Upload.dataUrltoBlob(dataUrl)
                },
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
    });