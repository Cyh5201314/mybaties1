package com.jw.ibatis.core;

import java.util.Map;

/**
 * 一个环境(一个数据库) 对应一个SqlSessionFactory对象
 */
public class SqlSessionFactory {


    private Transaction transaction;

    private Map<String,MappingStatement> mappingStatements;


    public SqlSessionFactory(Transaction transaction, Map<String, MappingStatement> mappingStatements) {
        this.transaction = transaction;
        this.mappingStatements = mappingStatements;
    }

    public SqlSession openSession(){
        //打开一个连接
        transaction.openConnection();
        SqlSession sqlSession = new SqlSession(this);
        return sqlSession;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Map<String, MappingStatement> getMappingStatements() {
        return mappingStatements;
    }
}
