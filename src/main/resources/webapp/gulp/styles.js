'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

var $ = require('gulp-load-plugins')();

gulp.task('styles', function () {
    var handleError = function (err) {
        console.error(err.toString());
        this.emit('end');
    };
    return gulp.src(paths.src + '/styles/**/*.scss')
        .pipe($.sass.sync().on('error', $.sass.logError))
        .pipe($.autoprefixer()).on('error', handleError)
        .pipe(gulp.dest(paths.src + '/styles/'));

});
