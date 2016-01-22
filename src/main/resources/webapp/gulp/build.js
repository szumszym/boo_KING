'use strict';

var gulp = require('gulp');

gulp.task('build', ['config-prod', 'utils-clean', 'compile-all', 'copy-all'], function () {
    gulp.start('utils-clean-tmp');
    gulp.start('utils-size')
});
gulp.task('build-dev', ['config-dev', 'utils-clean', 'compile-all', 'copy-all'], function () {
    gulp.start('utils-clean-tmp');
    gulp.start('utils-size')
});
gulp.task('build-dev-mock', ['config-dev-mock', 'utils-clean', 'compile-all', 'copy-all'], function () {
    gulp.start('utils-clean-tmp');
    gulp.start('utils-size')
});
gulp.task('build-debug', ['config-debug', 'utils-clean-tmp', 'compile-html'], function () {
    gulp.start('utils-size')
});

