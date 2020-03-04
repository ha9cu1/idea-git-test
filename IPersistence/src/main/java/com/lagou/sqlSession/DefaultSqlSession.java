package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public <E> List<E> selectList(String statementid, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> query = simpleExecutor.query(configuration,configuration.getMappedStatementMap().get(statementid),params);
        return (List<E>) query;
    }

    public <T> T selectOne(String statementid, Object... params) throws Exception {
        List<Object> objects = selectList(statementid, params);
        if(objects.size() != 1){
            throw new RuntimeException("查询结果空或者不唯一");
        }
        return (T) objects.get(0);
    }



    @Override
    public <T> T getMapper(Class mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //mehtod为执行 invoke 时的方法名，即代理对象调用接口的方法
                //args 为该方法的参数

                //开始执行jdbc操作
                String methodName = method.getName();//method方法名
                String daoClassName = method.getDeclaringClass().getName();//method所在接口类
                String statementid = daoClassName + "." + methodName; //在 mapper.xml 中约定

                Type genericReturnType = method.getGenericReturnType();//method返回类型
                //得到方法类型
                String comdType = configuration.getMappedStatementMap().get(statementid).getComdType();
                System.out.println("方法类型：" + comdType);
                switch (comdType) {
                    case "select":
                        //如果返回值类型中有泛型 (List<User>)则执行 selectList
                        if (genericReturnType instanceof ParameterizedType)
                            return selectList(statementid, args);
                        return selectOne(statementid, args);
                    case "insert":
                        return update(statementid, args);
                    case "update":
                        return update(statementid, args);
                    case "delete":
                        return update(statementid, args);
                    default:
                        return null;
                }
            }
        });
        return (T) proxyInstance;
    }

    public int update(String statementid, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        return  simpleExecutor.update(configuration, configuration.getMappedStatementMap().get(statementid), params);
    }
    public int insert(String statementid, Object... params) throws Exception {
        return update(statementid,params);
    }
    public int delete(String statementid, Object... params) throws Exception {
        return update(statementid,params);
    }

}
