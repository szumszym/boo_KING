angular.module('app')
    .factory('Hotels', function ($resource) {
        return $resource('/api/hotels/:id', {id: '@_id'}, {
            query: {
                method: 'GET',
                isArray: true,
                cancellable: true
            },
            update: {
                method: 'PUT'
            },
            byCity: {
                method: 'GET',
                url: '/api/hotels/search/byCity/:city',
                params: {city: '@_city'},
                isArray: true
            },
            cities: {
                method: 'GET',
                url: '/api/search/cities',
                isArray: true
            }
        });
    })
    .factory('Search', function ($resource) {
        return $resource('/api/search', {
            cities: {
                method: 'GET',
                url: '/api/search/cities',
                isArray: true
            }
        });
    });