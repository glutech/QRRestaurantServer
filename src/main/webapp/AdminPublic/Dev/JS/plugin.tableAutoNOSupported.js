;!function($) {

    // <table data-tableautono="true" data-tableautono-class="<class>">
    $('table[data-tableautono=true]').each(function() {
        var $this = $(this);
        var fieldClass = $this.data('tableautonoClass');
        $this.find('tbody .' + fieldClass).each(function(i) {
            $(this).text(i + 1);
        });
    });

}(window.jQuery);