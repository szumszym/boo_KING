'use strict';

var gulp = require('gulp');

gulp.paths = {
    src: 'src',
    dist: '../static',
    tmp: '.tmp'
};

gulp.task('config-prod', function () {
    gulp.config = {
        env: 'prod',
        mock: false,
        minify: true
    }
});

gulp.task('config-dev', function () {
    gulp.config = {
        env: 'dev',
        mock: false,
        minify: false
    }
});

gulp.task('config-debug', function () {
    gulp.config = {
        env: 'debug',
        mock: true,
        minify: false
    }
});