angular.module('app')
    .factory('Hotels', function ($resource) {
        return $resource('/api/hotels/:id', {id: '@_id'}, {
            update: {method: 'PUT'}
        });
    });