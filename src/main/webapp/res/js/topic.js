var replyKindeditor

function initTopicKindeditor(height) {
    if(!height)
        height = "300";
    replyKindeditor = KindEditor.create('#replyTxt', {
        items: ['undo', 'redo', '|', 'preview', 'cut', 'copy', 'paste', 'plainpaste', '|', 'justifyleft', 'justifycenter',
                'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript',
                'quickformat', 'selectall', '|', 'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic',
                'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'table', 'hr', 'emoticons', 'pagebreak', 'link', 'unlink',
                'singlefile','singleimg'],
        width: "100%",
        height: height+"px",
        resizeType: 0,
        filterMode: false,
    });
}

function initTopicCategory(){
    callAjax('/websiteService/getTopicCategory', '', 'getTopicCategoryCallback', '', '', '', '');
}
function getTopicCategoryCallback(data){
    if (data.status == "ok") {
        for (var i = 0; i < data.callBackData.length; i++){
            var item = data.callBackData[i];
            $("#topicCategorySelect").append($("<option>").val(item.id).text(item.name));
        }

    }
}

function addTopic(){
    var user = jQuery.parseJSON(Cookies.get("user"));
    var staffId = user.id;
    var title = $('#titleTxt').val();
    if($.trim(title) == ""){
        alert('标题不能为空');
        return;
    }
    var content = replyKindeditor.html();
    var categoryId = $("#topicCategorySelect").val();

    var postValue = {
        "title": title,
        "content": content,
        "staffId": staffId,
        "categoryId": categoryId,
    };

    callAjax('/websiteService/addTopic', '', 'addTopicCallback', '', 'POST', postValue, '.window-page-mask');
}
function addTopicCallback(data){
    if(data.status == "ok"){
        window.location = '/view/topic.html?topicId='+data.callBackData;
    }
}

function initTopic(topicPar){
    callAjax('/websiteService/getTopicById', '', 'getTopicByIdCallback', '', '', topicPar, '');
}
function getTopicByIdCallback(data){
    if(data.status == "ok"){
        var topic = data.callBackData;
        var creator = topic.staff;
        var category = topic.category;

        //Topic Body
        $('#tp_id').val(topic.id);
        $('#tp_title').text(topic.title);
        $('#tp_category').text(category.name);
        $('#tp_category').css('display','inline-block');
        if(topic.putTop)
            $('#tp_putTop').css('display','inline-block');
        if(topic.essence)
            $('#tp_essence').css('display','inline-block');
        if(topic.status == 2)
            $('#tp_status').css('display','inline-block');
        if(topic.resolved)
            $('#tp_resolved').css('display','inline-block');
        else
            $('#tp_non_resolved').css('display','inline-block');
        $('#tp_createTime').text(topic.dateTime);
        $('#tp_creator').text('作者 '+creator.name+' - '+creator.companyName);
        $('#tp_viewCount').text(topic.viewCount+' 次浏览');
        $('#tp_content').html(topic.content);

        //Reply-Topic Body
        $('#tp_replyCount').text(topic.replyCount +'回复');

        //Reply Body
        if(topic.status == 2)
            $('#reply-body').css('display','none');
    }
}

function updateTopicViewCountById(topicPar){
    callAjax('/websiteService/updateTopicViewCountById', '', '', '', '', topicPar, '');
}

function initTopicReply(topicPar, pageNumber, pageSize){
    var param = topicPar+"&pageNumber="+pageNumber+"&pageSize="+pageSize;
    callAjax('/websiteService/getReplyTopicsByTopicId', '', 'getReplyTopicsByTopicIdCallback', '', '', param, '');
}
function getReplyTopicsByTopicIdCallback(data){
    if(data.status == "ok"){
        $('#rtp_body').html('');
        var rtp_body = '';
        var replyTopics = data.callBackData;
        for(var i = 0; i < replyTopics.length; i++){
            var item = replyTopics[i];
            var staff = item.staff;
            rtp_body += '<div style="border-bottom: 5px solid #E1E1E1;padding:10px;">';
            rtp_body += '   <div class="left" style="width:48px;height:48px;background-image:url('+staff.avatar+');background-size:48px 48px;position:absolute;"></div>';
            rtp_body += '   <div style="margin-left:60px;">';
            rtp_body += '       <div>';
            rtp_body += '           <div class="left">'+staff.name+'</div>';
            rtp_body += '           <div class="right">'+item.createTime+'</div>';
            rtp_body += '           <div class="clear"></div>';
            rtp_body += '       </div>';
            rtp_body += '       <hr style="margin:5px 0px;">';
            rtp_body += '       <div style="margin: 20px 0px;">'+item.content+'</div>';
            rtp_body += '   </div>';
            rtp_body += '   <div class="clear"></div>';
            rtp_body += '</div>';
        }
        $('#rtp_body').html(rtp_body);
    }
}

function addReplyTopic(){
    var user = jQuery.parseJSON(Cookies.get("user"));
    var staffId = user.id;
    if($.trim(replyKindeditor.html()) == ""){
        alert('回复不能为空');
        return;
    }
    var content = replyKindeditor.html();

    var postValue = {
        "topicId": $('#tp_id').val(),
        "content": content,
        "staffId": staffId,
        "topicCreateTime": $('#tp_createTime').text(),
    };

    callAjax('/websiteService/addReplyTopic', '', 'addReplyTopicCallback', '', 'POST', postValue, '.window-page-mask');
}
function addReplyTopicCallback(data){
    if(data.status == "ok"){
        window.location = '/view/topic.html?topicId='+data.callBackData;
    }
}