package com.DanceBytes.mybatis.service;

import com.DanceBytes.mybatis.bean.Employee;
import com.DanceBytes.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 孟享广
 * @date 2020-11-25 6:17 下午
 * @description
 */

@Service
public class EmployeesService {
    @Autowired
    public EmployeeMapper employeeMapper;

    @Autowired
    private SqlSession sqlSession;

    public List<Employee> getEmployees() {
        return employeeMapper.getEmployees();
    }
}
