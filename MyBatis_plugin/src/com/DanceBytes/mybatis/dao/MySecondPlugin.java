package com.DanceBytes.mybatis.dao;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * @author 孟享广
 * @date 2020-11-26 5:51 下午
 * @description
 */

//完成插件签名：告诉MyBatis当前插件用来拦截哪个对象的哪个方法
@Intercepts(
        {
                @Signature(type = StatementHandler.class,
                        method = "parameterize",
                        args = java.sql.Statement.class)
        }
)
public class MySecondPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MySecondPlugin...intercept:"+invocation.getMethod());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("MySecondPlugin...plugin:"+target);
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
