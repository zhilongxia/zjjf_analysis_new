package com.zjjf.analysis.beans.analysis.authority;

public class BaseRoleData {
    private Integer id;

    private Integer level;

    private String userId;

    private String dataIds;

    private String tableKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getDataIds() {
        return dataIds;
    }

    public void setDataIds(String dataIds) {
        this.dataIds = dataIds == null ? null : dataIds.trim();
    }

    public String getTableKey() {
        return tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey == null ? null : tableKey.trim();
    }
}