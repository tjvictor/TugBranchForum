;
(function($) {
    var messageDiv = function(ele, opt) {
        this.$element = ele;
        this.defaults = {
            timeOut: 3000,
        };
        this.$element.removeClass();
        this.$element.removeAttr("style");
        this.$element.addClass('window-myMessage-mask');
        this.options = $.extend({},
        this.defaults, opt);
        this.$element.css(this.options);
    };

    messageDiv.prototype = {

        alert: function(title, content) {
            this.buildAlertDialog(title, content);
        },

        confirm: function(title, content, callback) {
            this.buildConfirmDialog(title, content, callback);
        },

        wait: function() {
            this.$element.html('');
            this.$element.addClass('window-myMessage-mask-background');
        },

        release: function() {
            this.$element.html('');
            this.$element.removeClass();
            this.$element.removeAttr("style");
        },

        prompt: function(title, content, timeOut) {
            this.buildPromptDialog(title, content);
            var delay = timeOut ? timeOut: this.defaults.timeOut;
            this.$element.delay(delay).hide(0);
        },

        buildAlertDialog: function(title, content) {
            var template = '';
            template += '<div id="alertDialog" style="border:1px solid #95B8E7;padding: 5px;border-radius: 5px;width:300px;position:relative;top:30%;background:-webkit-linear-gradient(top,#EFF5FF 0,#E0ECFF 20%);margin: 0 auto;">';
            template += '   <div style="width:100%;height:25px;">';
            template += '       <span style="padding:5px;font-size:14px;">' + title + '</span>';
            template += '   </div>';
            template += '   <div style="background-color:white;width:288px;padding:10px;">';
            template += '       <div style="text-align: center;font-size:24px;min-height:50px;line-height:50px;">' + content + '</div>';
            template += '       <div id="closeAlertBtn" style="margin: 0 auto;width:50px;border:1px solid #95B8E7;padding: 3px;border-radius: 5px;text-align: center;font-size: 14px;cursor: pointer;margin-top: 10px;">确定</div>';
            template += '   </div>';
            template += '</div>';

            this.$element.html(template);
            $('#closeAlertBtn').on('click', this.$element, this.close);
        },

        buildConfirmDialog: function(title, content, callback) {
            var template = '';
            template += '<div id="alertDialog" style="border:1px solid #95B8E7;padding: 5px;border-radius: 5px;width:300px;position:relative;top:30%;background:-webkit-linear-gradient(top,#EFF5FF 0,#E0ECFF 20%);margin: 0 auto;">';
            template += '   <div style="width:100%;height:25px;">';
            template += '       <span style="padding:5px;font-size:14px;">' + title + '</span>';
            template += '   </div>';
            template += '   <div style="background-color:white;width:288px;padding:10px;">';
            template += '       <div style="text-align: center;font-size:24px;min-height:50px;line-height:50px;">' + content + '</div>';
            template += '       <div style="width:50%;float:left;">';
            template += '           <div id="confirmActionBtn" style="margin: 0 auto;width:50px;border:1px solid #95B8E7;padding: 3px;border-radius: 5px;text-align: center;font-size: 14px;cursor: pointer;margin-top: 10px;">确定</div>';
            template += '       </div>';
            template += '       <div style="width:50%;float:left;">';
            template += '           <div id="cancelActionBtn" style="margin: 0 auto;width:50px;border:1px solid #95B8E7;padding: 3px;border-radius: 5px;text-align: center;font-size: 14px;cursor: pointer;margin-top: 10px;">取消</div>';
            template += '       </div>';
            template += '       <div style="clear:both;"></div>';
            template += '   </div>';
            template += '</div>';

            this.$element.html(template);
            $('#confirmActionBtn').on('click', {
                obj: this.$element,
                callback: callback
            },
            this.affirm);
            $('#cancelActionBtn').on('click', this.$element, this.close);
        },

        buildPromptDialog: function(title, content) {
            var template = '';
            template += '<div id="alertDialog" style="border:1px solid #95B8E7;padding: 5px;border-radius: 5px;width:300px;position:relative;top:30%;background:-webkit-linear-gradient(top,#EFF5FF 0,#E0ECFF 20%);margin: 0 auto;">';
            template += '   <div style="width:100%;height:25px;">';
            template += '       <span style="padding:5px;font-size:14px;">' + title + '</span>';
            template += '   </div>';
            template += '   <div style="background-color:white;width:288px;padding:10px;">';
            template += '       <div style="text-align: center;font-size:24px;min-height:50px;line-height:50px;">' + content + '</div>';
            template += '   </div>';
            template += '</div>';

            this.$element.html(template);
        },

        close: function(event) {
            event.data.html('');
            event.data.hide();
            return false;
        },

        affirm: function(event) {
            if (event.data.callback) event.data.callback();
            event.data.obj.html('');
            event.data.obj.hide();
            return false;
        }
    }

    $.fn.myMessager = function(opt) {
        return new messageDiv(this, opt);
    };
})(jQuery);