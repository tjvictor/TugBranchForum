KindEditor.plugin('singleimg', function(K) {
        var editor = this, name = 'singleimg';
        // 点击图标时执行
        editor.clickToolbar(name, function() {
            return $('#singleimgInput').click();
        });
});