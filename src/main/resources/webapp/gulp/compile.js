'use strict';

var gulp = require('gulp');

var $ = require('gulp-load-plugins')();

gulp.task('compile-templates', function () {
    return gulp.src([
            gulp.paths.src + '/views/**/*.html'
        ])
        .pipe($.htmlmin({collapseWhitespace: true, minifyCSS: true, minifyJS: true}))
        .pipe($.angularTemplatecache('templates.js', {
            module: 'app',
            root: 'views/'
        }))
        .pipe(gulp.dest(gulp.paths.src + '/views/'));
});


gulp.task('compile-styles', function () {
    var handleError = function (err) {
        console.error(err.toString());
        this.emit('end');
    };
    return gulp.src(gulp.paths.src + '/styles/**/*.scss')
        .pipe($.sass.sync().on('error', $.sass.logError))
        .pipe($.autoprefixer()).on('error', handleError)
        .pipe(gulp.dest(gulp.paths.src + '/styles/'));

});