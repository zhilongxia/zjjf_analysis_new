package com.zjjf.analysis.services.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.zjjf.analysis.beans.analysis.authority.BaseAuthority;
import com.zjjf.analysis.beans.analysis.authority.BaseRole;
import com.zjjf.analysis.beans.analysis.authority.BaseRoleData;
import com.zjjf.analysis.beans.analysis.users.UserInfos;
import com.zjjf.analysis.beans.vo.LoginVo;
import com.zjjf.analysis.beans.vo.ModelMsg;
import com.zjjf.analysis.common.constants.SessionConfig;
import com.zjjf.analysis.mapper.analysis.BaseAuthorityMapper;
import com.zjjf.analysis.mapper.analysis.BaseMenuMapper;
import com.zjjf.analysis.mapper.analysis.BaseRoleDataMapper;
import com.zjjf.analysis.mapper.analysis.BaseRoleMapper;

@Service
public class AuthorityServiceImpl {

	@Autowired
	private BaseMenuMapper baseMenuMapper;

	@Autowired
	private BaseRoleMapper baseRoleMapper;

	@Autowired
	private BaseAuthorityMapper baseAuthorityMapper;

	@Autowired
	private BaseRoleDataMapper baseRoleDataMapper;

	public ModelMsg dealScMgSuccessLogin(LoginVo loginRo, HttpServletRequest request, Model model) {

		UserInfos userInfo = new UserInfos();
		userInfo.setId("1");
		SecurityUtils.getSubject().getSession().setAttribute(SessionConfig.userId, userInfo.getId());
		SecurityUtils.getSubject().getSession().setAttribute(SessionConfig.user_session_key, userInfo);
		SecurityUtils.getSubject().getSession().setAttribute(SessionConfig.user_type_key, SessionConfig.user_scmg);
		SecurityUtils.getSubject().getSession().setAttribute(SessionConfig.menuTree, getMenuTree("test"));
		SecurityUtils.getSubject().getSession().setAttribute(SessionConfig.authorityDataKey, getAuthorityData("test"));
		int count = 1;
		if (count == 1) {
			return new ModelMsg(true, "登陆成功");
		} else {
			return new ModelMsg(false, "数据异常");
		}
	}

	/**
	 * 获取用户角色
	 * 
	 * @param userId
	 * @return
	 */
	public Set<String> getRoleSet(String userId) {

		Set<String> roleSet = new HashSet<String>();
		List<BaseRole> roleList = baseRoleMapper.getRoleByUserId(userId);
		if (roleList.size() > 0) {
			for (BaseRole baseRole : roleList) {
				roleSet.add(baseRole.getRoleNo());
			}
		}
		return roleSet;
	}

	/**
	 * 获取用户权限
	 * 
	 * @param userId
	 * @return
	 */
	public Set<String> getPermissionsSet(String userId) {

		Set<String> roleSet = new HashSet<String>();
		List<BaseAuthority> roleList = baseAuthorityMapper.getAuthorityByUserId(userId);
		if (roleList.size() > 0) {
			for (BaseAuthority baseAuthority : roleList) {
				roleSet.add(baseAuthority.getRoleNo() + ":" + baseAuthority.getAuthString());
			}
		}
		return roleSet;
	}

	public HashMap<String, Object> getAuthorityData(String userId) {

		HashMap<String, Object> authmap = new HashMap<String, Object>();
		String dataIds = "";
		String filterKeys = "";
		List<BaseRoleData> dataList = baseRoleDataMapper.getAuthorityData(userId);
		for (BaseRoleData baseRoleData : dataList) {
			dataIds = dataIds + baseRoleData.getDataIds() + ",";
			filterKeys  = filterKeys + baseRoleData.getTableKey() + ",";
			authmap.put("level", baseRoleData.getLevel());
		}
		authmap.put(SessionConfig.filterAuthorityData, dataIds.substring(0, dataIds.length() - 1));
		authmap.put(SessionConfig.filterKeys, filterKeys.substring(0, filterKeys.length() - 1));
		return authmap;
	}

	public List<HashMap<String, Object>> getMenuTree(String userId) {

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("level", 1);
		paramMap.put("userId", userId);
		List<HashMap<String, Object>> menuTree = baseMenuMapper.getMenuLevel(paramMap);
		for (HashMap<String, Object> hashMap : menuTree) {
			paramMap.clear();
			paramMap.put("pid", hashMap.get("id"));
			paramMap.put("level", 2);
			List<HashMap<String, Object>> level2Tree = baseMenuMapper.getMenuLevel(paramMap);
			hashMap.put("level2Tree", level2Tree);
		}
		return menuTree;
	}
}
