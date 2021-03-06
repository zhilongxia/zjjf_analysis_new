package com.zjjf.analysis.services.authority;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class AnaUserInfoRealm extends AuthorizingRealm {

	@Autowired
	private AuthorityServiceImpl authorityService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

		if (authcToken == null) {
			return null;
		} else {
			UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
			String credential = token.getUsername();
			if (StringUtils.isEmpty(credential)) {
				return null;
			}
			SimpleAuthenticationInfo sauth = new SimpleAuthenticationInfo("test", "111111", "测试账号");
			return sauth;
		}
	}

	// 获取授权信息
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		String currentUsername = (String) super.getAvailablePrincipal(principals);
		if (!StringUtils.isEmpty(currentUsername)) {
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			if (null != currentUsername) {
				authorizationInfo.setRoles(authorityService.getRoleSet(currentUsername));
				authorizationInfo.addStringPermissions(authorityService.getPermissionsSet(currentUsername));
				return authorizationInfo;
			}
		}
		return null;
	}

}
