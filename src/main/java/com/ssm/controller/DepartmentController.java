package com.ssm.controller;

import com.ssm.pojo.Department;
import com.ssm.utils.Msg;

import java.util.List;

public interface DepartmentController {
    /**
     * 获取所有部门的信息
     * @return
     */
    public Msg getAllDepartment();

}
