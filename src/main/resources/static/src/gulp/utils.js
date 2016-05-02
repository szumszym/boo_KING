'use strict';

var gulp = require('gulp');
var del = require('del');
var size = require('gulp-size');

gulp.task('utils-clean', ['utils-clean-tmp'], function () {
    return del.sync([gulp.paths.dist + '/'], {force: true});
});

gulp.task('utils-clean-tmp', function () {
    return del.sync([gulp.paths.src + '/styles/**/*.css', gulp.paths.src + '/scripts/templates.js', gulp.paths.src + '/index.html'], {force: true});
});

gulp.task('utils-size', function () {
    var src = gulp.config.debug ?
        [gulp.paths.src + '/**/*', '!' + gulp.paths.src + '/styles/**/*.css', '!' + gulp.paths.src + '/scripts/templates.js', '!' + gulp.paths.src + '/index.html'] :
        [gulp.paths.dist + '/**/*'];
    return gulp.src(src).pipe(size({showFiles: true}))
});