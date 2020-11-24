package com.DanceBytes.mybatis.dao;

import java.util.List;

import com.DanceBytes.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapperDynamicSQL {
	
	//携带了哪个字段查询条件就带上这个字段的值
	public List<Employee> getEmpsByConditionIf(Employee employee);

	//Trim标签
	public List<Employee> getEmpsByConditionTrim(Employee employee);

	//Choose
	public List<Employee> getEmpsByConditionChoose(Employee employee);

	//set if
	public void updateEmployee(Employee employee);

	//foreach 查询
	public List<Employee> getEmpsByConditionForeach(@Param("ids")List<Integer> ids);

	//foreach 批量保存
	public void addEmployeesByForeach(@Param("employees")List<Employee> employees);
}
