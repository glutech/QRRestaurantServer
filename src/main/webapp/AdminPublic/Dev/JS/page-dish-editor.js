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

        $form.exValidate({
            rules : {
                name : { required : true },
                desc : { required : true },
                category : { required : true },
                price : { required : true, number : true, min : 0.001 },
                tag : { required : true }
            },
            messages : {
                name : '请输入菜品名称',
                desc : '请输入菜品描述',
                category : '请选择菜品类别',
                price  : '请输入菜品价格',
                tag : '请输入菜品标签'
            },
            submitHandler : function() {
                // 检查图片
                if(!$form.find('[name=pic]').val() && !$form.find('[type=file]').val()) {
                    alert('请选择菜品图片');
                    return false;
                }
                doSubmit0();
            }
        });

        

        var resetUploader = function() {
            $uploadFile.replaceWith($uploadFile = $uploadFile.clone(true));
            $uploadPreview.attr('src', '');
            $uploadValue.val('');
        };
        $uploadBtn.on('click', function() {
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
                };
                reader.readAsDataURL(file);
            } else {
                resetUploader();
            }
        });


        // 存在初始数据时初始化
        $uploadValue.val() && $uploadPreview.attr('src', $uploadValue.val());


        function doSubmit0() {
            // 验证表单
            // 在exValidate内完成验证并在验证通过时调用到这里
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
                success : function(r) {
                    r['message'] && !!alert(r['message']);
                    if(r['redirectUrl']) {
                        if(r['redirectReplace']) {
                            window.location.replace(r['redirectUrl']);
                        } else {
                            window.location = r['redirectUrl'];
                        }
                    }
                    $.maskLoading('hide');
                }, 
                error : function() {
                    alert('发生错误');
                    $.maskLoading('hide');
                }
            });
        }

    });

}(window.jQuery);