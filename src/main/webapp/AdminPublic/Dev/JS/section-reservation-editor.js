;!function($) {

    if($('#section-reservation-editor-container').ex().empty()) return;

    $(function() {

        $('#section-reservation-editor-container').on('hidden.bs.modal', function(e) {
            $(this).removeData('bs.modal').html('');
        });

        $('#section-reservation-editor-container form').exValidate({
            
        });

    });

}(window.jQuery);