/*$(function() {
    createPagination('div', 1, 10, 273, selectPageFunction);
});*/

function createPagination(objId, pageNumber, pageSize, totalNumber, onSelectPageFunction) {
    var content = '<ul>';
    content += '    <li id="page_first">';
    content += '        <span title="第一页">«</span>';
    content += '    </li>';
    content += '    <li id="page_previous">';
    content += '        <span title="前一页"><</span>';
    content += '    </li>';
    content += '    <li id="page_1">';
    content += '        <span class="active">1</span>';
    content += '    </li>';
    content += '    <li id="page_2">';
    content += '        <span>2</span>';
    content += '    </li>';
    content += '    <li id="page_3">';
    content += '        <span>3</span>';
    content += '    </li>';
    content += '    <li id="page_4">';
    content += '        <span>4</span>';
    content += '    </li>';
    content += '    <li id="page_5">';
    content += '        <span>5</span>';
    content += '    </li>';
    content += '    <li  id="page_next">';
    content += '        <span title="下一页">></span>';
    content += '    </li>';
    content += '    <li id="page_last">';
    content += '        <span title="最后一页">»</span>';
    content += '    </li>';
    content += '   </ul>';
    var obj = '#' + objId + '.pagination';
    $(obj).html(content);
    initPagination(obj, pageNumber, pageSize, totalNumber, onSelectPageFunction);
}

function initPagination(obj, pageNumber, pageSize, totalNumber, onSelectPageFunction) {
    var maxPageNumber = Math.ceil(totalNumber / pageSize);
    $(obj + ' ul li').off("click");

    $(obj + ' #page_first').on("click",
    function() {
        onSelectPageFunction(1, pageSize);
        setCurrentPageClass(obj, 1);
        initPagination(obj, 1, pageSize, totalNumber, onSelectPageFunction);
    });
    $(obj + ' #page_last span').attr('title', '最后一页:' + maxPageNumber + '页');
    $(obj + ' #page_last').on("click",
    function() {
        onSelectPageFunction(maxPageNumber, pageSize);
        setCurrentPageClass(obj, maxPageNumber);
        initPagination(obj, maxPageNumber - maxPageNumber % 5 + 1, pageSize, totalNumber, onSelectPageFunction);
    });

    if (pageNumber <= maxPageNumber) {
        $(obj + ' #page_1 span').text(pageNumber);
        $(obj + ' #page_1').on("click",
        function() {
            onSelectPageFunction(pageNumber, pageSize);
            setCurrentPageClass(obj, pageNumber);
        });
        $(obj + ' #page_1').css('display', 'block');
    } else $(obj + ' #page_1').css('display', 'none');

    if (pageNumber + 1 <= maxPageNumber) {
        $(obj + ' #page_2 span').text(pageNumber + 1);
        $(obj + ' #page_2').on("click",
        function() {
            onSelectPageFunction(pageNumber + 1, pageSize);
            setCurrentPageClass(obj, pageNumber + 1);
        });
        $(obj + ' #page_2').css('display', 'block');
    } else $(obj + ' #page_2').css('display', 'none');

    if (pageNumber + 2 <= maxPageNumber) {
        $(obj + ' #page_3 span').text(pageNumber + 2);
        $(obj + ' #page_3').on("click",
        function() {
            onSelectPageFunction(pageNumber + 2, pageSize);
            setCurrentPageClass(obj, pageNumber + 2);
        });
        $(obj + ' #page_3').css('display', 'block');
    } else $(obj + ' #page_3').css('display', 'none');

    if (pageNumber + 3 <= maxPageNumber) {
        $(obj + ' #page_4 span').text(pageNumber + 3);
        $(obj + ' #page_4').on("click",
        function() {
            onSelectPageFunction(pageNumber + 3, pageSize);
            setCurrentPageClass(obj, pageNumber + 3);
        });
        $(obj + ' #page_4').css('display', 'block');
    } else $(obj + ' #page_4').css('display', 'none');

    if (pageNumber + 4 <= maxPageNumber) {
        $(obj + ' #page_5 span').text(pageNumber + 4);
        $(obj + ' #page_5').on("click",
        function() {
            onSelectPageFunction(pageNumber + 4, pageSize);
            setCurrentPageClass(obj, pageNumber + 4);
        });
        $(obj + ' #page_5').css('display', 'block');
    } else $(obj + ' #page_5').css('display', 'none');

    if (pageNumber == 1) {
        $(obj + ' #page_previous').css('display', 'none');
        if(maxPageNumber>5){
            $(obj + ' #page_next').css('display', 'block');
            $(obj + ' #page_next').on("click",
            function() {
                setCurrentPageClass(obj, 1);
                nextPagination(obj, pageNumber, pageSize, totalNumber, onSelectPageFunction);
            });
        } else
            $(obj + ' #page_next').css('display', 'none');
    } else if (pageNumber + 5 >= maxPageNumber) {
        $(obj + ' #page_previous').css('display', 'block');
        $(obj + ' #page_next').css('display', 'none');
        $(obj + ' #page_previous').on("click",
        function() {
            setCurrentPageClass(obj, 5);
            previousPagination(obj, pageNumber, pageSize, totalNumber, onSelectPageFunction);
        });
    } else {
        $(obj + ' #page_previous').css('display', 'block');
        $(obj + ' #page_next').css('display', 'block');
        $(obj + ' #page_next').on("click",
        function() {
            setCurrentPageClass(obj, 1);
            nextPagination(obj, pageNumber, pageSize, totalNumber, onSelectPageFunction);
        });
        $(obj + ' #page_previous').on("click",
        function() {
            setCurrentPageClass(obj, 5);
            previousPagination(obj, pageNumber, pageSize, totalNumber, onSelectPageFunction);
        });
    }
}

function nextPagination(obj, pageNumber, pageSize, totalNumber, onSelectPageFunction) {
    onSelectPageFunction(pageNumber + 5, pageSize);
    initPagination(obj, pageNumber + 5, pageSize, totalNumber, onSelectPageFunction);
}

function previousPagination(obj, pageNumber, pageSize, totalNumber, onSelectPageFunction) {
    onSelectPageFunction(pageNumber - 1, pageSize);
    initPagination(obj, pageNumber - 5, pageSize, totalNumber, onSelectPageFunction);
}

function setCurrentPageClass(obj, pageNumber) {
    $(obj + ' ul li span').removeClass('active');
    var index = pageNumber % 5;
    if (index == 0) {
        $(obj + ' #page_5 span').addClass('active');
    } else {
        $(obj + ' #page_' + index + ' span').addClass('active');
    }
}