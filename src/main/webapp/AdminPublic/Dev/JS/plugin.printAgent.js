;+function($) {


    var QRRestaurantPrintAgent = function() {

        this.debug = true && !!window.console;

        this.url = "ws://" + QRRestaurantPrintAgent.settings.host + ":4502/QRRestaurantPrintAgent";
        this.conn = null;
        this.status = "";

        this._initConn_callback_ = null;
        this._print_callback = null;
    };

    QRRestaurantPrintAgent.settings = {
        //host : '192.168.23.130',
        //host : '127.0.0.1',
        host : '192.168.1.102'
    };
    QRRestaurantPrintAgent.prototype = {

        _init : function() { 

            this.status = "";
            this.conn = new XSockets.WebSocket(this.url);

            this.conn.on(XSockets.Events.open,$.proxy(function (clientInfo) {
                this.debug && console.log("open");
                this._afterInitConn("open");
            }, this));
            
            this.conn.on(XSockets.Events.onError, $.proxy(function (err) {
                this.debug && console.log("error");
                this._afterInitConn("error");
            }, this));

            this.conn.on(XSockets.Events.close, $.proxy(function () {
                this.debug && console.log("close");
                this._afterInitConn("close");
            }, this));

            this.conn.on("print", $.proxy(this._onPrint, this));
        },

        _afterInitConn : function(status) {
            this.status = status;
            if(this._initConn_callback_) {
                this._initConn_callback_();
                this._initConn_callback_ = null;
            }
        },

        isConnOpen : function() {
            return this.conn != null && this.status == "open";
        },

        tryOpenConn : function(emptyCallback) {
            if(this.isConnOpen()) {
                emptyCallback();
            } else {
                this.status = "";
                this._initConn_callback_ = $.proxy(function() {
                    emptyCallback();
                }, this);
                this._init();
            }
            return this;
        },

        doPrint : function(data, callback) {
            !callback && (callback = $.noop);
            this.debug && console.log("print");
            this.tryOpenConn($.proxy(function() {
                if(!this.isConnOpen()) {
                    callback('未找到"打印助手"，请检查"打印助手"是否在运行中。');
                } else {
                    this._print_callback_ = callback;
                    this.conn.publish("print", data);
                }
            }, this));
            return this;
        },

        _onPrint : function(data) {
            this.debug && console.log("onPrint");
            if(data == "ok") {
                this._print_callback_(true);
            } else {
                this._print_callback_("打印过程中发生了错误");
            }
            this._print_callback_ = null;
        }
    };

    $.fn.printAgent && $.error('$.printAgent was defined');

    $.printAgent = function() {
        var pa = this._inner_printAgent__;
        if(!pa) {
            pa = new QRRestaurantPrintAgent();
            this._inner_printAgent__ = pa;
        }
        return pa;
    };

    $.printAgent.test = function() {
        $.printAgent().doPrint($.printAgent.TestData, function(result) {
            if(result == true) {
                alert('已提交至打印队列');
            } else {
                alert(result);
            }
        });
    };

    $.printAgent.TestData = {
        RestaurantName : "南亚风情第一城222",
        BoardName : "28号桌",
        DinersCount : "1",
        OrderNo : "74916",
        DishList : [
            {
                DishName : "欢乐牛扒（标准：五成熟）",
                DishPrice : "58.00",
                DishCount : "1"
            },
            {
                DishName : "欢乐牛扒2222（标准：五成熟）",
                DishPrice : "58.00",
                DishCount : "1"
            }
        ],
        AggregateAmount : "58.00",
        PaidAmount : "58.00",
        OrderTime : "2014-1-13 12:32:13",
        CashierName : "杨晓梅",
        RestaurantPhone : "0871-64601199",
        RestaurantAddress : "昆明市滇池路南亚风情第一城A1座三楼"
    };

}(window.jQuery);