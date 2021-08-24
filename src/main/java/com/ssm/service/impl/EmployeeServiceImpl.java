package com.ssm.service.impl;

import com.ssm.mapper.EmployeeMapper;
import com.ssm.pojo.Department;
import com.ssm.pojo.Employee;
import com.ssm.pojo.EmployeeExample;
import com.ssm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询全部用户信息
     * @return
     */
    public List<Employee> selectAllWithDept() {
        EmployeeExample example = new EmployeeExample();
        //根据 emp_id 增序排列
        example.setOrderByClause("emp_id ASC");
        return employeeMapper.selectByExampleWithDept(example);
    }

    /**
     * 通过 emp_id 查询用户
     * @return
     */
    public Employee selectByIdWithDept(Integer emp_id) {
        return employeeMapper.selectByPrimaryKeyWithDept(emp_id);
    }

    /**
     * 保存一个员工
     * @param employee
     */
    @Override
    public void saveOneEmployee(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    /**
     * 判断用户名是否可用
     * @param empName
     * @return 如果不存在返回true 否则返回false
     */
    @Override
    public boolean checkEmpName(String empName) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //判断用户名是否存在
        criteria.andNameEqualTo(empName);
        long l = employeeMapper.countByExample(example);
        return l == 0;
    }

    /**
     * 通过 emp_id 查找用户
     * @param emp_id
     * @return
     */
    public Employee findEmployeeById(int emp_id) {
        return employeeMapper.selectByPrimaryKey(emp_id);
    }

    /**
     * 根据用户 id 更新用户
     * @param employee
     */
    @Override
    public void updateEmployeeById(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 根据 id 删除用户
     * @param emp_id
     * @return
     */
    @Override
    public boolean deleteEmployeeById(int emp_id) {
        int flag = employeeMapper.deleteByPrimaryKey(emp_id);
        return flag != 0;
    }

    /**
     * 根据集合里的 id 删除多个
     * @param ids
     * @return
     */
    @Override
    public boolean deleteMultipleById(List<Integer> ids) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(ids);
        int flag = employeeMapper.deleteByExample(example);
        return flag != 0;
    }
}
