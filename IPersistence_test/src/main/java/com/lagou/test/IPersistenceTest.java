package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().builder(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> userlist = sqlSession.selectList("com.lagou.dao.IUserDao.findAll");
        System.out.println(userlist);

//        User user1 = new User();
//        user1.setId(8);
//        user1.setUsername("lalala");
//        sqlSession.insert("com.lagou.dao.IUserDao.insertUser",user1);





        //获取 IUserDao 的代理对象, getMapper返回一个proxy-代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        //代理对象调用接口的任意方法, 都会执行invoke方法[Proxy.newProxyInstance时的invoke方法]
        List<User> all = userDao.findAll();
        System.out.println("all----"+all);
//
//        User user = new User();
//        user.setId(1);
//        user.setUsername("cui");
//        User user2 = userDao.findByCondition(user);
//        System.out.println(user2);

        //新增
        User user = new User();
        user.setId(11);
        user.setUsername("insertUserDao");
        userDao.insertUser(user);

        //删除
        user.setId(7);
        userDao.deleteUser(user);

    }
}
