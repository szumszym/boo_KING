angular.module('app')
    .service('utils', function () {

        this.selectByObj = function (objArray, selectedObj, selectedProp) {
            selectedProp = selectedProp || 'selected';
            for (var i = 0; i < objArray.length; i++) {
                objArray[i][selectedProp] = objArray[i] === selectedObj;
            }
        };

        this.range = function (count, start) {
            start = start || 0;
            var range = [];
            for (var i = 0 + start; i < count + start; i++) {
                range.push(i)
            }
            return range;
        };
    });
