package com.lagou.sqlSession;

import java.util.List;

public interface SqlSession {
    //查询多个
    public <E> List<E> selectList(String statementid , Object... params) throws Exception;

    //查询单个
    public <T> T selectOne(String statementid , Object... params) throws Exception;

    //新增
    public int update(String statementid , Object... params) throws Exception;

    //为Dao接口生成代理实现类
    public <T> T getMapper(Class mapperClass);
}
