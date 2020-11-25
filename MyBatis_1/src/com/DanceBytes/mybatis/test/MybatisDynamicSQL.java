package com.DanceBytes.mybatis.test;

import com.DanceBytes.mybatis.bean.Department;
import com.DanceBytes.mybatis.bean.Employee;
import com.DanceBytes.mybatis.dao.EmployeeMapperDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 孟享广
 * @date 2020-11-24 3:48 下午
 * @description
 */
public class MybatisDynamicSQL {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    //Trim
    @Test
    public void test1() throws Exception {
        SqlSession openSession = getSqlSessionFactory().openSession();
        EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee(null, "%e%", "jery@333.com", null);
        List<Employee> list = mapper.getEmpsByConditionTrim(employee);
        for (Employee e: list) {
            System.out.println(e);
        }
        openSession.close();
    }

    //Choose
    @Test
    public void test2() throws Exception {
        SqlSession openSession = getSqlSessionFactory().openSession();
        EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee(3, null, null, null);
        List<Employee> list = mapper.getEmpsByConditionChoose(employee);
        for (Employee e: list) {
            System.out.println(e);
        }
        openSession.close();
    }

    //set if
    @Test
    public void test3() throws Exception {
        SqlSession openSession = getSqlSessionFactory().openSession();
        EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee(1, "admin2", null, null);
        mapper.updateEmployee(employee);
        openSession.commit();
        openSession.close();
    }

    //foreach 查询
    @Test
    public void test4() throws Exception {
        SqlSession openSession = getSqlSessionFactory().openSession();
        EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
        ArrayList<Integer> arrayList = new ArrayList<>();
        List<Employee> list = mapper.getEmpsByConditionForeach(Arrays.asList(1, 2, 3, 4));
        for (Employee e: list) {
            System.out.println(e);
        }
        openSession.close();
    }

    //foreach 批量保存
    @Test
    public void test5() throws Exception {
        SqlSession openSession = getSqlSessionFactory().openSession();
        EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "smith", "smith0x1@atguigu.com", "1",new Department(1)));
        employees.add(new Employee(1, "allen", "allen0x1@atguigu.com", "0",new Department(1)));
        mapper.addEmployeesByForeach(employees);
        openSession.commit();
        openSession.close();
    }

    //_parameter _databaseId
    @Test
    public void test6() throws Exception {
        SqlSession openSession = getSqlSessionFactory().openSession();
        EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee();
        List<Employee> list = mapper.getEmployeeTestInnerParameter(employee);
        for (Employee e: list) {
            System.out.println(e);
        }
        openSession.close();
    }

    //Bind
    @Test
    public void test7() throws Exception {
        SqlSession openSession = getSqlSessionFactory().openSession();
        EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee();
        employee.setLastName("e");
        List<Employee> list = mapper.getEmployeeTestInnerParameterBind(employee);
        for (Employee e: list) {
            System.out.println(e);
        }
        openSession.close();
    }


}
