'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

var $ = require('gulp-load-plugins')();

gulp.task('styles', function () {

    var sassOptions = {
        outputStyle: 'expanded'
    };

    var injectFiles = gulp.src([
        paths.src + '/{app,components}/**/*.scss',
        '!' + paths.src + '/app/app.scss',
        '!' + paths.src + '/app/vendor.scss'
    ], {read: false});

    var injectOptions = {
        transform: function (filePath) {
            filePath = filePath.replace(paths.src + '/app/', '');
            filePath = filePath.replace(paths.src + '/components/', '../components/');
            filePath = filePath.replace('_', '');
            filePath = filePath.replace('.scss', '');
            return '@import \'' + filePath + '\';';
        },
        starttag: '// injector',
        endtag: '// endinjector',
        addRootSlash: false
    };

    var appFilter = $.filter('app.scss');

    return gulp.src([
            paths.src + '/app/app.scss',
            paths.src + '/app/vendor.scss'
        ])
        .pipe($.cssjoin())
        .pipe(appFilter)
        .pipe($.inject(injectFiles, injectOptions))
        .pipe(appFilter.restore())
        .pipe($.sass(sassOptions).on('error', $.sass.logError))
        .pipe($.autoprefixer())
        .on('error', function handleError(err) {
            console.error(err.toString());
            this.emit('end');
        })
        .pipe(gulp.dest(paths.tmp + '/serve/styles/'));
});
