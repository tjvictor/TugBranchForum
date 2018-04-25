;
(function($){

var PaginationObj = function(ele, opt){

    this.$element = ele,
    this.defaults = {
        pageNumber: 1,
        pageSize: 10,
        pageTabSize: 5,
        totalNumber: 0,
        onSelectPage: function(pageNumber, pageSize){},
    },
    this.options = $.extend({}, this.defaults, opt);
}

PaginationObj.prototype = {
    createPagination: function(){
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

                for(var index = 2; index <= this.options.pageTabSize; index++){
                    content += '    <li id="page_'+index+'">';
                    content += '        <span>'+index+'</span>';
                    content += '    </li>';
                }

                content += '    <li  id="page_next">';
                content += '        <span title="下一页">></span>';
                content += '    </li>';
                content += '    <li id="page_last">';
                content += '        <span title="最后一页">»</span>';
                content += '    </li>';
                content += '   </ul>';
                if(this.options.totalNumber>0){
                    this.$element.html(content);
                    initPagination(this.$element, this.options.pageNumber, this.options.pageSize, this.options.pageTabSize, this.options.totalNumber, this.options.onSelectPage);
                }
                return this.$element;
    }
}

    function initPagination(obj, pageNumber, pageSize, pageTabSize, totalNumber, onSelectPageFunction){
                var maxPageNumber = Math.ceil(totalNumber/pageSize);
                obj.find('ul li').off("click");

                obj.find('#page_first').on("click", function(){
                    onSelectPageFunction(1, pageSize);
                    setCurrentPageClass(obj, 1, pageTabSize);
                    initPagination(obj, 1, pageSize, pageTabSize, totalNumber, onSelectPageFunction);
                });
                obj.find('#page_last span').attr('title', '最后一页:'+maxPageNumber+'页');
                obj.find('#page_last').on("click", function(){
                    onSelectPageFunction(maxPageNumber, pageSize);
                    setCurrentPageClass(obj, maxPageNumber, pageTabSize);
                    var pageInterval = maxPageNumber%pageTabSize==0?pageTabSize-1:maxPageNumber%pageTabSize-1;
                    initPagination(obj, maxPageNumber-pageInterval, pageSize, pageTabSize, totalNumber, onSelectPageFunction);
                });

                for(var indexTab = 1; indexTab <= pageTabSize; indexTab++){
                    if((pageNumber+indexTab-1) <= maxPageNumber){
                        obj.find('#page_'+indexTab+' span').text(pageNumber+indexTab-1);
                        obj.find('#page_'+indexTab).on("click", pageNumber+indexTab-1, function(event){
                            onSelectPageFunction(event.data, pageSize);
                            setCurrentPageClass(obj, event.data, pageTabSize);
                        });
                        obj.find('#page_'+indexTab).css('display','block');
                    }else
                        obj.find('#page_'+indexTab).css('display','none');

                }

                if(pageNumber==1){
                    obj.find('#page_previous').css('display','none');
                    if(maxPageNumber>pageTabSize){
                        obj.find('#page_next').css('display', 'block');
                        obj.find('#page_next').on("click",
                        function() {
                            setCurrentPageClass(obj, 1, pageTabSize);
                            nextPagination(obj, pageNumber, pageSize, pageTabSize, totalNumber, onSelectPageFunction);
                        });
                    } else
                        obj.find('#page_next').css('display', 'none');
                }else if(pageNumber+pageTabSize>=maxPageNumber){
                    obj.find('#page_previous').css('display','block');
                    obj.find('#page_next').css('display','none');
                    obj.find('#page_previous').on("click", function(){
                        setCurrentPageClass(obj, pageTabSize, pageTabSize);
                        previousPagination(obj, pageNumber, pageSize, pageTabSize, totalNumber, onSelectPageFunction);
                    });
                }else{
                    obj.find('#page_previous').css('display','block');
                    obj.find('#page_next').css('display','block');
                    obj.find('#page_next').on("click", function(){
                        setCurrentPageClass(obj, 1, pageTabSize);
                        nextPagination(obj, pageNumber, pageSize, pageTabSize, totalNumber, onSelectPageFunction);
                    });
                    obj.find('#page_previous').on("click", function(){
                        setCurrentPageClass(obj, pageNumber, pageTabSize);
                        previousPagination(obj, pageNumber, pageSize, pageTabSize, totalNumber, onSelectPageFunction);
                    });
                }
            }

            function nextPagination(obj, pageNumber, pageSize, pageTabSize, totalNumber, onSelectPageFunction){
                onSelectPageFunction(pageNumber+pageTabSize, pageSize);
                initPagination(obj, pageNumber+pageTabSize, pageSize, pageTabSize, totalNumber, onSelectPageFunction);
            }

            function previousPagination(obj, pageNumber, pageSize, pageTabSize, totalNumber, onSelectPageFunction){
                onSelectPageFunction(pageNumber-1, pageSize);
                initPagination(obj, pageNumber-pageTabSize, pageSize, pageTabSize, totalNumber, onSelectPageFunction);
            }

            function setCurrentPageClass(obj, pageNumber, pageTabSize){
                obj.find('ul li span').removeClass('active');
                var index = pageNumber%pageTabSize;
                if(index==0){
                    obj.find('#page_'+pageTabSize+' span').addClass('active');
                }
                else{
                    obj.find('#page_'+index+' span').addClass('active');
                }
            }


$.fn.pagination = function(opt){

    var paginationObj = new PaginationObj(this, opt);

    return paginationObj.createPagination();

}

})(jQuery);