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
 *  1、获取sqlSessionFactory对象:
 *  		解析文件的每一个信息保存在Configuration中，返回包含Configuration的DefaultSqlSession；
 *  		注意：【MappedStatement】：代表一个增删改查的详细信息
 *
 *  2、获取sqlSession对象
 *  		返回一个DefaultSQlSession对象，包含Executor和Configuration;
 *  		这一步会创建Executor对象；
 *
 *  3、获取接口的代理对象（MapperProxy）
 *  		getMapper，使用MapperProxyFactory创建一个MapperProxy的代理对象
 *  		代理对象里面包含了，DefaultSqlSession（Executor）
 *  4、执行增删改查方法
 *
 *  总结：
 *  	1、根据配置文件（全局，sql映射）初始化出Configuration对象
 *  	2、创建一个DefaultSqlSession对象，
 *  		他里面包含Configuration以及
 *  		Executor（根据全局配置文件中的defaultExecutorType创建出对应的Executor）
 *   3、DefaultSqlSession.getMapper（）：拿到Mapper接口对应的MapperProxy；
 *   4、MapperProxy里面有（DefaultSqlSession）；
 *   5、执行增删改查方法：
 *   		1）、调用DefaultSqlSession的增删改查（Executor）；
 *   		2）、会创建一个StatementHandler对象。
 *   			（同时也会创建出ParameterHandler和ResultSetHandler）
 *   		3）、调用StatementHandler预编译参数以及设置参数值;
 *   			使用ParameterHandler来给sql设置参数
 *   		4）、调用StatementHandler的增删改查方法；
 *   		5）、ResultSetHandler封装结果
 *   注意：
 *   	四大对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler);
 */

public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void buildMbg() throws Exception {
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

        try {
        EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.selectByPrimaryKey(1);
        System.out.println(employee);
        } finally {
            openSession.close();
        }
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
