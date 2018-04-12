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

function initTopic(topicId){
    callAjax('/websiteService/getTopicById', '', 'getTopicByIdCallback', '', '', 'topicId='+topicId, '');
}
function getTopicByIdCallback(data){

}