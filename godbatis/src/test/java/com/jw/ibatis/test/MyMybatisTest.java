package com.jw.ibatis.test;

import com.jw.ibatis.core.SqlSession;
import com.jw.ibatis.core.SqlSessionFactory;
import com.jw.ibatis.core.SqlSessionFactoryBuilder;
import com.jw.ibatis.pojo.User;
import com.jw.ibatis.utils.Resource;
import org.junit.Test;

public class MyMybatisTest {

    @Test
    public void testInser(){
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.builder(Resource.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        User user = new User("1", "经威威", "18");
        int insert = sqlSession.insert("user.insertUser", user);
        sqlSession.commit();

    }

    @Test
    public void testSelectOne(){
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.builder(Resource.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        Object o  = sqlSession.selectOne("user.queryId", 1);
        System.out.println(o);

    }

    public static void main(String[] args) {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.builder(Resource.getResourceAsStream("mybatis-config.xml"));
        System.out.println(factory);

    }
}
