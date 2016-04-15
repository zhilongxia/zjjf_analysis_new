package com.zjjf.analysis.services.authority;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjjf.analysis.beans.analysis.authority.BaseRole;
import com.zjjf.analysis.mapper.analysis.BaseRoleMapper;

@Service
public class BaseRoleServcie{

	@Autowired
	private BaseRoleMapper baseRoleMapper;
	

	public void add_base_role(String roleNo, String roleName, String roleRemark, String userId, Integer ordId){
		
		BaseRole baseRole = new BaseRole();
		baseRole.setRoleNo(roleNo);
		baseRole.setRoleName(roleName);
		baseRole.setRoleRemark(roleRemark);
		baseRole.setCreateTime(new Date());
		baseRole.setCreateUser(userId);
		baseRole.setStatus(1);
		baseRole.setOrdId(ordId);
		baseRole.setIsDelete("1");
		baseRoleMapper.insert(baseRole);
	}
}
