package com.DanceBytes.mybatis.dao;

import com.DanceBytes.mybatis.bean.Department;

/**
 * @author 孟享广
 * @date 2020-11-23 4:26 下午
 * @description
 */
public interface DepartmentMapper {
    //场景1
    public Department getDeptById(Integer id);

    //场景2
    public Department getDeptByIdPlus(Integer id);

    //分步查询 list
    public Department getDeptByIdStep(Integer id);

}
