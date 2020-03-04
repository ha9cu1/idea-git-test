package com.lagou.pojo;

public class MappedStatement {
    private String id;
    private String resultType;
    private String parameterType;
    private String sqlstr;
    private String comdType;

    public String getComdType() {
        return comdType;
    }

    public void setComdType(String comdType) {
        this.comdType = comdType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSqlstr() {
        return sqlstr;
    }

    public void setSqlstr(String sqlstr) {
        this.sqlstr = sqlstr;
    }
}
