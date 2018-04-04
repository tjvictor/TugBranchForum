KindEditor.plugin('singleaudio', function(K) {
        var editor = this, name = 'singleaudio';
        // 点击图标时执行
        editor.clickToolbar(name, function() {
            return $('#singleaudioInput').click();
        });
});