angular.module('app')
    .controller('HotelController', function ($scope, $stateParams, Hotels) {
        $scope.hotelName = $stateParams.name;
        var hotel = Hotels.get({name: $scope.hotelName}, function (hotel) {
            $scope.hotel = hotel;
            $scope.selectedFilters = $stateParams.filters;
            $scope.photos = $scope.hotel.photos;
        });
    });
