package com.jw.ibatis.utils;

import java.io.InputStream;

/**
 * 工具类 构造方法私有化
 */
public class Resource {
    private Resource(){
    }

    /**
     * 读取配置文件 返回一个InputStream 对象
     * @param resource
     * @return
     */
    public static InputStream getResourceAsStream(String resource){
        return ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
    }
}
