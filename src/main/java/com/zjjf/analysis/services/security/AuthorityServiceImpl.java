package com.zjjf.analysis.services.security;

import java.util.Date;
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
import com.zjjf.analysis.beans.analysis.users.UserInfos;
import com.zjjf.analysis.beans.login.ScManager;
import com.zjjf.analysis.beans.vo.LoginVo;
import com.zjjf.analysis.beans.vo.ModelMsg;
import com.zjjf.analysis.common.constants.SessionConfig;
import com.zjjf.analysis.mapper.analysis.BaseAuthorityMapper;
import com.zjjf.analysis.mapper.analysis.BaseMenuMapper;
import com.zjjf.analysis.mapper.analysis.BaseRoleMapper;

@Service
public class AuthorityServiceImpl {

	@Autowired
	private BaseMenuMapper baseMenuMapper;

	@Autowired
	private BaseRoleMapper baseRoleMapper;

	@Autowired
	private BaseAuthorityMapper baseAuthorityMapper;

	public ModelMsg dealScMgSuccessLogin(LoginVo loginRo, HttpServletRequest request, Model model) {

		UserInfos userInfo = new UserInfos();
		userInfo.setId("1");
		SecurityUtils.getSubject().getSession().setAttribute(SessionConfig.user_session_key, userInfo);
		SecurityUtils.getSubject().getSession().setAttribute(SessionConfig.user_type_key, SessionConfig.user_scmg);
		ScManager scManager = new ScManager();
		scManager.setId(userInfo.getId());
		scManager.setLoginIP(request.getRemoteAddr());
		scManager.setLastTime(new Date());
		String userId = "test";
		SecurityUtils.getSubject().getSession().setAttribute("menuTree", getMenuTree(userId));
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

	public List<HashMap<String, Object>> getMenuTree(String userId) {

		List<HashMap<String, Object>> menuTree = baseMenuMapper.getMenuTreeByUserId(userId);
		return menuTree;
	}
}
