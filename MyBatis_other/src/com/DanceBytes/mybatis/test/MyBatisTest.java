package com.DanceBytes.mybatis.test;

import com.DanceBytes.mybatis.bean.EmpStatus;
import com.DanceBytes.mybatis.bean.Employee;
import com.DanceBytes.mybatis.dao.EmployeeMapper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


    //分页查询 插件
    @Test
    public void test1() throws IOException {
        SqlSession openSession = getSqlSessionFactory().openSession();
        EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
//        Page<Object> page = PageHelper.startPage(1, 2);
        List<Employee> list = mapper.getEmployees();

        //传入要连续显示多少页
        PageInfo<Employee> info = new PageInfo<>(list, 5);

        for (Employee e: list) {
            System.out.println(e);
        }
//        System.out.println("page....");
//        System.out.println("当前页码： " + page.getPageNum());
//        System.out.println("总记录数： " + page.getTotal());
//        System.out.println("每页记录数： " + page.getPageSize());
//        System.out.println("总页码： " + page.getPages());

        System.out.println("info....");
        System.out.println("当前页码： " + info.getPageNum());
        System.out.println("总记录数： " + info.getTotal());
        System.out.println("每页记录数： " + info.getPageSize());
        System.out.println("总页码： " + info.getPages());
        System.out.println("首页？： " + info.isIsFirstPage());
        System.out.println("尾页？： " + info.isIsLastPage());

        System.out.println("连续显示的页码：");
        int[] nums = info.getNavigatepageNums();
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);

        }
        System.out.println();
        openSession.close();
    }

    //批量保存
    @Test
    public void test2() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //可以执行批量操作的sqlSession
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        long start = System.currentTimeMillis();

        EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i < 10; i++) {
            mapper.addEmployee(new Employee(UUID.randomUUID().toString().substring(0, 5), "b", "1"));
        }
        openSession.commit();
        long end = System.currentTimeMillis();
        //批量：（预编译sql一次==>设置参数===>10000次===>执行（1次））
        //Parameters: 616c1(String), b(String), 1(String)==>4598
        //非批量：（预编译sql=设置参数=执行）==》10000    10200
        System.out.println("执行时长："+(end-start));

        openSession.close();
    }

    /**
     * 默认mybatis在处理枚举对象的时候保存的是枚举的名字：EnumTypeHandler
     * 改变使用：EnumOrdinalTypeHandler：
     * 自定义类型
     */
    @Test
    public void test3() throws IOException {
        SqlSession openSession = getSqlSessionFactory().openSession();
        EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

//        Employee employee = new Employee("enum111", "n", "1");
//        mapper.addEmployee(employee);
//        System.out.println(employee.getId());

//        openSession.commit();
        openSession.close();
    }

    @Test
    public void testEnum(){
        EmpStatus login = EmpStatus.LOGIN;
        System.out.println(login);
        System.out.println("Enum的 索引： " + login.ordinal());
        System.out.println("Enum的 名字： " + login.name());
        System.out.println("Enum的 状态码： " + login.getCode());
        System.out.println("Enum的 提示消息： " + login.getMsg());
    }
}
