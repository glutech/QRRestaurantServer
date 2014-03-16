;!function($) {

    if($('#page-table-editor').ex().empty()) return;

    ;$(function() {

        var $form = $('form');

        $form.exValidate({
            rules : { 
                name : { required : true },
                type : { required : true },
                sort : { 
                    required : true,
                    digits : true,
                    min : 0,
                    max : 1024
                }
            },
            messages : {
                name : '请输入餐桌名称',
                type : '请输入餐桌类型',
                sort : '请输入正确的排序因子'

            }
        });
    });

}(window.jQuery);