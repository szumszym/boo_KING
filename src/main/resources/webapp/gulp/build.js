'use strict';

var gulp = require('gulp');

var paths = gulp.paths;
var MINIFY_ENABLED = true;

var del = require('del');
var gulpif = require('gulp-if');
var $ = require('gulp-load-plugins')({
    pattern: ['gulp-*', 'main-bower-files', 'uglify-save-license']
});

gulp.task('build', ['clean', 'html', 'images', 'fonts', 'misc']);
gulp.task('build-debug', ['clean-tmp', 'html-debug']);
gulp.task('build-dev', function () {
    MINIFY_ENABLED = false;
    gulp.start('build');
});


gulp.task('html', ['inject', 'partials'], function () {
    var partialsInjectFile = gulp.src(paths.tmp + '/partials/templateCacheHtml.js', {read: false});
    var partialsInjectOptions = {
        starttag: '<!-- inject:partials -->',
        ignorePath: paths.tmp + '/partials',
        addRootSlash: false
    };

    var htmlFilter = $.filter(['*.html', '!debug.html'], {restore: true});
    var jsFilter = $.filter('**/*.js', {restore: true});
    var cssFilter = $.filter('**/*.css', {restore: true});

    return gulp.src(paths.tmp + '/serve/*.html')
        .pipe($.inject(partialsInjectFile, partialsInjectOptions)) //inject HTML partials

        .pipe($.useref())//compiles <!-- build: --> tags from index.html
        .pipe($.preprocess())//compiles @if, @ifdef tags from HTML

        //JS
        .pipe(jsFilter)
        .pipe($.ngAnnotate())
        .pipe(gulpif(MINIFY_ENABLED, $.uglify({preserveComments: $.uglifySaveLicense})))
        .pipe($.rev())
        .pipe(jsFilter.restore)

        //CSS
        .pipe(cssFilter)
        .pipe(gulpif(MINIFY_ENABLED, $.csso({debug: true})))
        .pipe($.rev())
        .pipe(cssFilter.restore)

        //update css/js revision in HTML
        .pipe($.revReplace())

        //minify HTML
        .pipe(htmlFilter)
        .pipe(gulpif(MINIFY_ENABLED, $.htmlmin({collapseWhitespace: true, minifyCSS: true, minifyJS: true})))
        .pipe(htmlFilter.restore)

        .pipe(gulp.dest(paths.dist + '/'))
        .pipe($.size({title: paths.dist + '/', showFiles: true}));
});

gulp.task('html-debug', ['inject', 'partials'], function () {
    return gulp.src(paths.tmp + '/serve/index.html')
        .pipe($.preprocess({context: {DEBUG: true}}))
        .pipe($.rename('debug.html'))
        .pipe(gulp.dest(paths.src + '/'))
        .pipe($.size({title: paths.src + '/', showFiles: true}));
});

gulp.task('partials', function () {
    return gulp.src([
            paths.src + '/{app,components}/**/*.html',
            paths.tmp + '/{app,components}/**/*.html'
        ])
        .pipe($.htmlmin({collapseWhitespace: true, minifyCSS: true, minifyJS: true}))
        .pipe($.angularTemplatecache('templateCacheHtml.js', {
            module: 'app'
        }))
        .pipe(gulp.dest(paths.tmp + '/partials/'));
});

gulp.task('images', function () {
    return gulp.src(paths.src + '/assets/images/**/*')
        .pipe(gulp.dest(paths.dist + '/assets/images/'));
});

gulp.task('fonts', function () {
    return gulp.src($.mainBowerFiles())
        .pipe($.filter('**/*.{eot,svg,ttf,woff}'))
        .pipe($.flatten())
        .pipe(gulp.dest(paths.dist + '/fonts/'));
});

gulp.task('misc', function () {
    return gulp.src(paths.src + '/**/*.ico')
        .pipe(gulp.dest(paths.dist + '/'));
});


gulp.task('clean', function () {
    return del.sync([paths.dist + '/', paths.tmp + '/'], {force: true});
});

gulp.task('clean-tmp', function () {
    return del.sync([paths.tmp + '/'], {force: true});
});

