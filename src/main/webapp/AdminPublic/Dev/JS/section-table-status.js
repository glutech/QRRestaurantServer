;!function($) {

    if($('#section-table-status-container').ex().empty()) return;
    
    ;$(function() {

        /*
         * 动态调整餐桌状态面板高度
         */
        ;!function() {
            $('#section-table-status-container').on('shown.bs.modal', function(e) {
                var $this = $(this)
                  , $panelGroup = $this.find('.model-container-main.panel-group').first()
                  , $panels = $panelGroup.find('.panel');
                if($panels.length <= 1) return;
                var marginHeight = 0
                  , borderHeight = 0
                  , headingHeight = 0;
                $panels.each(function() {
                    var $panel = $(this);
                    marginHeight += parseFloat($panel.css('margin-top')) + parseFloat($panel.css('margin-bottom'));
                    borderHeight += parseFloat($panel.css('border-top-width')) + parseFloat($panel.css('border-bottom-width'));
                    headingHeight = Math.max(headingHeight, $panel.find('.panel-heading').outerHeight());
                });
                var panelMaxHeightPx = ($panelGroup.innerHeight() - headingHeight * ($panels.length - 1) - marginHeight - borderHeight) + 'px';
                $panels.each(function() {
                    $(this).css('max-height', panelMaxHeightPx);
                });
                $panels.find('.panel-collapse.collapse').on('shown.bs.collapse', function(e) {
                    var $this = $(this)
                      , $panel = $this.closest('.panel')
                      , $panelHeading = $panel.find('.panel-heading');
                    $this.height(parseFloat($panel.css('max-height')) - $panelHeading.height());
                });
            });
        }();
        

        /*
         * 餐桌状态面板操作逻辑
         */
        ;!function() {
            $('#section-table-status-container').on('hidden.bs.modal', function(e) {
                $(this).removeData('bs.modal').html('');
            }).on('shown.bs.modal', function(e) {
                var $this = $(this)
                  , $panelGroup = $this.find('.model-container-main.panel-group').first()
                  , $panels = $panelGroup.find('.panel');
                $panels.find('.panel-heading a[data-toggle=collapse]').on('click', function(e) {
                    var $this = $(this)
                      , $panel = $this.closest('.panel')
                      , $collapse = $panel.find('.panel-collapse');
                    if($collapse.hasClass('in')) {
                        var $nextPanel = $panel.next();
                        if(!$nextPanel.length || !$nextPanel.hasClass('panel')) {
                            $nextPanel = $panels.first();
                        }
                        if($nextPanel.length && $nextPanel.hasClass('panel')) {
                            $nextPanel.find('.panel-heading a[data-toggle=collapse]').trigger('click');
                            return false;
                        }
                    }
                });
                $panels.find('.panel-collapse.in').trigger('show.bs.collapse').trigger('shown.bs.collapse');
            });
        }();

    });

}(window.jQuery);