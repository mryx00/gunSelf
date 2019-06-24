var layTabPlus={
    _tabs:{},
    _opt:{
        refresh:true,
        lay_filter:null,
        tab_jump:false
    },
    init:function(opt){
        _this=this;
        if(opt){
            _this._opt.refresh = opt.refresh || _this._opt.refresh;
            _this._opt.tab_jump = opt.tab_jump || _this._opt.tab_jump;
            _this._opt.lay_filter = opt.lay_filter;
        }
        if(null==this._opt.lay_filter){
            throw new Error("lay_filter can't be null");
        }
        element.on('tabDelete('+this._opt.lay_filter+')', function(data){
            var tab_id=$(this.parentNode).attr('lay-id');
            console.log("adsad");
            if (tab_id==null){
                tab_id=$(".rightmenu li").attr('lay-id');
            }
            delete _this._tabs[tab_id];
        });
        $(".layui-tab-title").on('click',".layui-icon-refresh",function(){
            var iframe=$("#"+$(this).attr('f_id'))[0];
            iframe.src=iframe.src;
        });
        $('body').on('click','.layTabPlus',function(){
            _this.addTab(this);
        });
        //css
        document.write('<style>.layui-body{overflow-y: hidden;}.layui-tab{margin: 0;}.layui-tab-title li .layui-icon-refresh:hover{border-radius:2px;background-color:#FF5722;color:#fff}.layui-tab-title li .layui-icon-refresh{position:relative;display:inline-block;width:18px;height:18px;line-height:20px;margin-left:8px;top:1px;text-align:center;font-size:14px;color:#c2c2c2;transition:all .2s;-webkit-transition:all .2s}.layui-tab-title{padding-top:0!important;border-top:5px solid #009688}.layui-tab-title .layui-this:after{border-width:0!important}.noclose i{display:none!important}.layui-tab-title .layui-this{color:#fff;background:#009688}</style>');
    },
    addTab:function(obj,title){//1直接传dom 2传url和title
        var tab_url=obj;
        var tab_title=title||tab_url;
        var iframe_id='ltp_'+Date.now().toString(36);
        if(obj instanceof HTMLElement){
            var tab_jump=obj.getAttribute('tab_jump');
            if(tab_jump){
                this._opt.tab_jump=true;
            }
            tab_url=obj.getAttribute('tab_url');
            tab_title=obj.innerHTML;
        }
        if(this._opt.refresh){
            tab_title+=' <i class="layui-icon layui-icon-refresh" f_id="'+iframe_id+'"></i>';
        }
        if(this._tabs[tab_url]==null){
            var tab_jump_str='';
            if(!this._opt.tab_jump){
                tab_jump_str='sandbox="allow-top-navigation allow-scripts allow-same-origin allow-popups"';
            }
            // var width = document.documentElement.clientWidth;
            // var iframe = document.getElementById(iframe_id);
            // iframe.style.with = width;
            // iframe.height = window.innerHeight;
            element.tabAdd(this._opt.lay_filter, {
                title: tab_title
                ,content: '<iframe id="'+iframe_id+'" src="'+tab_url+'" style="width:100%;height:2000px;overflow-x:hidden !important; overflow-y:auto;" scrolling="yes" onload="layTabPlus.setIframeHeight()" frameborder="0"  '+tab_jump_str+'></iframe>' //支持传入html
                ,id: tab_url
            });
            this._tabs[tab_url]=iframe_id;
        }
        console.log("aaaa");
        element.tabChange(this._opt.lay_filter, tab_url);
        //绑定右键菜单
        CustomRightClick(this, tab_url);
    },

// document.domain = "caibaojian.com";
//     setIframeHeight(iframe) {
//         if (iframe) {
//             console.log(iframe.height);
//             var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
//             if (iframeWin.document.body) {
//                 iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
//                 console.log(iframe.height);
//             }
//         }
//     }
    /**
     * 必须减一点高度  原因暂不清楚
     */

    setIframeHeight:function(){
         var h =  $(window).height() - 85;
         $("iframe").css("height",h+"px");

    }
    // setIframeHeight(iframe) {
    //     if (iframe) {
    //         var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
    //         if (iframeWin.document.body) {
    //             iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
    //         }
    //     }
    // }
}
/**
 * 解决iframe响应式问题
 */
$(window).resize(function () {
    layTabPlus.setIframeHeight();
})