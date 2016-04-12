package com.zjjf.analysis.mapper.analysis;

import java.util.List;

import com.zjjf.analysis.beans.analysis.authority.BaseRoleData;

public interface BaseRoleDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BaseRoleData record);

    int insertSelective(BaseRoleData record);

    BaseRoleData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseRoleData record);

    int updateByPrimaryKey(BaseRoleData record);
    
    List<BaseRoleData> getAuthorityData(String userId);
}