package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;

public class DefaultSessionFactory implements SqlSessionFactory{
    private Configuration configuration;
    public DefaultSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
