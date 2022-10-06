package com.jw;

//接口
public interface SellTickets {
    void sell();
}

//目标对象
class TrainStation implements SellTickets{

    @Override
    public void sell() {
        System.out.println("火车站卖票");
    }
}

//代售点
 class ProxyPoint implements SellTickets {

    private SellTickets target;

    public ProxyPoint(SellTickets target) {
        this.target = target;
    }

    //既然你都是直接调用TrainStation对象的方法进行卖票，是否可以不实现SellTickets
    public void sell() {
        System.out.println("proxy Before");
        target.sell();
        System.out.println("proxy after ");
    }

    public static void main(String[] args) {
        TrainStation trainStation = new TrainStation();
        ProxyPoint proxyPoint = new ProxyPoint(trainStation);
        proxyPoint.sell();
    }

}
