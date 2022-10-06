package com.jw;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * Session工具类
 */
public class SessionUtil {



    private static SqlSessionFactory  sessionFactory;

    private SessionUtil(){}
    static {
        try {
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
    public SqlSession openSession() throws Exception{
        //1.创建 SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //2.一个environment对应一个SessionFactory,也对应一个数据库，所以不需要每次调用方法都获取SessionFactory
        //应该放到静态代码块中初始化
        SqlSessionFactory sessionFactory = builder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        return  sessionFactory.openSession();
    }
   **/

    public static SqlSession openSession(){
        return sessionFactory.openSession();
    }
}
