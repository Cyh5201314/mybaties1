package com.jw.ibatis.core;



public class MappingStatement {
    private String sqlText;
    private String resultType;

    public MappingStatement(String sqlText, String resultType) {
        this.sqlText = sqlText;
        this.resultType = resultType;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
