package com.jw.ibatis.core;

import com.jw.ibatis.utils.Resource;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlSessionFactoryBuilder {


    /**
     * 解析xml文件  创建SqlSessionFactory对象
     * @param in
     * @return
     */
    public SqlSessionFactory builder(InputStream in){
        Transaction transaction = null;
        Map<String,MappingStatement> map = null;
        SqlSessionFactory sqlSessionFactory = null;

        try {
            //解析配置文件
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(in);
            String xPath = "/configuration/environments";
            //解析默认环境
            Element environments = (Element)document.selectSingleNode(xPath);
            String edefault = environments.attributeValue("default");
            xPath = "/configuration/environments/environment[@id='"+edefault+"']";
            Element environment = (Element) document.selectSingleNode(xPath);
            //获取所有mapping标签的路径
            //获取所有的Mapping标签
            List<String> list = new ArrayList();
            xPath = "//mapper";
            List<Node> nodes = document.selectNodes(xPath);
            nodes.forEach(ele -> {
                Element ele1 = (Element) ele;
                String resource = ele1.attributeValue("resource");
                list.add(resource);
            });
            //事务管理标签
            Element transactionManagerEle = environment.element("transactionManager");
            //数据源标签
            Element dataSourceEle = environment.element("dataSource");
            DataSource dataSource = getDataSource(dataSourceEle);
            transaction = getTransaction(transactionManagerEle,dataSource);
            //创建一个MappingStatement
            map = getStaments(list);
            sqlSessionFactory = new SqlSessionFactory(transaction,map);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        return sqlSessionFactory;
    }

    private Map<String, MappingStatement> getStaments(List<String> list) {
        Map<String, MappingStatement> map = new HashMap<>();
        list.forEach(resource->{
            try {
                SAXReader saxReader = new SAXReader();
                Document document = saxReader.read(Resource.getResourceAsStream(resource));
                String xPath="/mapper";
                Element rootElement = (Element)document.selectSingleNode(xPath);
                String namespace = rootElement.attributeValue("namespace");
                List<Element> elements = rootElement.elements();
                elements.forEach(element -> {
                    String id = element.attributeValue("id");
                    String sql = element.getTextTrim();
                    String resultType = element.attributeValue("resultType");
                    MappingStatement mapping = new MappingStatement(sql, resultType);
                    map.put(namespace+"."+id,mapping);
                });
            } catch (DocumentException e) {
                throw new RuntimeException(e);

            }
        });
        return map;
    }

    /**
     * 根据事务管理标签和数据源,创建一个事务管理器
     * @param element
     * @param dataSource
     * @return
     */
    private Transaction getTransaction(Element element, DataSource dataSource) {
        Transaction transaction = null;
        String type = element.attributeValue("type").trim().toUpperCase();
        //type 有JDBC 和 Managed两种
        if (type.equals("JDBC")){
            transaction = new JdbcTransaction(dataSource,false);
        }
        if (type.equals("Managed")){
            transaction = new ManagedTransaction();
        }
        return transaction;
    }

    /**
     * 根据数据源标签,创建一个数据源
     * @param dataSourceEle
     * @return
     */
    private DataSource getDataSource(Element dataSourceEle)  {
        DataSource dataSource = null;
        //数据源一共有三种模式 UNPOOLED POOLED JNDI
        String type = dataSourceEle.attributeValue("type");
        type = type.trim().toUpperCase();
        Map<String,String> map = new HashMap<>();
        List<Element> propertys = dataSourceEle.elements();
        propertys.forEach(element->{
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            map.put(name,value);
        });

        if (type.equals("UNPOOLED")){
            dataSource = new UnpooledDataSource(map.get("driver"),map.get("url"),map.get("username"),map.get("password"));
        }
        if (type.equals("POOLED")){
            dataSource = new UnpooledDataSource(map.get("driver"),map.get("url"),map.get("username"),map.get("password"));
        }
        if (type.equals("JNDI")){
            dataSource = new UnpooledDataSource(map.get("driver"),map.get("url"),map.get("username"),map.get("password"));
        }
        return dataSource;

    }


}
