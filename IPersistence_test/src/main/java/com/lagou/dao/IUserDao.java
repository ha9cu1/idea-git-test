package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

//使用代理模式生成Dao层的代理实现类
public interface IUserDao {
    public List<User> findAll();
    public User findByCondition(User user);
    public void insertUser(User user);
    public void deleteUser(User user);

}
