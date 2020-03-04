package com.lagou.config;

import com.lagou.utils.ParameterMapping;

import java.util.List;

public class BoundSql {
    private String sqlstr;//解析后的sql
    private List<ParameterMapping> parameterMappingList;//参数

    public BoundSql(String sqlstr, List<ParameterMapping> parameterMappingList) {
        this.sqlstr = sqlstr;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlstr() {
        return sqlstr;
    }

    public void setSqlstr(String sqlstr) {
        this.sqlstr = sqlstr;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
