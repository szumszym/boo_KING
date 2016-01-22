angular.module('app')
    .service('utils', function () {

        this.selectByObj = function (objArray, selectedObj, selectedProp) {
            selectedProp = selectedProp || 'selected';
            for (var i = 0; i < objArray.length; i++) {
                objArray[i][selectedProp] = objArray[i] === selectedObj;
            }
        }
    });
