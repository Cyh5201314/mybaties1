package com.jw.ibatis.core;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.Map;

public class SqlSession {
    private SqlSessionFactory sessionFactory;

    public SqlSession(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Object selectOne(String sqlId,Object param){
        Connection connection = sessionFactory.getTransaction().getConnection();
        Object o=null;
        try {
            Map<String, MappingStatement> statements = sessionFactory.getMappingStatements();
            MappingStatement statement = statements.get(sqlId);
            //要封装的全限定类名
            String resultType = statement.getResultType();
            String sql = statement.getSqlText().trim();
            //   select * from t_user where id = #{id}  假设只有一个占位符
            String newSql = sql.replaceAll("#\\{[0-9A-Za-z_$]*}", "?");
            PreparedStatement ps = connection.prepareStatement(newSql);
            //因为假设只有一个占位符,所以直接给占位符赋值就行了
            ps.setString(1,param.toString());
            ResultSet rs = ps.executeQuery();
            Class<?> aClass = Class.forName(resultType);
            o = aClass.newInstance();
            if (rs.next()) {

                //获取列名, 转成SetXXX,就可以调用 SetXXX() 方法
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i =1;i<=columnCount;i++){
                    String columnName = rsmd.getColumnName(i);
                    String methodName =
                            "set"+columnName.toUpperCase().charAt(0)+columnName.substring(1);
                    Method method = aClass.getDeclaredMethod(methodName, String.class);
                    method.invoke(o,rs.getString(columnName));
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return o;
    }

    public int insert(String sqlId,Object pojo){
        int count = 0;
        try {
            //获得连接对象
            Connection connection = sessionFactory.getTransaction().getConnection();
            Map<String, MappingStatement> statements = sessionFactory.getMappingStatements();
            //框架中的原始sql语句，带#{}占位符
            MappingStatement statement = statements.get(sqlId);
            String sql = statement.getSqlText();
            //将占位符换成?号
            String newSql = sql.replaceAll("#\\{[0-9A-Za-z_$]*}", "?");
            PreparedStatement ps = connection.prepareStatement(newSql);
            //给?号占位符传值,
            //难度是你不知道?号有多少个
            //不知道该把pojo对象的哪些属性赋值给哪一个?号
            int fromIndex = 0;
            //问号的下标
            int index = 1;
            while (true){
                int jingIndex = sql.indexOf('#',fromIndex);
                if (jingIndex<0){
                    break;
                }
                int youkuohao = sql.indexOf('}',fromIndex);
                //截取字符串 左右右不包
                //举例 #{id},则 propertName=id
                String propertName = sql.substring(jingIndex + 2, youkuohao).trim();
                fromIndex = youkuohao+1;
                //给第index个问号传值
                //比如#{id} #{name} #{age}
                //则将id getId 在通过反射调用
                String methodName = "get"+propertName.toUpperCase().charAt(0)+propertName.substring(1);
                Method method =  pojo.getClass().getDeclaredMethod(methodName);
                String value = (String)method.invoke(pojo);
                ps.setString(index,value);
                index++;
            }


            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return count;
    }


    public static void main(String[] args) {
        String sql = "insert into t_user values(#{id},#{ name},#{age})";
        int fromIndex = 0;
        //问号的下标
        int index = 1;
        while (true){
            int jingIndex = sql.indexOf('#',fromIndex);
            if (jingIndex<0){
                break;
            }
            int youkuohao = sql.indexOf('}',fromIndex);
            //截取字符串 左右右不包
            String propertName = sql.substring(jingIndex + 2, youkuohao).trim();
            fromIndex = youkuohao+1;
            System.out.println();
            //问号的下标
            index++;
        }
    }



    public void commit(){
        sessionFactory.getTransaction().commit();
    }
    public void rollback(){
        sessionFactory.getTransaction().rollback();
    }
    public void close(){
        sessionFactory.getTransaction().close();
    }

}
