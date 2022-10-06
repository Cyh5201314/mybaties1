package com;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import sun.security.mscapi.CPublicKey;

import java.util.Iterator;
import java.util.List;

public class TestB {

    @Test
    public void method1() throws Exception{
        //创建解析器对象
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.
                read(ClassLoader.getSystemClassLoader().getResourceAsStream("file/mybatis-config.xml"));
        //获取根元素
        Element root = document.getRootElement();

        List<Node> nodes = root.selectNodes("environments//property");
        Iterator<Node> iterator = nodes.iterator();
        while(iterator.hasNext()){
            Element next = (Element)iterator.next();
            System.out.println(next.getName());
        }


    }

    @Test
    public void method2() throws Exception{
        //创建解析器对象
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(ClassLoader.getSystemClassLoader().getResourceAsStream("file/mybatis-config.xml"));
        //xpath语法
        //使用绝对路径查找元素  绝对路径是从根标签开始找  以/开头
        Element node = (Element)document.selectSingleNode(
                "/configuration/environments/environment/dataSource/property");
        String name = node.attributeValue("name");
        System.out.println(name);
        //相对路径    ../是表示上一个元素 ./是表示当前元素
        Element node1 = (Element)node.selectSingleNode("../property");
        System.out.println(node1.attributeValue("name") );
    }
}
