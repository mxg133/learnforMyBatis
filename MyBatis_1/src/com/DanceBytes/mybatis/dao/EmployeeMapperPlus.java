package com.DanceBytes.mybatis.dao;

import com.DanceBytes.mybatis.bean.Employee;

import java.util.List;

/**
 * @author 孟享广
 * @date 2020-11-23 2:22 下午
 * @description
 */
public interface EmployeeMapperPlus {
    public Employee getEmployee(Integer id);


    //联合查询1
    public Employee getEmployeeAndDept1(Integer id);
    //联合查询2
    public Employee getEmployeeAndDept2(Integer id);//association
    //分步查询
    public Employee getEmployeeByIdStep(Integer id);

    //分步查询 list
    public List<Employee> getEmployeeByDeptId(Integer id);
}
