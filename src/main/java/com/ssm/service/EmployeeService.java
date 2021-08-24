package com.ssm.service;

import com.ssm.pojo.Department;
import com.ssm.pojo.Employee;

import java.util.List;

public interface EmployeeService {
    /**
     * 查询全部用户
     * @return
     */
    public List<Employee> selectAllWithDept();

    /**
     * 通过 emp_id 查询用户
     * @return
     */
    public Employee selectByIdWithDept(Integer emp_id);

    /**
     * 保存一个员工
     * @param employee
     */
    public void saveOneEmployee(Employee employee);

    /**
     * 判断用户名是否可用
     * @param empName
     * @return
     */
    public boolean checkEmpName(String empName);

    /**
     * 通过 emp_id 查找用户
     * @param emp_id
     * @return
     */
    public Employee findEmployeeById(int emp_id);

    /**
     * 根据用户 id 更新用户
     * @param employee
     */
    public void updateEmployeeById(Employee employee);

    /**
     * 根据 id 删除用户
     * @param emp_id
     * @return
     */
    public boolean deleteEmployeeById(int emp_id);

    /**
     * 根据集合里的 id 删除多个
     * @param ids
     * @return
     */
    public boolean deleteMultipleById(List<Integer> ids);

}
