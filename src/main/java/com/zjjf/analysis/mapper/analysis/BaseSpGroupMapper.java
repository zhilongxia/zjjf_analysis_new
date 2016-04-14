package com.zjjf.analysis.mapper.analysis;

import java.util.HashMap;
import java.util.List;

import com.zjjf.analysis.beans.analysis.base.BaseSpGroup;
import com.zjjf.analysis.beans.vo.DictionaryVo;

public interface BaseSpGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BaseSpGroup record);

    int insertSelective(BaseSpGroup record);

    BaseSpGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseSpGroup record);

    int updateByPrimaryKey(BaseSpGroup record);
    
    List<DictionaryVo> getSpGroupCodeList(HashMap<String, Object> cityMap);
}