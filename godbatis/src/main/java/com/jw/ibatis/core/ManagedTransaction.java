package com.jw.ibatis.core;

import java.sql.Connection;

/**
 * 暂时就不实现了,主要实现JdbcTransaction
 */
public class ManagedTransaction implements Transaction{
    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void close() {

    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void openConnection() {

    }
}
