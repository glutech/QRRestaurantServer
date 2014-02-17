;!function($) {

	var formSubmitOnIframeSupportor = function(form, callback) {

		var $form = $(form);
		
		$form.submit($.proxy(function() {

			var uid = '__FormSubmitOnIframe_' + new Date().getTime() + '_' + Math.floor(Math.random() * 99999999) + '__'
			  , $iframe = $("<iframe id='" + uid + "' src='about:blank' style='display:none;'></iframe>").appendTo('body');
			$form.attr('target', uid);

			// 当action跨域时，必须由远程服务器发起重定向请求至当前domain。
			// 否则跨域时无法取到iframe内的内容
			$iframe.load($.proxy(function(e) {
				try {
					callback(true, $iframe.contents().find('body').text());
				} catch(expt) {
					callback(false);
				} finally {
					$iframe.remove();
					delete $iframe;
					$form.removeAttr('target');
				}
			}, this));

		}, this));

	};

	var qiniuUploader = function(input, callback) {

		var $input = $(input);

		$.ajax({
		    type: "POST",
		    url: 'http://localhost:8080/qr_restaurant/User/PicUploaderGateway.do',
		    data: {}, // TODO: other data
		    //crossDomain: true, // TEST ONLY
		    success: function(token) {

				var $form = $(
					"<form method='post' action='http://up.qiniu.com' enctype='multipart/form-data' style='display:none'>" +
						"<input name='token' type='hidden' value='" + token + "' />" +
					"</form>").appendTo($('body'));
				
				// INPUT 控件无法设置值，而iframe提交后将重定向，控件不再存在
				// 固提交前复制一个包含空值的副本
				var $cloneInput = $input.clone(true);
				$input.hide();
				$input.after($cloneInput);
				$input.appendTo($form).attr('name', 'file');

				formSubmitOnIframeSupportor($form[0], function(status, result) {
					
					$form.remove();
					delete $form;

					if(status && (result = $.parseJSON(result))) {
						if(result['key']) {
							callback(true, result['key']);
						} else if(result['error']) {
							callback(false, result['error']);
						} else {
							callback(false);
						}
					} else {
						callback(false);
					}

				});

				$form.submit();
 		  	},
 		  	error: function(e) {
 		  		callback(false, e);
 		  	}
		});
	};


	($.fn.qiniuUploader) && $.error('$.fn.qiniuUploader was defined');

	$.fn.qiniuUploader = function(callback) {
		qiniuUploader(this, callback);
	}

}(window.jQuery);