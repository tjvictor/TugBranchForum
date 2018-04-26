function initPersonInformation(param){
    callAjax('/websiteService/getUserById', '', 'getUserByIdCallback', '', '', param, '');
}
function getUserByIdCallback(data){
    if(data.status == "ok"){
        var user = data.callBackData;
        $('#pi_avatar').attr('src', user.avatar);
        $('#pi_name').text(user.name);
        $('#pi_companyName').text(user.companyName);
    }
}

function initPublishTopic(pageNumber, pageSize){
    param = $('#pi_idPar').val()+"&pageNumber="+pageNumber+"&pageSize="+pageSize;
    callAjax('/websiteService/getPublicTopicsByUserId', '', 'getPublicTopicsByUserIdCallback', '', '', param, '');
}
function getPublicTopicsByUserIdCallback(data){
    if(data.status == "ok"){
        $('#publishTopicsBody').html('');
        var topic_body = '';
        var loginUser = jQuery.parseJSON(Cookies.get("user")).id;
        var pageUser = $('#pi_idPar').val().replace('userId=','');
        for(var i = 0; i < data.callBackData.length; i++){
            var topic = data.callBackData[i];
            if(pageUser == loginUser)
                topic_body += bindEditTopicBody(topic);
            else
                topic_body += bindCommonTopicBody(topic);
        }
        if(topic_body != ''){
            $('#publishTopicsBody').html(topic_body);
        }else{
            $('#publishTopicsBody').html('<div class="main-panel-body-cell">此分类下面没有数据</div>');
        }
    }
}

function initPublishTopicCount(){
    callAjax('/websiteService/getPublicTopicCountByUserId', '', 'getPublicTopicCountByUserIdCallback', '', '', $('#pi_idPar').val(), '');
}
function getPublicTopicCountByUserIdCallback(data){
    if(data.status == "ok"){
        $('#publishPagination').pagination({
            totalNumber: data.callBackData,
            onSelectPage: initPublishTopic,
        });
    }
}

function initReplyTopic(pageNumber, pageSize){
    param = $('#pi_idPar').val()+"&pageNumber="+pageNumber+"&pageSize="+pageSize;
    callAjax('/websiteService/getReplyTopicsByUserId', '', 'getReplyTopicsByUserIdCallback', '', '', param, '');
}
function getReplyTopicsByUserIdCallback(data){
    if(data.status == "ok"){
        $('#replyTopicsBody').html('');
        var topic_body = '';
        for(var i = 0; i < data.callBackData.length; i++){
            topic_body += bindCommonTopicBody(data.callBackData[i]);
        }
        if(topic_body != ''){
            $('#replyTopicsBody').html(topic_body);
        }else{
            $('#replyTopicsBody').html('<div class="main-panel-body-cell">此分类下面没有数据</div>');
        }
    }
}

function initReplyTopicCount(){
    callAjax('/websiteService/getReplyTopicCountByUserId', '', 'getReplyTopicCountByUserIdCallback', '', '', $('#pi_idPar').val(), '');
}
function getReplyTopicCountByUserIdCallback(data){
    if(data.status == "ok"){
        $('#replyPagination').pagination({
            totalNumber: data.callBackData,
            onSelectPage: initReplyTopic,
        });
    }
}

function bindEditTopicBody(topic) {
    var topic_body = '';
    topic_body += '<div class="main-panel-body-cell">';
    topic_body += ' <a target="_blank" href="/view/topic.html?topicId=' + topic.id + '" style="margin-left:10px;color:#888">' + topic.title + '</a>';
    topic_body += ' <span style="color: #9e78c0;font-size: 12px">' + topic.replyCount + '</span>';
    topic_body += ' <span style="margin: 0 -3px;">/</span>';
    topic_body += ' <span style="color: #b4b4b4;font-size: 12px">' + topic.viewCount + '</span>';
    topic_body += ' <span style="background-color:grey; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">' + topic.category.name + '</span>';
    if (topic.putTop) topic_body += ' <span style="background-color:blue; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">置顶</span>';
    if (topic.essence) topic_body += ' <span style="background-color:purple; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">申精</span>';
    if (topic.resolved) topic_body += ' <span style="background-color:#80bd01; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">已解决</span>';
    else topic_body += ' <span style="background-color:red; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">待解决</span>';
    if (topic.status == 2) topic_body += ' <span style="background-color:green; padding:2px 6px 2px 4px; border-radius:3px;color:yellow;font-size:14px;">已结贴</span>';

    if(topic.status == 2)
        topic_body += ' <a class="right" style="color:grey;margin-left:5px;cursor:default;">结贴</a>';
    else
        topic_body += ' <a target="_blank" href="#" class="right" style="color:green;margin-left:5px;">结贴</a>';
    topic_body += ' <a target="_blank" href="#" class="right" style="color:red;margin-left:5px;">删除</a>';
    topic_body += ' <a target="_blank" href="/view/newTopic.html?topicId='+topic.id+'" class="right" style="color:blue;margin-left:20px;">编辑</a>';
    topic_body += ' <span class="right" style="color:#888">' + topic.createTime.substring(0, 10) + '</span>';
    topic_body += '</div>';
    return topic_body;
}

function bindCommonTopicBody(topic) {
    var topic_body = '';
    topic_body += '<div class="main-panel-body-cell">';
    topic_body += ' <img src="' + topic.staff.avatar + '" class="left" style="width:36px;height:36px;margin-top:6px;">';
    topic_body += ' <span style="margin-left:10px;">' + topic.staff.name + '</span>';
    topic_body += ' <span style="color: #9e78c0;font-size: 12px">' + topic.replyCount + '</span>';
    topic_body += ' <span style="margin: 0 -3px;">/</span>';
    topic_body += ' <span style="color: #b4b4b4;font-size: 12px">' + topic.viewCount + '</span>';
    topic_body += ' <span style="background-color:grey; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">' + topic.category.name + '</span>';
    if (topic.putTop) topic_body += ' <span style="background-color:blue; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">置顶</span>';
    if (topic.essence) topic_body += ' <span style="background-color:purple; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">申精</span>';
    if (topic.resolved) topic_body += ' <span style="background-color:#80bd01; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">已解决</span>';
    else topic_body += ' <span style="background-color:red; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">待解决</span>';
    if (topic.status == 2) topic_body += ' <span style="background-color:green; padding:2px 6px 2px 4px; border-radius:3px;color:yellow;font-size:14px;">已结贴</span>';
    topic_body += ' <a target="_blank" href="/view/topic.html?topicId=' + topic.id + '" style="margin-left:10px;color:#888">' + topic.title + '</a>';
    topic_body += ' <span class="right" style="color:#888">' + topic.createTime.substring(0, 10) + '</span>';
    topic_body += '</div>';
    return topic_body;
}