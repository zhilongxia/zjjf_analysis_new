package com.zjjf.analysis.mapper.analysis;

import com.zjjf.analysis.beans.analysis.authority.BaseRoleAuthorityKey;

public interface BaseRoleAuthorityMapper {
    int deleteByPrimaryKey(BaseRoleAuthorityKey key);

    int insert(BaseRoleAuthorityKey record);

    int insertSelective(BaseRoleAuthorityKey record);
}