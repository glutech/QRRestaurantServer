;!function($) {

	if($('#page-dish-list').ex().empty()) return;

	;$(function() {

		var Selector = function(root) {
			this.$root = $(root);
			this.$form = this.$root.closest('form');
			this.$action = this.$root.find('> a.dropdown-toggle');
			this.$options = this.$root.find('> ul.dropdown-menu');
			this.$value = $("<input type='hidden' name='" + this.$options.data('key') + "' />").appendTo(this.$root);
			this.init();
		};

		Selector.prototype = {

			init : function() {
				var $active = this.$options.find('a[data-value=' + this.$options.data('value') + ']');
				$active.closest('li').addClass('active');
				this.$value.val($active.data('value'));
				this.$action.find('span.caret').before('<small> (' + $active.contents().get(0).textContent + ')</small>');
				this.$options.find('a').on('click', $.proxy(this.onOptionClick, this));
			},

			onOptionClick : function(e) {
				var $a = $(e.currentTarget);
				if($a.data('value') != this.$options.data('value')) {
					this.$value.val($a.data('value'));
					this.$form.submit();
				}
				return false;
			}

		};

		$('#page-dish-list').find('form').find('li.dropdown').each(function() {
			new Selector(this);
		});

	});

}(window.jQuery);