package com.zjjf.analysis.services.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

public class AnaUserInfoRealm extends AuthorizingRealm {

	// @Autowired
	// private AuthorityServiceImpl authorityService;

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
			// CustomerService user = null;
			// try {
			// user =
			// authorityService.getUserByCustomerServiceCredential(credential);
			// logger.info("根据手机号获取当前用户：id：{},密码：{}，手机号：{}，用户名：{}",
			// user.getId(), user.getPassword());
			// } catch (Exception e) {
			// return null;
			// }finally{
			// if(user == null) throw new UnknownAccountException();
			// }
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
			// Set<String> rolesSet = authorityService.getRolesByUserId(userId);
			Set<String> rolesSet = new HashSet<String>();
			rolesSet.add("a");
			authorizationInfo.setRoles(rolesSet);
			// Set<String> auths = authorityService.getAuthsByUserId(userId);
			if (null != currentUsername && "test".equals(currentUsername)) {
				// 添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
				authorizationInfo.addRole("admin");
				// 添加权限
				authorizationInfo.addStringPermission("admin:manage");
				System.out.println("已为用户[test]赋予了[admin]角色和[admin:manage]权限");
				return authorizationInfo;
			}
			// Set<String> permissionsSet = new HashSet<String>();
			// permissionsSet.add("B");
			// authorizationInfo.setStringPermissions(permissionsSet);
			// return authorizationInfo;
		}
		return null;
	}

}
