package com.jw;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory implements InvocationHandler  {

    public ProxyFactory(SellTickets target) {
        this.target = target;
    }

    private SellTickets target;

    public SellTickets getPropxyObject(){
        //这里就是返回代理类
        return (SellTickets)Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{SellTickets.class},
//                new InvocationHandler() {
//                    private String className= "匿名内部类而已";
//                    @Override
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        System.out.println("代理前");
//                        Object invoke = method.invoke(target, args);
//                        System.out.println("代理后");
//                        return invoke;
//                    }
//                }
                this
        );

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("代理前");
            Object invoke = method.invoke(proxy, args);
            System.out.println("代理后");
            return null;
    }
}
class A{
    public static void main(String[] args) {
        SellTickets trainStation = new TrainStation();
        ProxyFactory proxyFactory = new ProxyFactory(trainStation);
        SellTickets  propxyObject = proxyFactory.getPropxyObject();
        //代理类中的sell方法
        propxyObject.sell();
        System.out.println("----------");

    }

}