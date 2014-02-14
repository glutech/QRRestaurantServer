;!function($) {

	if($('#page-category-list').ex().empty()) return;

	;$(function() {

		var $model = $('.model-category-list')
		  , $module = $model.find('.model-module');

		$module.find('.model-no').each(function(i) {
			$(this).text(i + 1);
		});

	});

}(window.jQuery);