window.addEventListener('DOMContentLoaded', function () {
    // 获取web的根路径
    var webPath = $('#webPath').val();
    // 开始跳转到第一页
    toPage(1);
    //获取分页的最右一页
    var lastPageNum;
    //获取分页的当前页
    var currentPageNum;
    // 添加背景图片
    var imgPath = webPath + '/images/backImag2.jpg';
    $(document.body).css("background", "url(" + imgPath + ") no-repeat fixed center");
    $(document.body).css("background-size", "cover");
    //跳转到第几页
    function toPage(pageNum) {
        // 发送 ajax 获取员工信息
        $.ajax({
            url: webPath + "/selectAllByPage",
            data: {"pageNum": pageNum},
            type: "GET",
            success: function (result) {
                // 每次添加前清空表单体
                $('#emp_table tbody').empty();
                var emps = result.extend.pageInfo.list;
                $.each(emps, function (index, item) {
                    var emp_checked = $('<td><input type="checkbox" class="check_item"></td>');
                    var emp_id = $('<td></td>').append(item.empId);
                    var name = $('<td></td>').append(item.name);
                    var gender = $('<td></td>').append(item.gender=='M'? '男' : '女');
                    var email = $('<td></td>').append(item.email);
                    var department = $('<td></td>').append(item.department.department)
                    var updateBtn = $('<button type="button"></button>').addClass("btn btn-info update_emp_btn").append($('<span></span>').addClass("glyphicon glyphicon-pencil")).append(" 更新");
                    updateBtn.attr("update_emp_id", item.empId);
                    var deleteBtn = $('<button type="button"></button>').addClass("btn btn-danger delete_emp_btn").append($('<span></span>').addClass("glyphicon glyphicon-trash")).append(" 删除");
                    deleteBtn.attr("delete_emp_id", item.empId);
                    var operator = $('<td></td>').append(updateBtn).append(" ").append(deleteBtn);
                    $('<tr></tr>').append(emp_checked).append(emp_id).append(name).append(gender).append(email).append(department).append(operator).appendTo('#emp_table tbody');
                });

                // 显示分页信息
                build_page_info(result);
                // 显示分页条
                build_page_nav(result);
                //防点击全选删除后回到新页面全选还被选中
                $('.check_all').prop("checked", $('.check_item').length === $('.check_item:checked').length);
            }
        });
    }

    //解析显示分页信息
    function build_page_info(result) {
        // 获取最后一页数目
        lastPageNum = result.extend.pageInfo.pages;
        currentPageNum = result.extend.pageInfo.pageNum;
        // 每次添加前清空分页信息
        $('#page_info_area').empty();
        $('#page_info_area').append($('<h2></h2>').css({"margin-top": "26px", "margin-left": "-40px"}).append("第 " + result.extend.pageInfo.pageNum +" 页" + "， 共 " + result.extend.pageInfo.pages + " 页，总计 " + result.extend.pageInfo.total + " 条记录"));
    }

    //解析显示分页条
    function build_page_nav(result) {
        // 每次添加前清空分页条
        $('#page_nav_area').empty();
        // 首页
        var firstPage = $('<li></li>').append($('<a href="#">首页</a>'));
        // 末页
        var lastPage = $('<li></li>').append($('<a href="#">末页</a>'));
        // 上一页
        var prePage = $('<li></li>').append($('<a href="#" aria-label="Previous"></a>').append($('<span aria-hidden="true">&laquo;</span>')));
        // 下一页
        var nextPage = $('<li></li>').append($('<a href="#" aria-label="Next"></a>').append($('<span aria-hidden="true">&raquo;</span>')));
        // 分页添加
        var nav_ul = $('<ul class="pagination pagination-lg"></ul>').appendTo($('<nav></nav>'));
        nav_ul.append(firstPage).append(prePage);
        // 遍历分页信息
        $.each(result.extend.pageInfo.navigatepageNums, function(index, item) {
            var whatPage = $('<li></li>').append($('<a href="#">' + item +'</a>'));
            if (result.extend.pageInfo.pageNum === item) {
                whatPage.addClass("active");
            }
            //添加点击事件
            whatPage.click(function() {
                toPage(item);
            })
            nav_ul.append(whatPage);
        });
        nav_ul.append(nextPage).append(lastPage).appendTo('#page_nav_area');
        //没有上一页
        if (result.extend.pageInfo.hasPreviousPage == false) {
            firstPage.addClass("disabled");
            prePage.addClass("disabled");
        } else {
            //添加单击事件
            firstPage.click(function() {
                toPage(1);
            });
            prePage.click(function() {
                toPage(result.extend.pageInfo.pageNum - 1);
            });
        }
        //没有下一页
        if (result.extend.pageInfo.hasNextPage == false) {
            lastPage.addClass("disabled");
            nextPage.addClass("disabled");
        } else {
            //添加单击事件
            lastPage.click(function() {
                toPage(result.extend.pageInfo.pages);
            });
            nextPage.click(function() {
                toPage(result.extend.pageInfo.pageNum + 1);
            });
        }
    }

    //点击新增员工弹出模态框
    $('#emp_add_btn').click(function() {
        //员工添加表单清空
        reset_form("#empAddModal form");
        // 部门选择框获取部门
        getAllDepartment('#dept_select');
        $('#empAddModal').modal({
            backdrop: "static"
        });
        // 新增员工邮箱离焦验证
        blur_validate_email('#inputEmpEmail');
    });

    //员工添加表单清空
    function reset_form(ele) {
        // 清空新增员工表单
        $(ele)[0].reset();
        // 清空 输入框的检验状态
        $(ele).find("*").removeClass("has-success has-error");
        $(ele).find(".help-block").text("");
        //保存按钮的转态置位 false
        $('#emp_save_btn').attr("ajax-vx", "error");
    }

    // 获取所有部门信息
    function getAllDepartment(ele) {
        $(ele).empty();
        $.ajax({
            url: webPath + "/getAllDepartment",
            type: "GET",
            success: function(result) {
                $.each(result.extend.departments, function(index, item) {
                    // 插入部门选项
                    $('<option></option>').append(this.department).prop("value", this.depId).appendTo($(ele));
                });
            }
        });
    }

    //点击保存员工信息
    $('#emp_save_btn').click(function() {
        // // 进行表单验证
        if (!validate_emp_add_form()) {
            // 检验不通过不发请求
            return false;
        }
        // 判断用户名检验是否成功
        if ($('#emp_save_btn').attr("ajax-vx") == "error") {
            // 用户名存在提示
            show_validate_msg("#inputEmpName", "error","用户名不可用");
            return false;
        }
        $.ajax({
            url: webPath + "/saveOneEmployee",
            data: $('#emp_save_form').serialize(),
            type: "POST",
            success: function(result) {
                // 判断后端是否检验通过
                if (result.status == 200) {
                    // 关闭模态框
                    $('#empAddModal').modal('hide')
                    //跳转到最右一页
                    toPage(lastPageNum + 1);
                } else {
                    if (undefined != result.extend.errorFiled.email) {
                        // 显示邮箱的错误信息
                        show_validate_msg("#inputEmpEmail", "error", result.extend.errorFiled.email);
                    }
                    if (undefined != result.extend.errorFiled.name) {
                        // 显示员工的错误信息
                        show_validate_msg("#inputEmpName", "error", result.extend.errorFiled.name);
                    }
                }
            }
        })
    });

    // 保存员工表单验证
    function validate_emp_add_form() {
        var empName = $('#inputEmpName').val();
        var empEmail = $('#inputEmpEmail').val();
        var regName = /(^[a-zA_Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,6}$)/;
        var regEmail = /^([a-zA_Z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        //检验姓名
        if(!regName.test(empName)) {
            show_validate_msg("#inputEmpName", "error", "用户名为 6-16 位英文字符或 2-6 位汉字！");

        } else {
            show_validate_msg("#inputEmpName", "success","");
        }
        // 检验邮箱
        if (!regEmail.test(empEmail)) {
            show_validate_msg("#inputEmpEmail", "error","邮箱格式错误！");
        } else {
            show_validate_msg("#inputEmpEmail", "success","");
        }
        // 返回检验状态
        return regName.test(empName) && regEmail.test(empEmail);
    }

    // 邮箱离焦验证
    function blur_validate_email(ele) {
        $(ele).blur(function() {
            var empEmail = $(ele).val();
            var regEmail = /^([a-zA_Z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
            if (!regEmail.test(empEmail)) {
                show_validate_msg(ele, "error","邮箱格式错误！");
                // 给更新按钮添加检验状态
                $('#emp_update_btn').attr("ajax-email", "error");
            } else {
                show_validate_msg(ele, "success","邮箱可用");
                // 给更新按钮添加检验状态
                $('#emp_update_btn').attr("ajax-email", "success");
            }
        });
    }
    // 验证用户名是否重复
    $('#inputEmpName').change(function() {
        $.ajax({
            url: webPath + "/checkEmpName",
            data: {"empName": this.value},
            type: "POST",
            success: function(result) {
                if (result.status == 200) {   // 用户名可用
                    var empName = $('#inputEmpName').val();
                    var regName = /(^[a-zA_Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,6}$)/;
                    if(!regName.test(empName)) {
                        show_validate_msg("#inputEmpName", "error", "用户名为 6-16 位英文字符或 2-6 位汉字！");
                    } else {
                        show_validate_msg("#inputEmpName", "success","用户名可用");
                        // 保存按钮上添加用户名可用信息
                        $('#emp_save_btn').attr("ajax-vx", "success");
                    }
                } else if (result.status == 500) {  // 用户名不可用
                    show_validate_msg("#inputEmpName", "error","用户名不可用");
                    // 保存按钮上添加用户名可用信息
                    $('#emp_save_btn').attr("ajax-vx", "error");
                }
            }
        });
    });

    //展示验证员工信息
    function show_validate_msg(ele, status, msg) {
        // 清楚当前元素的检验状态
        $(ele).parent().removeClass("has-success has-error");
        // 检验成功
        if ("success" == status) {
            $(ele).parent().addClass("has-success");
            $(ele).next("span").text(msg);
        } else if ("error" == status) {  //检验失败
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg);
        }
    }
    // 点击更新员工
    $(document).on("click", ".update_emp_btn", function () {
         // 部门选择框获取部门
         getAllDepartment('#update_dept_select');
         // 清楚更新按钮状态
        $('#emp_update_btn').attr("ajax-email", "error");
        // 弹出更新模态框
        $('#empUpdateModal').modal({
           backdrop: "static"
        });
        //获取要更新的员工信息
        getUpdateEmp($(this).attr("update_emp_id"));
        // 更新员工邮箱离焦验证
        blur_validate_email('#updateEmpEmail');
    });
    let startEmail;
    //获取要更新的员工信息
    function getUpdateEmp (emp_id) {
        $.ajax({
            url: webPath + "/findById/" + emp_id,
            type: "GET",
            success: function(result) {
                var empData = result.extend.update_emp;
                // 将员工 id  放在更新按钮上
                $('#emp_update_btn').attr("update_emp_id", empData.empId);
                $('#update_emp_name').text(empData.name);
                $('#updateEmpEmail').val(empData.email);
                (empData.gender == 'M') ? $('#empUpdateModal input[name=gender]').val([empData.gender]) : $('#empUpdateModal input[name=gender]').val([empData.gender]);
                $('#empUpdateModal select').val([empData.dId]);
                //获取邮箱
                startEmail = $('#updateEmpEmail').val();
            }
        })
    }

    // 点击确认更新员工
    $('#emp_update_btn').click(function(){
        if ($('#emp_update_btn').attr("ajax-email") == "success") {
            // 发送 ajax 更新
            $.ajax({
                url: webPath + "/updateEmpById/" + $(this).attr("update_emp_id"),
                type: "PUT",
                data: $('#emp_update_form').serialize(),
                success: function (result) {
                    // 关闭对话框
                    $('#empUpdateModal').modal("hide");
                    // 回到本页面
                    toPage(currentPageNum);
            }
            });
        } else {
            // 邮箱格式不正确
            show_validate_msg("#updateEmpEmail", "error","邮箱格式有误,不能更新！");
        }
        // 如果邮箱未发生变化
        if ($('#updateEmpEmail').val() === startEmail) {
            // 邮箱无变化
            show_validate_msg("#updateEmpEmail", "error","邮箱没有变化！");
        }
    });

    // 给删除按钮绑定事件
    $(document).on("click", ".delete_emp_btn", function () {

       var empName = $(this).parents("tr").find("td:eq(2)").text();
       var empId=  $(this).attr("delete_emp_id");
    if (confirm("确认删除【" + empName + "】吗?")) {
        // 确认删除，发送 ajax
        $.ajax({
            url: webPath + "/deleteEmpById/" + empId,
            type: "DELETE",
            success: function (result) {
                // 回到当前页
                toPage(currentPageNum);
            }
        })
    }
    });

    // 设置全选全不选
   $('.check_all').click(function () {
       $('.check_item').prop("checked", $(this).prop("checked"));
   });
   //为每个单选绑定点击事件
    $(document).on("click",'.check_item',function () {
        // 判断是否全选
        var flag = $('.check_item').length === $('.check_item:checked').length;
        $('.check_all').prop("checked", flag);
    });

    // 删除选中
    $('#delete_select_btn').click(function () {
        var empNames = "";
        var del_idstr = "";
        $.each($(".check_item:checked"), function(index, item){
            empNames += $(this).parents("tr").find("td:eq(2)").text() + ", ";
            // 组装员工 id 字符串
            del_idstr += $(this).parents("tr").find("td:eq(1)").text() + "-";
        });
            empNames = empNames.substring(0, empNames.length - 2);
            del_idstr = del_idstr.substring(0, del_idstr.length - 1);
            if (confirm("确认删除【" + empNames + "】吗？")) {
                // 发送 ajax
                $.ajax({
                   url: webPath + "/deleteEmpById/" + del_idstr,
                    type:"DELETE",
                    success: function (result) {
                        // 回到当前页面
                        toPage(currentPageNum);
                    }
                });
            }

    });

})