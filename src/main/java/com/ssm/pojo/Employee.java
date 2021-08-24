package com.ssm.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    private Integer empId;

    @Pattern(regexp = "(^[a-zA_Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,6}$)", message = "用户名须为 6-16 位英文字符或 2-6 位汉字！")
    private String name;

    private String gender;

    @Pattern(regexp = "(^([a-zA_Z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$)", message = "邮箱格式不正确!")
    private String email;

    private Integer dId;

    private Department department;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}