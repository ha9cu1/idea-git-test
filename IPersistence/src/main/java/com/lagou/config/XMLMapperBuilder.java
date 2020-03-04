package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {
    private Configuration configuration;
    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        // 解析 <select /> <insert /> <update /> <delete /> 节点们
        f(rootElement,"select");
        f(rootElement,"insert");
        f(rootElement,"update");
        f(rootElement,"delete");
    }
    public void f(Element element,String path){
        List<Element> nodes = element.selectNodes("//"+path);
        for (Element node : nodes) {
            String id = node.attributeValue("id");
            String resultType = node.attributeValue("resultType");
            String parameterType = node.attributeValue("parameterType");
            String sqltext = node.getTextTrim();//得到sql语句
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSqlstr(sqltext);
            mappedStatement.setComdType(path);
            String namespace = element.attributeValue("namespace");
            configuration.getMappedStatementMap().put(namespace+"."+id,mappedStatement);
        }


    }


}