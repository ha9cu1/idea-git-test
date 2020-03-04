package com.lagou.sqlSession;

import com.lagou.config.XMLConfigBuilder;
import com.lagou.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory builder(InputStream inputStream) throws PropertyVetoException, DocumentException {
        //1.dom4j---->Configuration
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);

        //2.sqlSessionFactory
        DefaultSessionFactory defaultSessionFactory = new DefaultSessionFactory(configuration);

        return defaultSessionFactory;
    }
}
