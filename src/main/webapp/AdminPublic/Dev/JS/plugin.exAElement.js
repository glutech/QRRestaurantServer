;+function($) {

	$('a[data-exaele]').each(function() {
		var $this = $(this);
		var action = $(this).data('exaele');
		if(action == 'goback') {
			$this.on('click', function() {
				history.go(-1);
				return false;
			})
		}
	});

}(window.jQuery);