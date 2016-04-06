package com.zjjf.analysis.services.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.zjjf.analysis.beans.analysis.users.UserInfos;
import com.zjjf.analysis.beans.login.ScManager;
import com.zjjf.analysis.beans.vo.LoginVo;
import com.zjjf.analysis.beans.vo.ModelMsg;
import com.zjjf.analysis.common.constants.SessionConfig;

@Service
public class AuthorityServiceImpl {

	public ModelMsg dealScMgSuccessLogin(LoginVo loginRo, HttpServletRequest request, Model model) {

		UserInfos userInfo = new UserInfos();
		userInfo.setId("1");
		SecurityUtils.getSubject().getSession().setAttribute(SessionConfig.user_session_key, userInfo);
		SecurityUtils.getSubject().getSession().setAttribute(SessionConfig.user_type_key, SessionConfig.user_scmg);
		ScManager scManager = new ScManager();
		scManager.setId(userInfo.getId());
		scManager.setLoginIP(request.getRemoteAddr());
		scManager.setLastTime(new Date());
		int count = 1;
		if (count == 1) {
			return new ModelMsg(true, "登陆成功");
		} else {
			return new ModelMsg(false, "数据异常");
		}
	}
}
