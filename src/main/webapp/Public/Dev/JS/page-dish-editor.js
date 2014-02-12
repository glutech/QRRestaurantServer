;!function($) {

	if($('#page-dish-editor').ex().empty()) return;
    
	;$(function() {
        
		var $uploadView = $('.model-upload').first()
          , $uploadBtn = $uploadView.find('button').first()
          , $uploadFile = $uploadView.find('[type=file]').first()
          , $uploadValue = $uploadView.find('[type=hidden]').first()
          , $uploadPreview = $uploadView.find('img').first()
          , $form = $uploadView.closest('form');

        if(typeof FileReader == 'undefined') {
            $uploaderView.text('当前浏览器不支持HTML5的上传功能！');
            $uploadBtn.attr('disabled', 'disabled').click(function() { return false; });
            return;
        }

        

        var resetUploader = function() {
            $uploadFile.replaceWith($uploadFile = $uploadFile.clone(true));
            $uploadPreview.attr('src', '');
            $uploadValue.val('');
        };
        $uploadBtn.on('click', function() {
            console.log('click')
            console.log($uploadFile)
            $uploadFile.click();
        });
        $uploadFile.on('change', function(e) {
            if(this.files.length == 1) {
                var file = this.files[0];
                if(file.size > 2*1024*1024) {
                    alert('待上传文件不得大于2MB');
                    resetUploader();
                    return false;
                }
                var reader = new FileReader();
                reader.onload = function(e) {
                    var content = e.target.result;
                    if(!/data:image/i.test(content)) {
                        alert('只能够选择图片文件');
                        resetUploader();
                        return false;
                    }
                    $uploadValue.val('');
                    $uploadPreview.attr('src', content);
                }
                reader.readAsDataURL(file);
            } else {
                resetUploader();
            }
        });



        function doSubmit0() {
            // 验证表单

            // 转到doSubmit1
            doSubmit1();
        }
        function doSubmit1() {
            // 开始异步、UI
            $.maskLoading('setText', '提交中....').maskLoading('show');
            // 未上传图片、转到七牛上传图片
            if(!$uploadValue.val()) {
                $uploadFile.qiniuUploader(function(status, result) {
                    // qiniuUploader操作完后，重新关联$uploadFile
                    $uploadFile = $uploadView.find('[type=file]').first();
                    // 根据上传结果决定下一步操作
                    if(status) {
                        $uploadValue.val(result);
                        // 转到 doSubmit2
                        doSubmit2();
                    } else {
                        $uploadValue.val('');
                        alert('上传图片时发生错误');
                        // 停止上传
                        $.maskLoading('hide');
                    }
                    
                });
            } else {
                // 转到doSubmit2
                doSubmit2();
            }
        };
        function doSubmit2() {
            // 提交外部表单
            $.ajax({
                url : $form.attr('action'),
                type : $form.attr('method'),
                data : $form.serialize(),
                success : function() {
                    alert('ajax form success');
                    $form.html('创建成功');
                    $.maskLoading('setText', '创建成功，跳转中...');
                    // window.location = '';
                }, 
                error : function() {
                    alert('ajax form error');
                    alert('xxx错误');
                    $.maskLoading('hide');
                }
            })
        }

        $form.on('submit', function() {
            doSubmit0();
            return false;
        });

	});

}(window.jQuery);