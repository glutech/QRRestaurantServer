;!function($) {

    var MaskLoading = function(element, options) {
        this.element = $(element);
        this.options = options;
        this.component = $(MaskLoading.template).hide().appendTo(this.element);
        this.setText();
        this.setImage();
    };

    MaskLoading.template = function() {
        return [
            "<div class='plugin plugin-maskloading-container'>",
                "<div class='plugin-maskloading'>",
                    "<img class='plugin-img' />",
                    "<span class='plugin-span' />",
                "</div>",
            "</div>"
        ].join('');
    }();

    MaskLoading.prototype = {

        setText: function(text) {
            this.component.find('.plugin-span').text(arguments.length ? text : this.options.text);
        },

        setImage: function(img) {
            this.component.find('.plugin-img').attr('src', arguments.length ? img : this.options.img);
        },

        show : function() {
            this.component.show();
        },

        hide: function() {
            //this.component.hide();
            this.component.fadeOut('fast');
        }

    };

    $.maskLoading && $.error('$.maskLoading was defined');

    $.maskLoading = function(action, param) {
        var REF_NAME = '__MASK_LOADING__'
          , $dom = $('body')
          , obj = $dom.data(REF_NAME);
        if(!obj) {
            obj = new MaskLoading($dom[0], $.maskLoading.defaults);
            $dom.data(REF_NAME, obj);
        }
        typeof action == 'string' && obj[action](param);
        return this;
    };

    $.maskLoading.defaults = {
        text : '加载中...',
        img : './../UserPublic/Img/loading.gif'
    };

}(window.jQuery);