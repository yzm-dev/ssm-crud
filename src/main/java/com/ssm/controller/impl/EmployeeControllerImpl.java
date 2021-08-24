package com.ssm.controller.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.controller.EmployeeController;
import com.ssm.pojo.Employee;
import com.ssm.service.EmployeeService;
import com.ssm.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeControllerImpl implements EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 跳转到初始页面
     * @return
     */
    @RequestMapping("/")
    public String jumpIndex() {
        return "list";
    }

    /**
     * 返回所有用户的json格式集合
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectAllByPage")
    public Msg selectAllWithDept(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        //查询每页显示 5 条记录
        PageHelper.startPage(pageNum, 5);
        //查询所有员工
        List<Employee> employeeList = employeeService.selectAllWithDept();
        //连续显示的页数为 5
        PageInfo<Employee> pageInfo = new PageInfo<Employee>(employeeList, 5);

        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 返回 emp_id 用户的json格式
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectById/{id}")
    public Employee selectByIdWithDept(@PathVariable("id")Integer emp_id) {
        return employeeService.selectByIdWithDept(emp_id);
    }

    @ResponseBody
    @RequestMapping("/test")
    public Msg test() {
        return null;
    }

    /**
     * 保存一个员工
     * @param employee
     * @return 返回 Msg
     */
    @ResponseBody
    @RequestMapping(value = "/saveOneEmployee", method = RequestMethod.POST)
    public Msg saveOneEmployee(@Valid Employee employee, BindingResult result) {
        //如果校验失败
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            Map<String, Object> map = new HashMap<String, Object>();
            for (FieldError fieldError : fieldErrors) {
                System.out.println("错误的字段名: " + fieldError.getField());
                System.out.println("错误信息: " + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFiled", map);
        } else {
            employeeService.saveOneEmployee(employee);
            return Msg.success();
        }

    }

    /**
     * 判断用户名是否重复
     * @param empName
     * @return Msg 状态信息
     */

    @ResponseBody
    @RequestMapping("/checkEmpName")
    public Msg checkEmpName(@RequestParam("empName")String empName) {
        String regName = "(^[a-zA_Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,6}$)";
        if (!empName.matches(regName)) {
            return Msg.fail().add("value_msg", "用户名须为 6-16 位英文字符或 2-6 位汉字！");
        }
        return employeeService.checkEmpName(empName) ? Msg.success() : Msg.fail().add("value_msg", "用户名不可用");
    }

    /**
     * 通过 id 查找用户
     * @param id
     * @return Msg 对象
     */
    @ResponseBody
    @RequestMapping("/findById/{id}")
    public Msg findEmployeeById(@PathVariable("id")int  id) {
        Employee employee = employeeService.findEmployeeById(id);
        return Msg.success().add("update_emp", employee);
    }

    /**
     * 根据用户的 id 更新用户
     * @param employee
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateEmpById/{empId}", method = RequestMethod.PUT)
    public Msg updateEmployeeById(Employee employee) {
        employeeService.updateEmployeeById(employee);
        return Msg.success();
    }

    /**
     * 根据 id 删除用户
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteEmpById/{ids}", method = RequestMethod.DELETE)
    public Msg deleteEmployeeById(@PathVariable("ids")String ids) {
        if (ids.contains("-")) {
            //删除多个
            String[] str_ids = ids.split("-");
            List<Integer> del_list = new ArrayList<Integer>();
            for (String str_id : str_ids) {
                del_list.add(Integer.parseInt(str_id));
            }
            boolean flag = employeeService.deleteMultipleById(del_list);
            if (flag) {
                //删除成功
                return Msg.success();
            } else {
                //删除失败
                return Msg.fail();
            }

        } else {
            //删除单个
            int id = Integer.parseInt(ids);
            boolean flag = employeeService.deleteEmployeeById(id);
            if (flag) {
                //删除成功
                return Msg.success();
            } else {
                //删除失败
                return Msg.fail();
            }
        }
    }
}
