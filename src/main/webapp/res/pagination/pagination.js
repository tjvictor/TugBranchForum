/*$(function() {
    createPagination(1, 10, 273, selectPageFunction);
});*/

function createPagination(pageNumber, pageSize, totalNumber, onSelectPageFunction) {
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
    $('.pagination').html(content);
    initPagination(pageNumber, pageSize, totalNumber, onSelectPageFunction);
}

function initPagination(pageNumber, pageSize, totalNumber, onSelectPageFunction) {
    var maxPageNumber = Math.ceil(totalNumber / pageSize);
    $('.pagination ul li').off("click");

    $('.pagination #page_first').on("click",
    function() {
        onSelectPageFunction(1, pageSize);
        setCurrentPageClass(1);
        initPagination(1, pageSize, totalNumber, onSelectPageFunction);
    });
    $('.pagination #page_last span').attr('title', '最后一页:' + maxPageNumber + '页');
    $('.pagination #page_last').on("click",
    function() {
        onSelectPageFunction(maxPageNumber, pageSize);
        setCurrentPageClass(maxPageNumber);
        initPagination(maxPageNumber - maxPageNumber % 5 + 1, pageSize, totalNumber, onSelectPageFunction);
    });

    if (pageNumber <= maxPageNumber) {
        $('.pagination #page_1 span').text(pageNumber);
        $('.pagination #page_1').on("click",
        function() {
            onSelectPageFunction(pageNumber, pageSize);
            setCurrentPageClass(pageNumber);
        });
        $('.pagination #page_1').css('display', 'block');
    } else $('.pagination #page_1').css('display', 'none');

    if (pageNumber + 1 <= maxPageNumber) {
        $('.pagination #page_2 span').text(pageNumber + 1);
        $('.pagination #page_2').on("click",
        function() {
            onSelectPageFunction(pageNumber + 1, pageSize);
            setCurrentPageClass(pageNumber + 1);
        });
        $('.pagination #page_2').css('display', 'block');
    } else $('.pagination #page_2').css('display', 'none');

    if (pageNumber + 2 <= maxPageNumber) {
        $('.pagination #page_3 span').text(pageNumber + 2);
        $('.pagination #page_3').on("click",
        function() {
            onSelectPageFunction(pageNumber + 2, pageSize);
            setCurrentPageClass(pageNumber + 2);
        });
        $('.pagination #page_3').css('display', 'block');
    } else $('.pagination #page_3').css('display', 'none');

    if (pageNumber + 3 <= maxPageNumber) {
        $('.pagination #page_4 span').text(pageNumber + 3);
        $('.pagination #page_4').on("click",
        function() {
            onSelectPageFunction(pageNumber + 3, pageSize);
            setCurrentPageClass(pageNumber + 3);
        });
        $('.pagination #page_4').css('display', 'block');
    } else $('.pagination #page_4').css('display', 'none');

    if (pageNumber + 4 <= maxPageNumber) {
        $('.pagination #page_5 span').text(pageNumber + 4);
        $('.pagination #page_5').on("click",
        function() {
            onSelectPageFunction(pageNumber + 4, pageSize);
            setCurrentPageClass(pageNumber + 4);
        });
        $('.pagination #page_5').css('display', 'block');
    } else $('.pagination #page_5').css('display', 'none');

    if (pageNumber == 1) {
        $('.pagination #page_previous').css('display', 'none');
        $('.pagination #page_next').css('display', 'block');
        $('.pagination #page_next').on("click",
        function() {
            setCurrentPageClass(1);
            nextPagination(pageNumber, pageSize, totalNumber, onSelectPageFunction);
        });
    } else if (pageNumber + 5 >= maxPageNumber) {
        $('.pagination #page_previous').css('display', 'block');
        $('.pagination #page_next').css('display', 'none');
        $('.pagination #page_previous').on("click",
        function() {
            setCurrentPageClass(5);
            previousPagination(pageNumber, pageSize, totalNumber, onSelectPageFunction);
        });
    } else {
        $('.pagination #page_previous').css('display', 'block');
        $('.pagination #page_next').css('display', 'block');
        $('.pagination #page_next').on("click",
        function() {
            setCurrentPageClass(1);
            nextPagination(pageNumber, pageSize, totalNumber, onSelectPageFunction);
        });
        $('.pagination #page_previous').on("click",
        function() {
            setCurrentPageClass(5);
            previousPagination(pageNumber, pageSize, totalNumber, onSelectPageFunction);
        });
    }
}

function nextPagination(pageNumber, pageSize, totalNumber, onSelectPageFunction) {
    onSelectPageFunction(pageNumber + 5, pageSize);
    initPagination(pageNumber + 5, pageSize, totalNumber, onSelectPageFunction);
}

function previousPagination(pageNumber, pageSize, totalNumber, onSelectPageFunction) {
    onSelectPageFunction(pageNumber - 1, pageSize);
    initPagination(pageNumber - 5, pageSize, totalNumber, onSelectPageFunction);
}

function setCurrentPageClass(pageNumber, pageSize) {
    $('.pagination ul li span').removeClass('active');
    var index = pageNumber % 5;
    if (index == 0) $('.pagination #page_5 span').addClass('active');
    else $('.pagination #page_' + index + ' span').addClass('active');
}