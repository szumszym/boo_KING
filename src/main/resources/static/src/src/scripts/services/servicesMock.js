angular.module('app')
    .factory('Hotels', function ($resource, $location) {
        return $resource('data/mock/hotel/hotels.json', {id: '@_id'}, {
            get: {
                method: 'GET',
                url: 'data/mock/hotel/hotels.json',
                transformResponse: function (data) {
                    var hotels = angular.fromJson(data);
                    var hotelName = $location.path().replace('/hotel/', '');
                    return _.find(hotels, {name: hotelName});
                }
            },
            query: {
                method: 'GET',
                isArray: true,
                cancellable: true
            },
            update: {method: 'PUT'}
        });
    })
    .factory('Search', function ($resource) {
        return $resource('data/mock/', {}, {
            cities: {
                method: 'GET', url: 'data/mock/filters/filters.json', transformResponse: function (data, header) {
                    var filters = angular.fromJson(data);
                    return filters.cities;
                },
                isArray: true
            }
        });
    });