var gulp = require('gulp');
var del = require('del');

gulp.task('copy-resources', function () {
    return gulp.src([
        'node_modules/**/jquery/dist/jquery.min.*',
        'node_modules/**/bootstrap/dist/**',
        'node_modules/**/bootstrap-icons/font/**',
        'node_modules/**/observable-slim/observable-slim.min.js'])
        .pipe(gulp.dest('../resources/static/lib_'));
});

gulp.task('clean', function () {
    return del(['../resources/static/lib_'], {force: true});
});

gulp.task('build', gulp.series('clean', 'copy-resources'));