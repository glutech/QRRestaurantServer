;!function($) {

	if($.fn.ex) {
		$.error('jQuery.fn.ex was defined');
		return;
	}

	$.fn.ex = function() {
		var $this = this;
		return {
			empty: function() {
				return $this.length == 0;
			},

			size: function(p1, p2) {
				switch(arguments.length) {
					case 0: return [
								$this.width(), $this.height()
							];
					case 1: {
						$this.width(p1[0]); $this.height(p1[1]);
						return $this;
					}
					case 2: {
						$this.width(p1); $this.height(p2);
						return $this;
					}

				}
			}
		};
	};

	$('[data-action=goback]').on('click', function() {
		history.go(-1);
		return false;
	});

	$('[data-action=disabled]').on('click', function() {
		return false;
	});

	$('.modal[data-action=loading]').on('show.bs.modal', function() {
		$.maskLoading('setText', '加载中...').maskLoading('show');
	}).on('shown.bs.modal', function() {
		$.maskLoading('hide');
	});

}(window.jQuery);