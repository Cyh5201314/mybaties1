package com.jw;

import com.pojo.Car;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1 {


    @Test
    public void testNamespace(){
        //当两个的Mapping文件中，id相同，会报错。
        //这时，可以用Namespace指定命名空间，防止Id重复
        // namespace.id   比如 car2.selectAll
        SqlSession sqlSession = SessionUtil.openSession();
        List<Car> cars = sqlSession.selectList("car1.selectAll");
        cars.forEach(car -> System.out.println(car));
    }


    @Test
    public void selectAll(){
        //查询多条，返回List，但还是要指定 resultType 集合中的泛型
        SqlSession sqlSession = SessionUtil.openSession();
        SqlSession sqlSession2 = SessionUtil.openSession();
        List<Car> cars = sqlSession.selectList("car2.selectAll");
        cars.forEach(car -> System.out.println(car));
    }

    @Test
    public void queryId(){
        //根据Id查询一个
        SqlSession sqlSession = SessionUtil.openSession();
        /**   必须要指定resultType,指定全限定类名
          <select id="queryId" resultType="com.pojo.Car">
                  select *
                  from t_car where id = #{id};
              </select>
         * **/
        Car car = (Car)sqlSession.selectOne("queryId", 2);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void deleteById(){
        SqlSession sqlSession = SessionUtil.openSession();
        // delete from t_car where id = #{sss}   当只传一个值的时候,#{}里可以随便写,但是不能不写
        int i = sqlSession.delete("deleteById", 1);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testMap(){
        SqlSession sqlSession = SessionUtil.openSession();

        //前端传值,我们先用Map传参
       /** Map map = new HashMap();
        map.put("k1","111");
        map.put("k2","比迪亚");
        map.put("k3",10.0);
        map.put("k4","2020-1-10");
        map.put("k5","新能源"); **/
        //一般要见名知意 底层会自动调用Map.get(Ket),PreparedStatement.setXX()填充占位符
        Map map2 = new HashMap();
        map2.put("car_num","111");
        map2.put("brand","比迪亚");
        map2.put("guide_price",10.0);
        map2.put("produce_time","2020-1-10");
        map2.put("car_type","新能源");


        //执行sql语句
        //insert方法的参数： sqlId，从CarMapping.xml复制
        //第二个参数：封装数据的对象
        // int insertCar = sqlSession.insert("insertCar");
        int insertCar = sqlSession.insert("insertCar",map2);

        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void testPojo(){
        SqlSession sqlSession = SessionUtil.openSession();

        //如果用Pojo传参
        Car car = new Car();
        car.setCarNum("111");
        car.setBrand("比迪亚");
        car.setGuidePrice(10.0);
        car.setProduceTime("2020-1-10");
        car.setCarType("新能源");
        //执行sql语句
        //pojo传参  #{carNum} 会自动调用car.getCarNum()方法
        //   #{xyz} car.getXyz(),找不到就会报错,类中没有getXyz()方法
        int insertCar = sqlSession.insert("insertCar",car);

        sqlSession.commit();
        sqlSession.close();

    }
}
