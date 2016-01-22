angular.module('app')
    .controller('StartController', function ($scope, $state, $timeout, $mdDialog, Hotels) {

        $scope.range = function (count) {
            var ratings = [];
            for (var i = 0; i < count; i++) {
                ratings.push(i)
            }
            return ratings;
        };

        $scope.city = null;
        $scope.adult = null;
        $scope.child = null;
        $scope.room = null;

        $scope.cities = null;
        $scope.adults = $scope.range(30);
        $scope.children = $scope.range(30);
        $scope.rooms = $scope.range(20);

        $scope.loadCities = function () {
            /*TODO: load from service*/
            return $scope.cities || $timeout(function () {
                    $scope.cities = [
                        {id: 0, name: 'All cities'},
                        {id: 1, name: 'Kraków'},
                        {id: 2, name: 'Wrocław'},
                        {id: 3, name: 'Warszawa'},
                        {id: 4, name: 'Poznań'},
                        {id: 5, name: 'Łódź'}
                    ];
                }, 650);
        };

        $scope.minDate = new Date();
        $scope.checkinDate = new Date();
        $scope.checkoutDate = new Date(
            $scope.checkinDate.getFullYear(),
            $scope.checkinDate.getMonth(),
            $scope.checkinDate.getDate() + 7
        );

        Hotels.query(function (hotels) {
            var tiles = [];
            for (var i = 0; i < hotels.length; i++) {
                var hotel = hotels[i];
                tiles.push({
                    hotel: hotel,
                    colspan: Math.max(hotel.stars - 2, 1),
                    rowspan: Math.max(hotel.stars - 1, 1)
                });
            }
            $scope.tiles = tiles;
        });

        $scope.goToHotel = function (hotel) {
            $state.go('main.hotel', {hotel: hotel}, {location: false});
        };
        $scope.openDetailsPopup = function (hotel) {
            $mdDialog
                .show($mdDialog.confirm({
                    title: hotel.name,
                    content: hotel.description,
                    ok: 'Select room',
                    cancel: 'Close'
                }))
                .finally(function () {
                    alert = undefined;
                });
        };
    });
