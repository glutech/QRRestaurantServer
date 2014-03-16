;!function($) {

    // <form action=<action> method="post" data-ajaxform="true">
    // call this function to return a submitHandler function
    // @see plugin.exValidate

    $.ajaxFormSupported && $.error('$.ajaxFormSupported was defined');

    $.ajaxFormSupported = function(form) {
        
        var $form = $(form)
          , method = $form.attr('method')
          , action = $form.attr('action');
        
        !$form.is('FORM') && $.error('must called on a form element');
        (!method || method!= 'post') && $.error('must request on post method');
        (!action || action =='#') && $.error('must request to action attr');

        // @see validate.submitHandler
        return function() {
            $.maskLoading('setText', '提交中...').maskLoading('show');
            $.post(action, $form.serialize(), function(r) {
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
            return false;
        };

    }

}(window.jQuery);