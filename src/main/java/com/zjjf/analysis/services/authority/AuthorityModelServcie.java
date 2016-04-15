package com.zjjf.analysis.services.authority;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjjf.analysis.beans.analysis.authority.BaseRole;
import com.zjjf.analysis.beans.analysis.authority.BaseRoleUser;
import com.zjjf.analysis.mapper.analysis.BaseRoleMapper;
import com.zjjf.analysis.mapper.analysis.BaseRoleUserMapper;

import net.sf.json.JSONObject;

@Service
public class AuthorityModelServcie {

	@Autowired
	private BaseRoleMapper baseRoleMapper;
	
	@Autowired
	private BaseRoleUserMapper baseRoleUserMapper;

	
	public HashMap<String, Object> addRoles(JSONObject jsonObj, String userId) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		add_base_role(jsonObj, userId);
		return map;
	}
	
	public HashMap<String, Object> grantUser(JSONObject jsonObj, String userId) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		add_base_role_user(jsonObj.getInt("roleId"), jsonObj.getString("userId"));
		
		add_base_authority_data(jsonObj.getString("dataIds"));
		return map;
	}
	
	private void add_base_role(JSONObject jsonObj, String userId){
		
		BaseRole baseRole = new BaseRole();
		baseRole.setRoleNo(jsonObj.getString("roleNo"));
		baseRole.setRoleName(jsonObj.getString("roleName"));
		baseRole.setRoleRemark(jsonObj.getString("roleRemark"));
		baseRole.setCreateTime(new Date());
		baseRole.setCreateUser(userId);
		baseRole.setStatus(1);
		baseRole.setOrdId(jsonObj.getInt("ordId"));
		baseRole.setIsDelete("1");
		baseRoleMapper.insert(baseRole);
	}


	private void add_base_role_user(Integer roleId, String userId){
		
		BaseRoleUser baseRoleUser = new BaseRoleUser();
		baseRoleUser.setRoleId(roleId);
		baseRoleUser.setUserId(userId);
		baseRoleUserMapper.insert(baseRoleUser);
	}
	
	private void add_base_authority_data(String dataIds){
		
	}
	
	
}
