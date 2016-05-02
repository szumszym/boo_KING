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
                scope.interval = scope.interval || 5000;

                scope.changeSelected = function (slide) {
                    utils.selectByObj(scope.slides, slide);
                };

                scope.$watch(function () {
                    return element.find('.carousel').width()
                }, function (carouselWidth) {
                    scope.thumbHeight = carouselWidth / 10;
                }, true);

                angular.element(window).on("resize", function () {
                    scope.$apply();
                });
            }
        }
    });