import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MybatisTest {

    public static void main(String[] args) throws Exception {
        //获取SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        //获取SqlSessionFactory对象
        //Resources默认从类的根路径下寻找资源
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        //一个数据库对应一个SqlSessionFactory对象
        SqlSessionFactory sessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);

        //获取SqlSession对象
        SqlSession sqlSession = sessionFactory.openSession();

        //执行SQL语句
        int i = sqlSession.insert("insertCar");
        System.out.println("插入了几条记录="+i);

        sqlSession.commit();
    }
}
