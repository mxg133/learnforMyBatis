package com.DanceBytes.mybatis.dao;

import com.DanceBytes.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author 孟享广
 * @date 2020-11-22 11:04 上午
 * @description
 */
public interface EmployeeMapper {
    public Employee getEmployeeByMap(Map<String, Object> map);
    //查询
    public Employee getEmployeeById(Integer id);
    public Employee getEmployeeByIdAndLastName(@Param("id")Integer id, @Param("lastName")String lastName);

    //增加
    public Integer addEmployee(Employee employee);
    //修改
    public Integer updateEmployee(Employee employee);
    //删除
    public Integer deleteEmployee(Integer id);
}
