package com.zjjf.analysis.mapper.analysis;

import java.util.HashMap;

import com.zjjf.analysis.beans.analysis.base.BaseRoleData;

public interface BaseRoleDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BaseRoleData record);

    int insertSelective(BaseRoleData record);

    BaseRoleData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseRoleData record);

    int updateByPrimaryKey(BaseRoleData record);
    
    BaseRoleData getAuthorityData(HashMap<String, Object> dataParam);
}