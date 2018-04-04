KindEditor.plugin('singlevideo', function(K) {
        var editor = this, name = 'singlevideo';
        // 点击图标时执行
        editor.clickToolbar(name, function() {
            return $('#singlevideoInput').click();
        });
});