'use strict';

var gulp = require('gulp');
var $ = require('gulp-load-plugins')({pattern: ['gulp-*', 'main-bower-files', 'uglify-save-license', 'wiredep']});

gulp.task('compile-all', ['compile-html'], function () {
    var htmlFilter = $.filter(['*.html', '!index_src.html'], {restore: true});
    var jsFilter = $.filter('**/*.js', {restore: true});
    var cssFilter = $.filter('**/*.css', {restore: true});

    return gulp.src(gulp.paths.src + '/index.html')
        .pipe($.useref())//compiles <!-- build: --> tags

        //JS
        .pipe(jsFilter)
        .pipe($.ngAnnotate())
        .pipe($.if(gulp.config.minify, $.uglify({preserveComments: $.uglifySaveLicense})))
        .pipe($.rev())
        .pipe(jsFilter.restore)

        //CSS
        .pipe(cssFilter)
        .pipe($.if(gulp.config.minify, $.csso()))
        .pipe($.rev())
        .pipe(cssFilter.restore)

        //update css/js revision in HTML
        .pipe($.revReplace())

        //minify HTML
        .pipe(htmlFilter)
        .pipe($.if(gulp.config.minify, $.htmlmin({collapseWhitespace: true, minifyCSS: true, minifyJS: true})))
        .pipe(htmlFilter.restore)

        .pipe(gulp.dest(gulp.paths.dist + '/'));
});

gulp.task('compile-html', ['compile-styles', 'compile-templates'], function () {

    var injectStyles = gulp.src([
        gulp.paths.src + '/styles/**/*.css'
    ], {read: false});

    var injectScripts = gulp.src([gulp.paths.src + '/scripts/**/*.js',
        (gulp.config.mock ? '!**/services.js' : '!**/servicesMock.js'),
        gulp.paths.src + (gulp.config.debug ? '!' : '') + '/scripts/templates.js'
    ]).pipe($.angularFilesort());

    var injectOptions = {
        ignorePath: [gulp.paths.src],
        addRootSlash: false
    };

    var wiredepOptions = {
        directory: 'bower_components'/*,exclude: [/bootstrap\.css/, /foundation\.css/]*/
    };


    return gulp.src(gulp.paths.src + '/index_src.html')
        .pipe($.inject(injectStyles, injectOptions))
        .pipe($.inject(injectScripts, injectOptions))
        .pipe($.wiredep.stream(wiredepOptions))
        .pipe($.rename('index.html'))
        .pipe(gulp.dest(gulp.paths.src));
});

gulp.task('compile-templates', function () {
    return gulp.config.debug ? null : gulp.src([
            gulp.paths.src + '/{scripts,views}/**/*.html'
        ])
        .pipe($.htmlmin({collapseWhitespace: true, minifyCSS: true, minifyJS: true}))
        .pipe($.angularTemplatecache('templates.js', {
            module: 'app'
        }))
        .pipe(gulp.dest(gulp.paths.src + '/scripts/'));
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