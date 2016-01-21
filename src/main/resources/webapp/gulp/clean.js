'use strict';

var gulp = require('gulp');
var del = require('del');

gulp.task('clean', ['clean-tmp'], function () {
    return del.sync([gulp.paths.dist + '/'], {force: true});
});

gulp.task('clean-tmp', function () {
    return del.sync([gulp.paths.tmp + '/', gulp.paths.src + '/styles/**/*.css', gulp.paths.src + '/views/templates.js', gulp.paths.src + '/debug.html'], {force: true});
});