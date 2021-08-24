package com.ssm.controller;

import com.ssm.pojo.Employee;
import com.ssm.utils.Msg;
import org.springframework.validation.BindingResult;


public interface EmployeeController {
    public Msg test();
    /**
     * 跳转到初始页面
     * @return
     */
    public String jumpIndex();

    /**
     * 返回所有用户的json格式集合
     * @return
     */
    public Msg selectAllWithDept(int pageNum);

    /**
     * 返回 emp_id 用户的json格式
     * @return
     */
    public Employee selectByIdWithDept(Integer emp_id);

    /**
     * 保存一个员工
     * @param employee
     * @return 返回 Msg
     */
    public Msg saveOneEmployee(Employee employee, BindingResult result);

    /**
     * 判断用户名是否可用
     * @param empName
     * @return
     */
    public Msg checkEmpName(String empName);

    /**
     * 通过 id 查找用户
     * @param id
     * @return
     */
    public Msg findEmployeeById(int id);

    /**
     * 根据用户的 id 更新用户
     * @param employee
     * @return
     */
    public Msg updateEmployeeById(Employee employee);

    /**
     * 根据 id 删除用户
     * @param ids
     * @return
     */
    public Msg deleteEmployeeById(String ids);

}
