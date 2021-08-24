package com.ssm.service.impl;

import com.ssm.mapper.DepartmentMapper;
import com.ssm.pojo.Department;
import com.ssm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取所有部门信息
     * @return
     */
    public List<Department> getAllDepartment() {
        return departmentMapper.selectByExample(null);
    }
}
