package com.jw.ibatis.core;

import java.sql.Connection;

/**
 * 会务管理器接口
 * 所有的事务管理器都应该遵循该规范
 * Jdbc事务管理器和Managed事务管理器都应该实现这个接口
 * Transaction 提供事务管理的方法
 *
 */
public interface Transaction {


    void commit();

    void rollback();

    void close();

    Connection getConnection();

    void openConnection();
}
