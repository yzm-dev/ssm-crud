package com.ssm.service;

import com.ssm.pojo.Department;

import java.util.List;

public interface DepartmentService {

    /**
     * 获取所有部门信息
     * @return
     */
    public List<Department> getAllDepartment();
}
