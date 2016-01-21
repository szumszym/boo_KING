angular.module('app')
    .factory('Hotels', function ($resource) {
        return $resource('data/mock/hotel/hotels.json');
    });