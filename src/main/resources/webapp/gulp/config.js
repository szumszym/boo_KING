'use strict';

var gulp = require('gulp');

gulp.paths = {
    src: 'src',
    dist: '../static'
};

gulp.task('config-prod', function () {
    gulp.config = {
        debug: false,
        mock: false,
        minify: true
    }
});

gulp.task('config-dev', function () {
    gulp.config = {
        debug: false,
        mock: false,
        minify: false
    }
});
gulp.task('config-dev-mock', function () {
    gulp.config = {
        debug: false,
        mock: true,
        minify: false
    }
});

gulp.task('config-debug', function () {
    gulp.config = {
        debug: true,
        mock: true,
        minify: false
    }
});