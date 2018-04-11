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