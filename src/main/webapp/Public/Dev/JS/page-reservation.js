;!function($) {

	if($('#page-reservation').ex().empty()) return;

	var $model = $('.model.model-reservation');
	$model.find('.model-remarks').each(function() {
        var $this = $(this);
        var CONST_OVERFLOW = 18;
        if($this.text().length > CONST_OVERFLOW) {
            $this.popover({
                html : true,
                placement : 'left',
                title : '备注详情',
                content : $(this).html(),
                trigger : 'hover'
            });
            $this.css('cursor', 'help');
        }
    });

    $model.find('.model-table .model-no').each(function(i) {
        $(this).text(i + 1);
    }); 

}(window.jQuery);