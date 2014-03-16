;!function($) {

    // <a href="<href>" data-ajaxtodo="true" data-ajaxtodo-warning="<warning>">
    $('a[data-ajaxtodo=true]').each(function() {
        var $this = $(this);
        $this.on('click', function() {
            var href = $this.attr('href');
            if(!href) return;
            var warning = $this.data('ajaxtodoWarning');
            if(!warning || confirm(warning)) {
                $.maskLoading('setText', '提交中...').maskLoading('show');
                $.post(href, {}, function(r) {
                    r['message'] && !!alert(r['message']);
                    if(r['reload']) {
                        window.location.replace(window.location);
                    } else {
                        if(r['redirectUrl']) {
                            if(r['redirectReplace']) {
                                window.location.replace(r['redirectUrl']);
                            } else {
                                window.location = r['redirectUrl'];
                            }
                        }
                    }
                    $.maskLoading('hide');
                });
            }
            return false;
        });
    });

}(window.jQuery);