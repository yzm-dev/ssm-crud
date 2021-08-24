package com.ssm.controller.impl;

import com.ssm.controller.DepartmentController;
import com.ssm.pojo.Department;
import com.ssm.service.DepartmentService;
import com.ssm.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentControllerImpl implements DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    /**
     * 获取所有部门信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAllDepartment")
    public Msg getAllDepartment() {
        List<Department> departmentList = departmentService.getAllDepartment();
        return Msg.success().add("departments", departmentList);
    }

}
