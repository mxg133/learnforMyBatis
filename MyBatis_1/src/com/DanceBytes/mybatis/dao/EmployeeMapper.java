package com.DanceBytes.mybatis.dao;

import com.DanceBytes.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 孟享广
 * @date 2020-11-22 11:04 上午
 * @description
 */
public interface EmployeeMapper {
    //查询6 多条记录封装一个map Map<Integer, Employee>键是这条记录的主键，值是记录封装后的javaBean
    //告诉MyBatis封装map用哪个属性id 还是 lastName....
    @MapKey("id")
    public Map<Integer, Employee> getEmployeeLastNameLikeReturnMap(String lastName);

    //查询5 返回map key 是列名，value 是对应值
    public Map<String, Object> getEmployeeReturnMap(Integer id);

    //查询4 返回list
    public List<Employee> getEmployeeByLastNameLike(String lastName);


    //推荐查询方式
    public Employee getEmployeeByMap(Map<String, Object> map);
    //查询
    public Employee getEmployeeById(Integer id);
    //当参数多了的时候，让配置文件可以写 单词
    public Employee getEmployeeByIdAndLastName(@Param("id")Integer id, @Param("lastName")String lastName);

    //增加
    public Integer addEmployee(Employee employee);
    //修改
    public Integer updateEmployee(Employee employee);
    //删除
    public Integer deleteEmployee(Integer id);
}
