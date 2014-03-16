;!function($) {

    if($('#page-restdishcat-editor').ex().empty()) return;

    ;$(function() {

        $('form').exValidate({
            rules : { 
                name : { required : true }
            },
            messages : {
                name : '请输入菜品分类名称'

            }
        });
    });

}(window.jQuery);