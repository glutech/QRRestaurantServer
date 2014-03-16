;!function($) {

    if($('#page-restaurant-editor').ex().empty()) return;

    ;$(function() {

        var $form = $('form');

        $form.exValidate({
            rules : { 
                name : { required : true },
                desc : { required : true },
                type : { required : true },
                addr : { required : true },
                tel : { required : true }
            },
            messages : {
                name : '请输入餐厅名称',
                desc : '请输入餐厅描述',
                type : '请选择主营菜品',
                addr : '请输入餐厅地址',
                tel : '请输入联系电话'

            }
        });
    });

}(window.jQuery);