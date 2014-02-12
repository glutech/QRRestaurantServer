;+function($) {

	$.fn.exValidate = function(options) {

        var $this = $(this);

        !$this.is('FORM') && $.error('must called on a form element');
        !$this.is('.module-exValidate') && $.error('must called on .module-exValidate');

        var $errorContainer = $('<div class="alert alert-warning fade in" style="display:none;"><button type="button" class="close" aria-hidden="true">×</button></div>')
            .prependTo($this);
        $errorContainer.find('button').on('click', function() {
                $(this).closest('.alert').hide();
            });

        var uuid = '_exValidate_' + new Date().getTime() + '_' + Math.floor(Math.random() * 999999) + '_';
        $this.addClass(uuid);

        $this.validate($.extend({
            errorContainer : '.' + uuid + ' > .alert',
            errorLabelContainer : '.' + uuid + ' > .alert',
            wrapper : ""
        }, options));

        return this;
    };

}(window.jQuery);