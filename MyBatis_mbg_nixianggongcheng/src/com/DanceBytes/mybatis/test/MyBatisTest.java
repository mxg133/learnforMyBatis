package com.DanceBytes.mybatis.test;

import com.DanceBytes.mybatis.bean.Employee;
import com.DanceBytes.mybatis.bean.EmployeeExample;
import com.DanceBytes.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 孟享广
 * @date 2020-11-26 11:58 上午
 * @description
 */
public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testMbg() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("mbg.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
    }

    @Test
    public void test2() throws IOException {
        SqlSession openSession = getSqlSessionFactory().openSession();
        EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.selectByPrimaryKey(1);
        System.out.println(employee);
        openSession.close();
    }

    @Test
    public void test3() throws IOException {
        SqlSession openSession = getSqlSessionFactory().openSession();
        EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
        //xxxExample就是封装查询条件的
        //1、查询所有
//        List<Employee> list = mapper.selectByExample(null);

        //2、查询员工名字中有e字母的，和员工性别是1的
        EmployeeExample example = new EmployeeExample();
        //创建一个Criteria，这个Criteria就是拼装查询条件
        //select id, last_name, email, gender, d_id from tbl_employee
        //WHERE ( last_name like ? and gender = ? ) or email like "%e%"
        EmployeeExample.Criteria criteria1 = example.createCriteria();
        criteria1.andLastNameLike("%e%");
        criteria1.andGenderEqualTo("1");

        EmployeeExample.Criteria criteria2 = example.createCriteria();
        criteria2.andEmailLike("%e%");

        example.or(criteria2);

        mapper.selectByExample(example);
        //封装员工查询条件的example
        List<Employee> list = mapper.selectByExample(example);
        for (Employee e: list) {
            System.out.println(e);
        }
        openSession.close();
    }
}
