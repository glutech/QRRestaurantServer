;!function($) {

	if($('#page-table-list').ex().empty()) return;

	;$(function() {

		var $model = $('.model-table-list')
		  , $module = $model.find('.model-module');

		$module.find('.model-no').each(function(i) {
			$(this).text(i + 1);
		});

		$module.not('.model-module-free').find('.model-action a')
			.addClass('disabled').removeAttr('href')
			.attr('title', '不能在餐桌处于工作状态时操作')
			.on('click', function() { return false; });

	});

}(window.jQuery);