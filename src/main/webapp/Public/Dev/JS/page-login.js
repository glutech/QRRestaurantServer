;!function($) {
	
	if($('#page-login').ex().empty()) return;

	;$(function() {

		var $aVerifycode = $('.model-verifycode')
		  , $imgVerifycode = $aVerifycode.find('> img').first();
		
		var boxSize = $aVerifycode.closest('.input-group-btn').ex().size()
		  , imgSize = $imgVerifycode.ex().size()
		  , newSize = [imgSize[0] / imgSize[1] * boxSize[1], boxSize[1]];

		$imgVerifycode.ex().size(newSize);

		$aVerifycode.on('click', function(e) {
			$imgVerifycode.attr('src', $imgVerifycode.attr('src'));
			return false;
		});

		$('form').exValidate({
            rules : {
                username: { required : true },
                password : { required : true },
                verifycode : { required : true, rangelength : [4,4] }
            },
            messages : {
                username : '请输入用户名',
                password : '请输入密码',
                verifycode : { 
                    required : '请输入验证码',
                    rangelength : '请输入4位验证码'
                }
            },
        });

	});
	

}(window.jQuery);