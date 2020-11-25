package com.DanceBytes.mybatis.controller;

import com.DanceBytes.mybatis.bean.Employee;
import com.DanceBytes.mybatis.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author 孟享广
 * @date 2020-11-25 6:15 下午
 * @description
 */

@Controller
public class EmployeesController {

    @Autowired
    EmployeesService employeesService;

    @RequestMapping(value = "employees")
    public String employees(Map<String, Object> map) {
        List<Employee> list = employeesService.getEmployees();
        map.put("list", list);
        return "success";
    }
}
