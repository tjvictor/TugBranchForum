; (function($) {

    var PaginationObj = function(ele, opt) {

        this.$element = ele,
        this.$element.removeClass();
        this.$element.addClass('myPagination');
        this.defaults = {
            pageNumber: 1,
            pageSize: 10,
            pageTabSize: 5,
            totalNumber: 0,
            onSelectPage: function(pageNumber, pageSize) {},
        },
        this.options = $.extend({}, this.defaults, opt);
    }

    PaginationObj.prototype = {
        createPagination: function() {
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

            for (var index = 2; index <= this.options.pageTabSize; index++) {
                content += '    <li id="page_' + index + '">';
                content += '        <span>' + index + '</span>';
                content += '    </li>';
            }

            content += '    <li  id="page_next">';
            content += '        <span title="下一页">></span>';
            content += '    </li>';
            content += '    <li id="page_last">';
            content += '        <span title="最后一页">»</span>';
            content += '    </li>';
            content += '   </ul>';
            if (this.options.totalNumber > 0) {
                this.$element.html(content);
                this.initPagination(1);
            }
            return this;
        },

        initPagination: function (pageNumber) {
            var maxPageNumber = Math.ceil(this.options.totalNumber / this.options.pageSize);
            this.$element.find('ul li').off("click");

            this.$element.find('#page_first').on("click", this, function(event) {
                event.data.options.onSelectPage(1, event.data.options.pageSize);
                event.data.setCurrentPageClass(1);
                event.data.initPagination(1);
            });
            this.$element.find('#page_last span').attr('title', '最后一页:' + maxPageNumber + '页');
            this.$element.find('#page_last').on("click", this, function(event) {
                event.data.options.onSelectPage(maxPageNumber, event.data.options.pageSize);
                event.data.setCurrentPageClass(maxPageNumber);
                var pageInterval = maxPageNumber % event.data.options.pageTabSize == 0 ? event.data.options.pageTabSize - 1 : maxPageNumber % event.data.options.pageTabSize - 1;
                event.data.initPagination(maxPageNumber - pageInterval);
            });

            for (var indexTab = 1; indexTab <= this.options.pageTabSize; indexTab++) {
                if ((pageNumber + indexTab - 1) <= maxPageNumber) {
                    this.$element.find('#page_' + indexTab + ' span').text(pageNumber + indexTab - 1);
                    this.$element.find('#page_' + indexTab).on("click", {pageNumber: pageNumber + indexTab - 1, obj:this}, function(event) {
                        event.data.obj.options.onSelectPage(event.data.pageNumber, event.data.obj.options.pageSize);
                        event.data.obj.setCurrentPageClass(event.data.pageNumber);
                    });
                    this.$element.find('#page_' + indexTab).css('display', 'block');
                } else
                    this.$element.find('#page_' + indexTab).css('display', 'none');

            }

            if (pageNumber == 1) {
                this.$element.find('#page_previous').css('display', 'none');
                if (maxPageNumber > this.options.pageTabSize) {
                    this.$element.find('#page_next').css('display', 'block');
                    this.$element.find('#page_next').on("click", {pageNumber: pageNumber, obj:this}, function(event) {
                        event.data.obj.setCurrentPageClass(event.data.pageNumber);
                        event.data.obj.nextPagination(event.data.pageNumber);
                    });
                } else this.$element.find('#page_next').css('display', 'none');
            } else if (pageNumber + this.options.pageTabSize >= maxPageNumber) {
                this.$element.find('#page_previous').css('display', 'block');
                this.$element.find('#page_next').css('display', 'none');
                this.$element.find('#page_previous').on("click", {pageNumber: pageNumber, obj:this}, function(event) {
                    event.data.obj.setCurrentPageClass(event.data.pageNumber);
                    event.data.obj.previousPagination(event.data.pageNumber);
                });
            } else {
                this.$element.find('#page_previous').css('display', 'block');
                this.$element.find('#page_next').css('display', 'block');
                this.$element.find('#page_next').on("click", {pageNumber: pageNumber, obj:this}, function(event) {
                    event.data.obj.setCurrentPageClass(event.data.pageNumber);
                    event.data.obj.nextPagination(event.data.pageNumber);
                });
                this.$element.find('#page_previous').on("click", {pageNumber: pageNumber, obj:this}, function(event) {
                    event.data.obj.setCurrentPageClass(event.data.pageNumber);
                    event.data.obj.previousPagination(event.data.pageNumber);
                });
            }
        },

        nextPagination: function (pageNumber) {
            this.options.onSelectPage(pageNumber + this.options.pageTabSize, this.options.pageSize);
            this.initPagination(pageNumber + this.options.pageTabSize);
        },

        previousPagination: function (pageNumber) {
            this.options.onSelectPage(pageNumber - 1, this.options.pageSize);
            this.initPagination(pageNumber - + this.options.pageTabSize);
        },

        setCurrentPageClass: function (pageNumber) {
            this.$element.find('ul li span').removeClass('active');
            var index = pageNumber % this.options.pageTabSize;
            if (index == 0) {
                this.$element.find('#page_' + this.options.pageTabSize + ' span').addClass('active');
            } else {
                this.$element.find('#page_' + index + ' span').addClass('active');
            }
        }
    }

    $.fn.pagination = function(opt) {

        var paginationObj = new PaginationObj(this, opt);

        return paginationObj.createPagination();

    }

})(jQuery);