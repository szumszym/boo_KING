angular.module('app')
    .controller('HotelController', function ($scope, $stateParams) {
        $scope.hotel = $stateParams.hotel;
        $scope.photos = $scope.hotel.photos;
    });
