'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

var $ = require('gulp-load-plugins')();

var wiredep = require('wiredep').stream;

gulp.task('inject', ['styles'], function () {

    var injectStyles = gulp.src([
        paths.src + '/styles/**/*.css'
    ], {read: false});

    var injectScripts = gulp.src([
            paths.src + '/scripts/**/*.js',
            (gulp.config.mock ? '!**/services.js' : '!**/servicesMock.js')
        ])
        .pipe($.angularFilesort());

    var injectOptions = {
        ignorePath: [paths.src, paths.tmp + '/serve'],
        addRootSlash: false
    };

    var wiredepOptions = {
        directory: 'bower_components',
        exclude: [/bootstrap\.css/, /foundation\.css/]
    };

    return gulp.src([paths.src + '/*.html', '!' + paths.src + '/debug.html'])
        .pipe($.inject(injectStyles, injectOptions))
        .pipe($.inject(injectScripts, injectOptions))
        .pipe(wiredep(wiredepOptions))
        .pipe(gulp.dest(paths.tmp + '/serve'));

});