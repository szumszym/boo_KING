'use strict';

var gulp = require('gulp');
var $ = require('gulp-load-plugins')({pattern: ['gulp-filter', 'gulp-flatten', 'main-bower-files']});

gulp.task('copy-all', ['copy-images', 'copy-fonts', 'copy-misc', 'copy-data']);

gulp.task('copy-images', function () {
    return gulp.src(gulp.paths.src + '/assets/images/**/*')
        .pipe(gulp.dest(gulp.paths.dist + '/assets/images/'));
});

gulp.task('copy-fonts', function () {
    return gulp.src($.mainBowerFiles())
        .pipe($.filter('**/*.{eot,svg,ttf,woff}'))
        .pipe($.flatten())
        .pipe(gulp.dest(gulp.paths.dist + '/fonts/'));
});

gulp.task('copy-misc', function () {
    return gulp.src([gulp.paths.src + '/**/*.ico', gulp.paths.src + '/404.html'])
        .pipe(gulp.dest(gulp.paths.dist + '/'));
});

gulp.task('copy-data', function () {
    var src = [gulp.paths.src + '/data/**'];
    if (!gulp.config.mock) {
        src.push('!' + gulp.paths.src + '/data/mock/**');
        src.push('!' + gulp.paths.src + '/data/mock');
    }
    return gulp.src(src)
        .pipe(gulp.dest(gulp.paths.dist + '/data/'));
});