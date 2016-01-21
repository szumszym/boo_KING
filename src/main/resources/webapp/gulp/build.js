'use strict';

var gulp = require('gulp');
var gulpif = require('gulp-if');
var wiredep = require('wiredep').stream;
var $ = require('gulp-load-plugins')({pattern: ['gulp-*', 'main-bower-files', 'uglify-save-license']});

gulp.task('build', ['config-prod', 'clean', 'html', 'copy-images', 'copy-fonts', 'copy-misc', 'copy-data']);
gulp.task('build-dev', ['config-dev', 'clean', 'html', 'copy-images', 'copy-fonts', 'copy-misc', 'copy-data']);
gulp.task('build-debug', ['config-debug', 'clean-tmp', 'html-debug']);

gulp.task('html', ['inject'], function () {
    var htmlFilter = $.filter(['*.html', '!debug.html'], {restore: true});
    var jsFilter = $.filter('**/*.js', {restore: true});
    var cssFilter = $.filter('**/*.css', {restore: true});

    return gulp.src(gulp.paths.tmp + '/serve/*.html')
        .pipe($.useref())//compiles <!-- build: --> tags from index.html

        //JS
        .pipe(jsFilter)
        .pipe($.ngAnnotate())
        .pipe(gulpif(gulp.config.minify, $.uglify({preserveComments: $.uglifySaveLicense})))
        .pipe($.rev())
        .pipe(jsFilter.restore)

        //CSS
        .pipe(cssFilter)
        .pipe(gulpif(gulp.config.minify, $.csso({debug: true})))
        .pipe($.rev())
        .pipe(cssFilter.restore)

        //update css/js revision in HTML
        .pipe($.revReplace())

        //minify HTML
        .pipe(htmlFilter)
        .pipe(gulpif(gulp.config.minify, $.htmlmin({collapseWhitespace: true, minifyCSS: true, minifyJS: true})))
        .pipe(htmlFilter.restore)

        .pipe(gulp.dest(gulp.paths.dist + '/'))
        .pipe($.size({title: gulp.paths.dist + '/', showFiles: true}));
});

gulp.task('html-debug', ['inject'], function () {
    return gulp.src(gulp.paths.tmp + '/serve/index.html')
        .pipe($.rename('debug.html'))
        .pipe(gulp.dest(gulp.paths.src + '/'))
        .pipe($.size({title: gulp.paths.src + '/', showFiles: true}));
});

gulp.task('inject', ['compile-styles', 'compile-templates'], function () {

    var injectStyles = gulp.src([
        gulp.paths.src + '/styles/**/*.css'
    ], {read: false});

    var injectScripts = gulp.src([
            gulp.paths.src + '/scripts/**/*.js',
            gulp.paths.src + '/views/templates.js',
            (gulp.config.mock ? '!**/services.js' : '!**/servicesMock.js')
        ])
        .pipe($.angularFilesort());

    var injectTemplates = gulp.src(gulp.paths.src + '/views/templates.js', {read: false});

    var injectOptions = {
        ignorePath: [gulp.paths.src],
        addRootSlash: false
    };

    var injectTemplatesOptions = {
        starttag: '<!-- inject:templates -->',
        addRootSlash: false
    };

    var wiredepOptions = {
        directory: 'bower_components',
        exclude: [/bootstrap\.css/, /foundation\.css/]
    };


    return gulp.src([gulp.paths.src + '/*.html', '!' + gulp.paths.src + '/debug.html'])
        .pipe(gulpif(gulp.config.env !== 'debug', $.inject(injectTemplates, injectTemplatesOptions)))
        .pipe($.inject(injectStyles, injectOptions))
        .pipe($.inject(injectScripts, injectOptions))
        .pipe(wiredep(wiredepOptions))
        .pipe(gulp.dest(gulp.paths.tmp + '/serve'));

});