package com.DanceBytes.mybatis.test;

import com.DanceBytes.mybatis.bean.Department;
import com.DanceBytes.mybatis.bean.Employee;
import com.DanceBytes.mybatis.dao.DepartmentMapper;
import com.DanceBytes.mybatis.dao.EmployeeMapper;
import com.DanceBytes.mybatis.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 孟享广
 * @date 2020-11-22 10:17 上午
 * @description
 * 1、接口式编程
 * 	原生：		Dao		====>  DaoImpl
 * 	mybatis：	Mapper	====>  xxMapper.xml
 *
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 * 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * 		（将接口和xml进行绑定）
 * 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件：
 * 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * 		sql映射文件：保存了每一个sql语句的映射信息：
 * 					将sql抽取出来。
 */
public class MyBatisTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
    
    @Test
    public void test2() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.getEmployeeById(1);
        System.out.println(employee);
        openSession.close();
    }

    @Test
    public void testCRUD() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //1、获取到的SqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession();

        EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
        //测试查询方法1
        Employee employee1 = mapper.getEmployeeByIdAndLastName(1, "tom");
        System.out.println(employee1);
        //测试查询方法2
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("lastName", "tom");
        map.put("tableName", "tbl_employee");
        Employee employee2 = mapper.getEmployeeByMap(map);
        System.out.println(employee2);


        //测试查询4 返回集合
        List<Employee> list = mapper.getEmployeeByLastNameLike("%e%");
        for (Employee i: list) {
            System.out.println(i);
        }

        //查询5 返回map key 是列名，value 是对应值
        Map<String, Object> map1 = mapper.getEmployeeReturnMap(1);
        System.out.println(map1);

        //查询6 多条记录封装一个map Map<Integer, Employee>键是这条记录的主键，值是记录封装后的javaBean
        Map<Integer, Employee> map2 = mapper.getEmployeeLastNameLikeReturnMap("%r%");
        System.out.println(map2);

        Employee employee = new Employee(null, "jerry23", "jery@333.com", "1");
//        Employee employee = new Employee(2, "jerry2", "jery@333.com", "1");
        //测试添加
//        int i = mapper.addEmployee(employee);
//        System.out.println(employee.getId());

        //测试修改
//        int i = mapper.updateEmployee(employee);

        //测试删除
//        int i = mapper.deleteEmployee(2);

//        System.out.println("影响了 " + i + " 行");
        //手动提交
        openSession.commit();
    }


    @Test
    public void test3() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);

        Employee employee = mapper.getEmployee(1);
        System.out.println(employee);

        //联合查询1
        Employee employee1 = mapper.getEmployeeAndDept1(1);
        System.out.println(employee1);
        System.out.println(employee1.getDept());

        //联合查询2
        Employee employee2 = mapper.getEmployeeAndDept2(1);
        System.out.println(employee2);
        System.out.println(employee2.getDept());

        //分步查询3
        Employee employee3 = mapper.getEmployeeByIdStep(1);
        System.out.println(employee3);
        System.out.println(employee3.getDept());

        //分段查询
        Employee employee4 = mapper.getEmployeeByIdStep(1);
        System.out.println(employee4.getLastName());
        System.out.println(employee3.getDept().getDepartmentName());
        openSession.close();
    }

    @Test
    public void test4() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);

        Department department = mapper.getDeptByIdPlus(1);
        System.out.println(department);
        System.out.println(department.getEmployees());
        openSession.close();
    }

    /**
     * 分段查询 list
     * @throws IOException
     */
    @Test
    public void test5() throws Exception {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);

        Department department = mapper.getDeptByIdStep(1);
        System.out.println(department);
        System.out.println(department.getEmployees());
        openSession.close();
    }
}
