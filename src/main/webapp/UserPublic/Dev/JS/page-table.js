;!function($) {

	if($('#page-table').ex().empty()) return;

	;$(function() {

		/**
		 * 动态修正餐桌名称过长带来的影响
		 */

		;!function() {
			var coreHeight = function($element) {
				return $element.innerHeight() - parseFloat($element.css('padding-top')) - parseFloat($element.css('padding-bottom'));
			};
			var resizeNameElement = function($element, $root, maxHeight) {
				// 1. 尽可能缩小字体
				var sourceFontsize = $element.css('font-size');
				do {
					$element.css('font-size', parseFloat(sourceFontsize) * 0.9 + 'px');
					var currentFontsize = $element.css('font-size');
					if(currentFontsize == sourceFontsize) {
						// browser(like chrome) force the minimum font-size value
						window.console && !console.log('can not set smaller font-size: ') && !console.log($element);
						break;
					}
					sourceFontsize = currentFontsize;
				} while(coreHeight($element) > maxHeight);
				// 2. 优化样式
				if(parseFloat(sourceFontsize) < 15) {
					$element.css('font-weight', 'normal').css('text-shadow', 'none')
						    .addClass('style-font-sans');
					$root.find('> .model-icon').hide();
					$root.find('> .model-status').hide();
				}
			};
			var moveMaskElement = function($root) { 
				$root.find('> .model-icon').css('left', 0).css('top', 0);
				$root.find('> .model-status').css('bottom', 0);
			};
			var CONST_OVERVIEW_HEIGHT = 110;
			var CONST_OFFSET_HEIGHT = 20;
			$('div.model.model-table-map > ul.model-list > li.model-item > a.model-module > span.model-name').each(function() {
				var $span = $(this)
				  , $a = $span.closest('a.model-module')
				  , span_coreHeight = coreHeight($span);
			  	if(span_coreHeight >= CONST_OVERVIEW_HEIGHT) {
			  		if(span_coreHeight - CONST_OVERVIEW_HEIGHT > CONST_OFFSET_HEIGHT) {
			  			resizeNameElement($span, $a, CONST_OVERVIEW_HEIGHT);
			  		}
			  		moveMaskElement($a);
			  	}
			});

		}();

	});

}(window.jQuery);