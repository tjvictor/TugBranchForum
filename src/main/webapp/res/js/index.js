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
}

function getTopicListByCategory(categoryId, pageNumber, pageSize){
    var param = "categoryId="+categoryId+"&pageNumber="+pageNumber+"&pageSize="+pageSize;
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
            topic_body += ' <span style="margin-left:10px;">'+topic.staff.name+'</span>';
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
            topic_body += '<div id = "pagination" class="pagination" style="margin: 10px 0 0 10px;"></div>';
            $('#topicListBody').html(topic_body);
            createPagination('pagination', 1, 10, data.callBackData.length, topicCategoryTabChange);
        }else{
            $('#topicListBody').html('<div class="main-panel-body-cell">此分类下面没有数据</div>');
        }
    }
}

function topicCategoryTabChange(pageNumber, pageSize){
    var param = "categoryId="+$('.main-panel-header-current-tab').attr('data-value')+"&pageNumber="+pageNumber+"&pageSize="+pageSize;
    callAjax('/websiteService/getTopicListByCategory', '', 'getTopicListByCategoryCallback', '', '', param, '');
}