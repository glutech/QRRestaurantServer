// @koala-prepend "fn.ex.js"
// @koala-prepend "plugin.qiniuUploader.js"
// @koala-prepend "plugin.maskLoading.js"
// @koala-prepend "plugin.exValidate.js"
// @koala-prepend "plugin.tableAutoNOSupported.js"
// @koala-prepend "plugin.ajaxTodoSupported.js"
// @koala-prepend "plugin.ajaxFormSupported.js"
// @koala-prepend "plugin.exAElement.js"
// @koala-prepend "page-login.js"
// @koala-prepend "page-table.js"
// @koala-prepend "section-table-status.js"
// @koala-prepend "page-reservation.js"
// @koala-prepend "section-reservation-editor.js"
// @koala-prepend "page-restaurant-editor.js"
// @koala-prepend "page-table-editor.js"
// @koala-prepend "page-dish-list.js"
// @koala-prepend "page-dish-editor.js"
// @koala-prepend "page-restdishcat-editor.js"

;!function($) {

    ;$(function() {

        /**
         * 为通知的bell标志赋予动画效果
         */
        ;!function() {
            var $root = $('.navbar-fixed-top .navbar-right > li:first > a:first')
              , $icon = $root.find('> .glyphicon')
              , $count = $root.find('> .badge');
            var intervalFunc = function() {
                if($count.is(':visible')) {
                    $icon.css('color', '#e74c3c')
                         .fadeTo(200, 0.618, function() {
                            $icon.fadeTo(400, 1);
                         });
                } else {
                    $icon.css('color', '');
                }
            };                                                                                                                                                                      
            $icon.data('animateIntervalResult', setInterval(intervalFunc, 1300));
            intervalFunc();
        }();
        

    });

}(window.jQuery);