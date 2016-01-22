angular
    .module('app')
    .directive('carousel', function carouselDrective(utils) {
        return {
            restrict: 'E',
            scope: {
                slides: '=',
                interval: '@',
                thumbs: '=',
                caption: '@'
            },
            templateUrl: 'scripts/directives/carousel.html',
            link: function (scope, element, attrs) {
                scope.interval = scope.interval || 4000;

                scope.changeSelected = function (slide) {
                    utils.selectByObj(scope.slides, slide);
                }
            }
        }
    });