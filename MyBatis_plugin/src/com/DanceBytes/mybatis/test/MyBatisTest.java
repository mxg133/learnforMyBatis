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
 * 插件原理
 *  在四大对象创建的时候
 *  1、每个创建出来的对象不是直接返回的，而是
 *  		interceptorChain.pluginAll(parameterHandler);
 *  2、获取到所有的Interceptor（拦截器）（插件需要实现的接口）；
 *  		调用interceptor.plugin(target);返回target包装后的对象
 *  3、插件机制，我们可以使用插件为目标对象创建一个代理对象；AOP（面向切面）
 *  		我们的插件可以为四大对象创建出代理对象；
 *  		代理对象就可以拦截到四大对象的每一个执行；
 *
 * 		public Object pluginAll(Object target) {
 * 		    for (Interceptor interceptor : interceptors) {
 * 		      target = interceptor.plugin(target);
 *                        }
 * 		    return target;
 * 		  }     *
 *
 * *****************************************************
 * 	 * 插件编写：
 * 	 * 1、编写Interceptor的实现类
 * 	 * 2、使用@Intercepts注解完成插件签名
 * 	 * 3、将写好的插件注册到全局配置文件中
 * 	 *
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
