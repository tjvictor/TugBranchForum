KindEditor.plugin('singlefile', function(K) {
        var editor = this, name = 'singlefile';
        // 点击图标时执行
        editor.clickToolbar(name, function() {
            return $('#singlefileInput').click();
        });
});