/**

 @Name：layui.blog 闲言轻博客模块
 @Author：徐志文
 @License：MIT
 @Site：http://www.layui.com/template/xianyan/
    
 */
layui.define(['element', 'form','laypage','jquery','laytpl'],function(exports){
  var element = layui.element
  ,form = layui.form
  ,laypage = layui.laypage
  ,$ = layui.jquery
  ,laytpl = layui.laytpl;
  

  //statr 分页
  
  laypage.render({
    elem: 'test1' //注意，这里的 test1 是 ID，不用加 # 号
    ,count: 2 //数据总数，从服务端得到
    ,theme: '#1e9fff'
  });
  
  // end 分頁



  // start 导航显示隐藏
  
  $("#mobile-nav").on('click', function(){
      console.log("click happened!");
    $("#pop-nav").toggle();
  });


  // end 导航显示隐藏

    // //点赞函数
    // function top (id){
    //     if(!($(this).hasClass("layblog-this"))){
    //         $(this).addClass('layblog-this');
    //         console.log(this.text);
    //         var number = this.text.substring(1,this.text.length());
    //         console.log(number);
    //         $.tipsBox({
    //             obj: $(this),
    //             str: "+1",
    //             callback: function () {
    //                 var ajax = new $ax(Feng.ctxPath + "/blogTop/add",
    //                     function (data) {
    //                         layer.msg('点赞成功', {
    //                             icon: 6
    //                             ,time: 1000
    //                         });
    //                         niceIn($(this));
    //                     },
    //                     function (data) {
    //                         console.log(data);
    //                         layer.msg('点赞失败!', {
    //                             icon: 6
    //                             ,time: 1000
    //                         })
    //                     });
    //                 console.log("blogId",id);
    //                 ajax.set("blogId",id);
    //                 ajax.start();
    //             }
    //         });
    //     }
    // };


  //start 评论的特效
  
  (function ($) {
    $.extend({
        tipsBox: function (options) {
          options = $.extend({
            obj: null,  //jq对象，要在那个html标签上显示
            str: "+1",  //字符串，要显示的内容;也可以传一段html，如: "<b style='font-family:Microsoft YaHei;'>+1</b>"
            startSize: "12px",  //动画开始的文字大小
            endSize: "30px",    //动画结束的文字大小
            interval: 600,  //动画时间间隔
            color: "red",    //文字颜色
            callback: function () { }    //回调函数
          }, options);

          $("body").append("<span class='num'>" + options.str + "</span>");

          var box = $(".num");
          var left = options.obj.offset().left + options.obj.width() / 2;
          var top = options.obj.offset().top - 10;
          box.css({
            "position": "absolute",
            "left": left + "px",
            "top": top + "px",
            "z-index": 9999,
            "font-size": options.startSize,
            "line-height": options.endSize,
            "color": options.color
          });
          box.animate({
            "font-size": options.endSize,
            "opacity": "0",
            "top": top - parseInt(options.endSize) + "px"
          }, options.interval, function () {
            box.remove();
            options.callback();
          });
        }
      });
  })($); 
  //点赞特效
  function niceIn(prop){

    prop.find('i').addClass('niceIn');
    setTimeout(function(){
      prop.find('i').removeClass('niceIn'); 
    },1000);    
  }

  $(function () {

  });




  //end 评论的特效


  // start点赞图标变身
  // $('#LAY-msg-box').on('click', '.info-img', function(){
  //   $(this).addClass('layblog-this');
  // })


  // end点赞图标变身

  //end 提交
  $('#item-btn').on('click', function(){
    var elemCont = $('#LAY-msg-content')
    ,content = elemCont.val();
    if(content.replace(/\s/g, '') == ""){
      layer.msg('请先输入留言');
      return elemCont.focus();
    }

    var view = $('#LAY-msg-tpl').html()

    //模拟数据
    ,data = {
      username: '闲心'
      ,avatar: '../res/static/images/info-img.png'
      ,praise: 0
      ,content: content
    };

    //模板渲染
    laytpl(view).render(data, function(html){
      $('#LAY-msg-box').prepend(html);
      elemCont.val('');
      layer.msg('留言成功', {
        icon: 1
      })
    });

  })

  // start  图片遮罩
  var layerphotos = document.getElementsByClassName('layer-photos-demo');
  for(var i = 1;i <= layerphotos.length;i++){
    layer.photos({
      photos: ".layer-photos-demo"+i+""
      ,anim: 0
    }); 
  }
  // end 图片遮罩


  //输出test接口
  exports('blog', {}); 
});

//音乐播放开始
document.addEventListener('DOMContentLoaded', function () {
    // 设置音频文件名显示宽度
    var element = document.querySelector('.audio-right');
    if (element !== undefined&&element !== null) {
        var maxWidth = window.getComputedStyle(element, null).width;
        document.querySelector('.audio-right p').style.maxWidth = maxWidth;

        // 初始化音频控制事件
        initAudioEvent();
    }

}, false);

