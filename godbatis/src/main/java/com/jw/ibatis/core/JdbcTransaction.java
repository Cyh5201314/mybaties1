package com.jw.ibatis.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Jdbc管理器
 * 想要控制事务,必须要获取Connection对象
 * Connection对象一定是数据源中得到的,所以肯定有javax.sql.DataSource属性
 *
 */
public class JdbcTransaction implements Transaction{


    //获得Connection对象来控制事务,所以必须要有DataSourece来获得连接对象
    private DataSource dataSource;

    private boolean autoCommit;

    /**
     * 保证每次执行事务方法,都是同一个Connection对象
     */
    private Connection connection;

    public JdbcTransaction(DataSource dataSource, boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    @Override
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 保证每次执行事务方法,都是同一个Connection对象
     * 真正打开数据库连接
     */
    public void openConnection(){
        if (connection==null) {
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 对外提供事务的Connection对象来执行sql语句
     * @return
     */
    public Connection getConnection() {
        return connection;
    }
}
