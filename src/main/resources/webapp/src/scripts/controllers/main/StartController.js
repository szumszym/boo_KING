angular.module('app')
    .controller('StartController', function ($scope, $state, $timeout, $mdDialog, Hotels, Search, utils) {

        var hotels = [];
        $scope.filters = {
            cities: [{value: null, name: 'All cities'}],
            rooms: utils.range(30, 1),
            adults: utils.range(20, 1),
            children: utils.range(20, 0)
        };

        $scope.loadCities = function () {
            var cityFilter = $scope.filters.cities;
            return !!cityFilter && cityFilter.length > 1 ? cityFilter : Search.cities(function (cities) {
                var parsedCities = _.map(cities, function (city) {
                    return {name: city, value: city}
                });
                $scope.filters.cities = $scope.filters.cities.concat(parsedCities);
            });
        };

        $scope.filtersChanged = function () {
            $scope.tiles = [];
            hotels.$cancelRequest();
            hotels = Hotels.query($scope.selectedFilters, hotelCallback);
        };


        var now = new Date();
        $scope.selectedFilters = {
            city: {value: null, name: 'All cities'},
            dates: {
                from: now,
                to: new Date(now.getFullYear(), now.getMonth(), now.getDate() + 7)
            },
            room: 1,
            adult: 1,
            child: 0
        };
        hotels = Hotels.query($scope.selectedFilters, hotelCallback);

        function hotelCallback(hotels) {
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
        }

        $scope.goToHotel = function (hotel) {
            $state.go('main.hotel', {name: hotel.name, hotel: hotel, filters: $scope.selectedFilters});
        };
        /*    $scope.openDetailsPopup = function (hotel) {
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
         };*/
    });
