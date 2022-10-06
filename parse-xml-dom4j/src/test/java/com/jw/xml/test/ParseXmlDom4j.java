package com.jw.xml.test;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;

public class ParseXmlDom4j {


    @Test
    public void testParseSqlMappingXml() throws Exception{
        //创建解析器对象
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(ClassLoader.getSystemClassLoader().getResourceAsStream("carMapping.xml"));
        String xPath = "/mapper";
        Element element = (Element) document.selectSingleNode(xPath);
        String namespace = element.attributeValue("namespace");
        List<Element> mapper = element.elements();
        mapper.forEach( node -> {
            String id = node.attributeValue("id");
            System.out.println(id);
            String resultType = node.attributeValue("resultType");
            String sql = node.getTextTrim();
            String newSql = sql.replaceAll("#\\{[0-9A-Za-z_$]*}", "?");
            System.out.println(newSql);
        });

    }

    @Test
    public void testParseXmlConfig() throws Exception {
        //创建解析器对象
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml"));
        String s = "/configuration/environments";
        Element e = (Element) document.selectSingleNode(s);
        String aDefault = e.attributeValue("default");
        System.out.println("默认环境是="+aDefault);
        String xPath = "/configuration/environments/environment[@id='"+aDefault+"']";
        Element node = (Element) document.selectSingleNode(xPath);
        Element transactionManager = node.element("transactionManager");
        String transationType = transactionManager.attributeValue("type");
        System.out.println("事务管理器的类型为="+transationType);
        Element dataSource = node.element("dataSource");
        System.out.println("数据源类型为="+ dataSource.attributeValue("type"));
        List<Element> property = dataSource.elements();
        property.forEach(element -> {
            System.out.println(element.attributeValue("name")+"  = "+element.attributeValue("value"));
        });



        //获取所有的Mapping标签
        xPath = "//mapper";
        List<Node> nodes = document.selectNodes(xPath);
        nodes.forEach(ele -> {
            Element ele1 = (Element) ele;
            String resource = ele1.attributeValue("resource");
            System.out.println(resource);
        });
    }
}
