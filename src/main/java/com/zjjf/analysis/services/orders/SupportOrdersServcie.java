package com.zjjf.analysis.services.orders;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjjf.analysis.mapper.analysis.SupportOrderPageMapper;
import com.zjjf.analysis.services.AbstractBaseServcie;

@Service
public class SupportOrdersServcie extends AbstractBaseServcie {

	@Autowired
	SupportOrderPageMapper supportOrderPageMapper;
	
	public List<Object[]> getOrderData(HashMap<String, Object> paramMap){
		
		List<HashMap<String, Object>> dataList = supportOrderPageMapper.getOrderData(paramMap);
		return stand_by_title(dataList, getOrderTitle());
	}
	
	public String [] getOrderTitle(){
		
		return getColumnId();
	}
	
	public String [] getOrderColumnName(){
		
		return getColumnName();
	}

	@Override
	public Object[] sort_by_viewTitle(HashMap<String, Object> t, String[] viewTitle) {
		
		Object[] row = new Object[viewTitle.length];
		for (int i = 0; i < viewTitle.length; i++) {
			if(t.containsKey(viewTitle[i])){
				row[i] = t.get(viewTitle[i]);
			}
		}
		return row;
	}
}