function initAudioEvent() {
    var audio = document.getElementsByTagName('audio')[0];
    var audioPlayer = document.getElementById('audioPlayer');
    audio.load();
    audio.oncanplay = function () {
        console.log("audio.duration", audio.duration);
        document.getElementById('audioTotalTime').innerText = transTime(audio.duration);
    }



    // // 添加音频自动播放功能
    // // PS：不完善，在chrome下会报错，原因看这里https://developers.google.com/web/updates/2017/09/autoplay-policy-changes
    // audio.addEventListener('canplaythrough', function (event) {
    //     var playPromise = audio.play();
    //     if (playPromise !== undefined) {
    //         playPromise.then(() => {
    //                 audioPlayer.src = './image/pause.png';
    //             })
    //             .catch(error => {
    //                 // Auto-play was prevented
    //                 // Show paused UI.
    //                 console.log(error.message)
    //             });
    //     }
    // });

    // 监听音频播放时间并更新进度条
    audio.addEventListener('timeupdate', function () {
        updateProgress(audio);
    }, false);

    // 监听播放完成事件
    audio.addEventListener('ended', function () {
        audioEnded();
    }, false);

    // 点击播放/暂停图片时，控制音乐的播放与暂停
    audioPlayer.addEventListener('click', function () {
        // 改变播放/暂停图片
        if (audio.paused) {
            // 开始播放当前点击的音频
            audio.play();
            audioPlayer.src = '/static/img/pause.png';
        } else {
            audio.pause();
            audioPlayer.src = '/static/img/play.png';
        }
    }, false);

    // 点击进度条跳到指定点播放
    // PS：此处不要用click，否则下面的拖动进度点事件有可能在此处触发，此时e.offsetX的值非常小，会导致进度条弹回开始处（简直不能忍！！）
    var progressBarBg = document.getElementById('progressBarBg');
    progressBarBg.addEventListener('mousedown', function (event) {
        // 只有音乐开始播放后才可以调节，已经播放过但暂停了的也可以
        if (!audio.paused || audio.currentTime != 0) {
            var pgsWidth = parseFloat(window.getComputedStyle(progressBarBg, null).width.replace('px', ''));
            var rate = event.offsetX / pgsWidth;
            audio.currentTime = audio.duration * rate;
            updateProgress(audio);
        }
    }, false);

    // 拖动进度点调节进度
    dragProgressDotEvent(audio);
}

/**
 * 鼠标拖动进度点时可以调节进度
 * @param {*} audio
 */
function dragProgressDotEvent(audio) {
    var dot = document.getElementById('progressDot');

    var position = {
        oriOffestLeft: 0, // 移动开始时进度条的点距离进度条的偏移值
        oriX: 0, // 移动开始时的x坐标
        maxLeft: 0, // 向左最大可拖动距离
        maxRight: 0 // 向右最大可拖动距离
    };
    var flag = false; // 标记是否拖动开始

    // 鼠标按下时
    dot.addEventListener('mousedown', down, false);
    dot.addEventListener('touchstart', down, false);

    // 开始拖动
    document.addEventListener('mousemove', move, false);
    document.addEventListener('touchmove', move, false);

    // 拖动结束
    document.addEventListener('mouseup', end, false);
    document.addEventListener('touchend', end, false);

    function down(event) {
        if (!audio.paused || audio.currentTime != 0) { // 只有音乐开始播放后才可以调节，已经播放过但暂停了的也可以
            flag = true;

            position.oriOffestLeft = dot.offsetLeft;
            position.oriX = event.touches ? event.touches[0].clientX : event.clientX; // 要同时适配mousedown和touchstart事件
            position.maxLeft = position.oriOffestLeft; // 向左最大可拖动距离
            position.maxRight = document.getElementById('progressBarBg').offsetWidth - position.oriOffestLeft; // 向右最大可拖动距离

            // 禁止默认事件（避免鼠标拖拽进度点的时候选中文字）
            if (event && event.preventDefault) {
                event.preventDefault();
            } else {
                event.returnValue = false;
            }

            // 禁止事件冒泡
            if (event && event.stopPropagation) {
                event.stopPropagation();
            } else {
                window.event.cancelBubble = true;
            }
        }
    }

    function move(event) {
        if (flag) {
            var clientX = event.touches ? event.touches[0].clientX : event.clientX; // 要同时适配mousemove和touchmove事件
            var length = clientX - position.oriX;
            if (length > position.maxRight) {
                length = position.maxRight;
            } else if (length < -position.maxLeft) {
                length = -position.maxLeft;
            }
            var progressBarBg = document.getElementById('progressBarBg');
            var pgsWidth = parseFloat(window.getComputedStyle(progressBarBg, null).width.replace('px', ''));
            var rate = (position.oriOffestLeft + length) / pgsWidth;
            audio.currentTime = audio.duration * rate;
            updateProgress(audio);
        }
    }

    function end() {
        flag = false;
    }
}

/**
 * 更新进度条与当前播放时间
 * @param {object} audio - audio对象
 */
function updateProgress(audio) {
    var value = audio.currentTime / audio.duration;
    document.getElementById('progressBar').style.width = value * 100 + '%';
    document.getElementById('progressDot').style.left = value * 100 + '%';
    document.getElementById('audioCurTime').innerText = transTime(audio.currentTime);
}

/**
 * 播放完成时把进度调回开始的位置
 */
function audioEnded() {
    document.getElementById('progressBar').style.width = 0;
    document.getElementById('progressDot').style.left = 0;
    document.getElementById('audioCurTime').innerText = transTime(0);
    document.getElementById('audioPlayer').src = '/static/img/play.png';
}

/**
 * 音频播放时间换算
 * @param {number} value - 音频当前播放时间，单位秒
 */
function transTime(value) {
    var time = "";
    var h = parseInt(value / 3600);
    value %= 3600;
    var m = parseInt(value / 60);
    var s = parseInt(value % 60);
    if (h > 0) {
        time = formatTime(h + ":" + m + ":" + s);
    } else {
        time = formatTime(m + ":" + s);
    }

    return time;
}

/**
 * 格式化时间显示，补零对齐
 * eg：2:4  -->  02:04
 * @param {string} value - 形如 h:m:s 的字符串
 */
function formatTime(value) {
    var time = "";
    var s = value.split(':');
    var i = 0;
    for (; i < s.length - 1; i++) {
        time += s[i].length == 1 ? ("0" + s[i]) : s[i];
        time += ":";
    }
    time += s[i].length == 1 ? ("0" + s[i]) : s[i];

    return time;
}