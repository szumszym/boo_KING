'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

var $ = require('gulp-load-plugins')();
var gulpif = require('gulp-if');

gulp.task('styles', function () {

    var injectScssFiles = gulp.src([
        paths.src + '/app/**/*.scss',
        '!' + paths.src + '/app/app.scss'
    ], {read: false});

    var injectOptions = {
        transform: function (filePath) {
            filePath = filePath
                .replace(paths.src + '/app/', '')
                .replace('_', '')
                .replace('.scss', '');
            return '@import \'' + filePath + '\';';
        },
        starttag: '// injector',
        endtag: '// endinjector',
        addRootSlash: false
    };

    var handleError = function (err) {
        console.error(err.toString());
        this.emit('end');
    };

    var isDebug = gulp.config.env === 'debug';
    var dist = isDebug ? paths.src : paths.tmp + '/serve/styles/';
    return gulp.src(paths.src + '/app/app.scss')
        .pipe($.cssjoin())
        .pipe($.inject(injectScssFiles, injectOptions))
        .pipe($.sass({outputStyle: 'expanded'}).on('error', $.sass.logError))
        .pipe($.autoprefixer()).on('error', handleError)
        .pipe(gulpif(isDebug, $.rename('debug.css')))
        .pipe(gulp.dest(dist));
});
