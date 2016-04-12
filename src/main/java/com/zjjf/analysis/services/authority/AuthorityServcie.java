package com.zjjf.analysis.services.authority;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.zjjf.analysis.services.AbstractBaseServcie;

import net.sf.json.JSONObject;

@Service
public class AuthorityServcie extends AbstractBaseServcie {

	@Override
	public Object[] sort_by_viewTitle(HashMap<String, Object> t, String[] viewTitle) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public HashMap<String, Object> addRoles(JSONObject jsonObj) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
}
