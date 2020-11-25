package com.DanceBytes.mybatis.dao;

import com.DanceBytes.mybatis.bean.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 孟享广
 * @date 2020-11-22 11:04 上午
 * @description
 */

@Repository
public interface EmployeeMapper {
    //查询
    public Employee getEmployeeById(Integer id);

    public List<Employee> getEmployees();
}
