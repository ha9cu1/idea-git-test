package com.lagou.sqlSession;

import com.lagou.config.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor{
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        //获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //获取sql
        String sqlstr = mappedStatement.getSqlstr();

        //转换sql
        BoundSql boundSql = getBoundSql(sqlstr);
        //获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlstr());

        //设置参数
        Class<?> parameterClazz = getClassWithType(mappedStatement.getParameterType());
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        int index=1;
        for (ParameterMapping parameterMapping : parameterMappingList) {
            Field declaredField = parameterClazz.getDeclaredField(parameterMapping.getContent());
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(index++,o);
        }
        //执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        //封装返回结果
        Class<?> resultClazz = getClassWithType(mappedStatement.getResultType());
        List<Object> resultList = new ArrayList<Object>();
        while (resultSet.next()){
            Object resultObj = resultClazz.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(columnName);

                //内省
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultClazz);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(resultObj,value);
            }
            resultList.add(resultObj);
        }
        return (List<E>) resultList;
    }

    public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        //获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //获取sql
        String sqlstr = mappedStatement.getSqlstr();
        System.out.println(sqlstr);

        //转换sql
        BoundSql boundSql = getBoundSql(sqlstr);
        System.out.println(boundSql.getSqlstr());
        //获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlstr());

        //设置参数
        Class<?> parameterClazz = getClassWithType(mappedStatement.getParameterType());
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        int index=1;
        for (ParameterMapping parameterMapping : parameterMappingList) {
            Field declaredField = parameterClazz.getDeclaredField(parameterMapping.getContent());
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(index++,o);
        }
        //执行sql
        return preparedStatement.executeUpdate();
    }

    private Class<?> getClassWithType(String parameterType) throws ClassNotFoundException {
        if(parameterType != null){
            return Class.forName(parameterType);
        }
        return null;
    }

    /**
     * 解析 sqlstr , 将 #{}用?占位 , 并存储变量?值
     * @param sqlstr
     * @return
     */
    private BoundSql getBoundSql(String sqlstr) {
        //ParameterMappingTokenHandler 解析处理类:配置标志解析器对占位符进行解析处理
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{","}",parameterMappingTokenHandler);

        //解析后的sql ？占位
        String parseSql = genericTokenParser.parse(sqlstr);

        //解析后的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();


        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);


        return boundSql;
    }
}
