package com.zjjf.analysis.services.authority;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjjf.analysis.beans.analysis.authority.BaseRole;
import com.zjjf.analysis.beans.analysis.authority.BaseRoleUser;
import com.zjjf.analysis.beans.analysis.base.BaseRoleData;
import com.zjjf.analysis.mapper.analysis.BaseMenuMapper;
import com.zjjf.analysis.mapper.analysis.BaseRoleDataMapper;
import com.zjjf.analysis.mapper.analysis.BaseRoleMapper;
import com.zjjf.analysis.mapper.analysis.BaseRoleUserMapper;

import net.sf.json.JSONObject;

@Service
public class AuthorityModelServcie {

	@Autowired
	private BaseRoleMapper baseRoleMapper;

	@Autowired
	private BaseRoleUserMapper baseRoleUserMapper;
	
	@Autowired
	private BaseMenuMapper baseMenuMapper;
	
	@Autowired
	private BaseRoleDataMapper baseRoleDataMapper;

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

	private void add_base_role(JSONObject jsonObj, String userId) {

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

	private void add_base_role_user(Integer roleId, String userId) {

		BaseRoleUser baseRoleUser = new BaseRoleUser();
		baseRoleUser.setRoleId(roleId);
		baseRoleUser.setUserId(userId);
		baseRoleUserMapper.insert(baseRoleUser);
	}

	private void add_base_authority_data(String dataIds) {

	}

	
	public HashMap<String, Object> overrideRole(Integer roleId){
		
		HashMap<String, Object> overrideMap = new HashMap<String, Object>();
		
		BaseRole baseRole = baseRoleMapper.getRoleByRoleId(roleId);
		overrideMap.put("roleName", baseRole.getRoleName());
		return overrideMap;
	}
	
	public List<BaseRole> getRolesOption() {
		return baseRoleMapper.getAllRole();
	}
	
	public List<HashMap<String, Object>> getAllMenuTree(){
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("level", 1);
		List<HashMap<String, Object>> menuTree = baseMenuMapper.getMenuLevel(paramMap);
		for (HashMap<String, Object> hashMap : menuTree) {
			paramMap.clear();
			paramMap.put("pid", hashMap.get("id"));
			paramMap.put("level", 2);
			List<HashMap<String, Object>> level2Tree = baseMenuMapper.getMenuLevel(paramMap);
			hashMap.put("level2Tree", level2Tree);
			hashMap.put("authorityKeys", getAuthorityKeysByMenuId(Integer.valueOf("" + hashMap.get("id"))));
		}
		return menuTree;
	}
	
	private List<HashMap<String, Object>> getAuthorityKeysByMenuId(Integer menuId) {
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> dataParam = new HashMap<String, Object>();
		dataParam.put("menuId", menuId);
		List<BaseRoleData> dataList = baseRoleDataMapper.getAuthorityData(dataParam);
		for (BaseRoleData baseRoleData : dataList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(baseRoleData.getTableKey().trim(), baseRoleData.getIsChecked());
			resultList.add(map);
		}
		return resultList;
	}
}
