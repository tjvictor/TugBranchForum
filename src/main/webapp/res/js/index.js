function initTopicCategory(){
    callAjax('/websiteService/getTopicCategory', '', 'getTopicCategoryCallback', '', '', '', '');
}
function getTopicCategoryCallback(data){
    var tabTemplate = '<a class="main-panel-header-tab main-panel-header-current-tab" data-value="">全部</a>';
    if (data.status == "ok") {
        for (var i = 0; i < data.callBackData.length; i++){
            var item = data.callBackData[i];
            tabTemplate += '<a class="main-panel-header-tab" data-value="'+item.id+'">'+item.name+'</a>'
        }
    }
    $('#category-tab').html(tabTemplate);
    $('#category-tab .main-panel-header-tab').click(
        function(event){
            $('#category-tab .main-panel-header-tab').removeClass('main-panel-header-current-tab');
            $(event.target).addClass('main-panel-header-current-tab');
            getTopicListByCategory($('#globalTopicTitleTxt').val().trim(), $(event.target).attr('data-value'), 1, globalPageSize);
            getTopicCountByCategory($('#globalTopicTitleTxt').val().trim(), $(event.target).attr('data-value'));
        }
    );
}

function globalTopicSearch(){
    getTopicListByCategory($('#globalTopicTitleTxt').val().trim(), $('.main-panel-header-current-tab').attr('data-value'), 1, globalPageSize);
    getTopicCountByCategory($('#globalTopicTitleTxt').val().trim(), $('.main-panel-header-current-tab').attr('data-value'));
}

function getTopicListByCategory(title, categoryId, pageNumber, pageSize){
    var param = "title="+title+"&categoryId="+categoryId+"&pageNumber="+pageNumber+"&pageSize="+pageSize;
    callAjax('/websiteService/getTopicListByCategory', '', 'getTopicListByCategoryCallback', '', '', param, '');
}
function getTopicListByCategoryCallback(data){
    if (data.status == "ok") {
        $('#topicListBody').html('');
        var topic_body = '';
        for(var i = 0; i < data.callBackData.length; i++){
            var topic = data.callBackData[i];
            topic_body += '<div class="main-panel-body-cell">';
            topic_body += ' <img src="'+topic.staff.avatar+'" class="left" style="width:36px;height:36px;margin-top:6px;">';
            topic_body += ' <a href="/view/userHomePage.html?userId='+topic.staff.id+'" target="_blank" style="margin-left:10px;cursor:pointer">'+topic.staff.name+'</a>';
            topic_body += ' <span style="color: #9e78c0;font-size: 12px">'+topic.replyCount+'</span>';
            topic_body += ' <span style="margin: 0 -3px;">/</span>';
            topic_body += ' <span style="color: #b4b4b4;font-size: 12px">'+topic.viewCount+'</span>';
            topic_body += ' <span style="background-color:grey; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">'+topic.category.name+'</span>';
            if(topic.putTop)
                topic_body += ' <span style="background-color:blue; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">置顶</span>';
            if(topic.essence)
                topic_body += ' <span style="background-color:purple; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">申精</span>';
            if(topic.resolved)
                topic_body += ' <span style="background-color:#80bd01; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">已解决</span>';
            else
                topic_body += ' <span style="background-color:red; padding:2px 6px 2px 4px; border-radius:3px;color:#fff;font-size:14px;">待解决</span>';
            if(topic.status == 2)
                topic_body += ' <span style="background-color:green; padding:2px 6px 2px 4px; border-radius:3px;color:yellow;font-size:14px;">已结贴</span>';
            topic_body += ' <a target="_blank" href="/view/topic.html?topicId='+topic.id+'" style="margin-left:10px;color:#888">'+topic.title+'</a>';
            topic_body += ' <span class="right" style="color:#888">'+topic.createTime.substring(0, 10)+'</span>';
            topic_body += '</div>';
        }
        if(topic_body != ''){
            $('#topicListBody').html(topic_body);
        }else{
            $('#topicListBody').html('<div class="main-panel-body-cell">此分类下面没有数据</div>');
        }
    }
}

function getTopicCountByCategory(title, categoryId){
    var param = "title="+title+"&categoryId="+categoryId;
    callAjax('/websiteService/getTopicCountByCategory', '', 'getTopicCountByCategoryCallback', '', '', param, '');
}
function getTopicCountByCategoryCallback(data){
    if(data.status == "ok"){
        if(data.callBackData>0){
            createPagination('pagination', 1, globalPageSize, data.callBackData, topicPaginationChange);
            $('#pagination').css('display','block');
        }
        else
            $('#pagination').css('display','none');
    }
}

function topicPaginationChange(pageNumber, pageSize){
    getTopicListByCategory($('#globalTopicTitleTxt').val().trim(), $('.main-panel-header-current-tab').attr('data-value'), pageNumber, pageSize);
}