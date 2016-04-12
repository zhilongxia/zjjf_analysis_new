package com.zjjf.analysis.controller.login;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjjf.analysis.beans.analysis.users.UserInfos;
import com.zjjf.analysis.beans.vo.LoginVo;
import com.zjjf.analysis.beans.vo.ModelMsg;
import com.zjjf.analysis.common.constants.LoginConstant;
import com.zjjf.analysis.common.constants.SessionConfig;
import com.zjjf.analysis.controller.BaseController;
import com.zjjf.analysis.services.security.AuthorityServiceImpl;
import com.zjjf.analysis.utils.ResponseUtils;

@Controller
@RequestMapping(value = "/analysis/authority")
public class LoginController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	AuthorityServiceImpl authorityService;

	@RequestMapping(value = "/scmsLoginPage.do")
	public String gotoLoginPage(HttpServletRequest request) throws IOException {

		return LoginConstant.login_url;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/scmsMainPage.do")
	public String index(HttpServletRequest request, Model model) {

		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			HashMap<String, Object> loginMap = new HashMap<String, Object>();

			UserInfos user = (UserInfos) subject.getSession().getAttribute(SessionConfig.user_session_key);
			List<HashMap<String, Object>> menuTree =  (List<HashMap<String, Object>>) subject.getSession().getAttribute("menuTree");
			if (user == null) {
				return LoginConstant.login_url;
			} else {
				loginMap.put("userId", user.getId());
				loginMap.put("userName", user.getUserName());
				model.addAttribute("logInVo", loginMap);
				model.addAttribute("menuTree", menuTree);
				return LoginConstant.main_url;
			}
		} else {
			return LoginConstant.login_url;
		}
	}

	@RequestMapping(value = "/userLoginIn.do")
	@ResponseBody
	public Object userLoginIn(LoginVo loginRo, HttpSession session, HttpServletRequest request, Model model) {

		// 参数校验
		if (loginRo == null || StringUtils.isEmpty(loginRo.getUserName()) || StringUtils.isEmpty(loginRo.getPassword())) {
			return ResponseUtils.sendMsg(false, "请输入用户名密码后登陆！");
		}
		// 防止重复登陆
		UserInfos sp = getCurrentUser(UserInfos.class, request);
		if (sp != null) {
			return ResponseUtils.sendMsg(true, "用户已登陆", LoginConstant.already_login_url);
		}
		// 校验验证码
		if (StringUtils.isEmpty(loginRo.getCheckCode())) {
			session.removeAttribute(SessionConfig.user_session_code);
			return ResponseUtils.sendMsg(false, "验证码不能为空");
		}
		String serverCode = (String) session.getAttribute(SessionConfig.user_session_code);
		if (serverCode == null || !serverCode.toLowerCase().equals(loginRo.getCheckCode().trim().toLowerCase())) {
			session.removeAttribute(SessionConfig.user_session_code);
			return ResponseUtils.sendMsg(false, "验证码错误");
		}
		// 处理登陆
		try {
			UsernamePasswordToken logintoken = new UsernamePasswordToken(loginRo.getUserName(), loginRo.getPassword(), true);
			try {
				SecurityUtils.getSubject().login(logintoken);
			} catch (UnknownAccountException une) {
				return ResponseUtils.sendMsg(false, "对不起您还没有注册");
			} catch (Exception e) {
				logger.error("用户登陆异常：{}", loginRo.getUserName(), e);
				return ResponseUtils.sendMsg(false, "用户名或密码不正确");
			}
			// ModelMsg msg =
			// authorityService.dealSupplierSuccessLogin(loginRo,request,model);
			ModelMsg msg = authorityService.dealScMgSuccessLogin(loginRo, request, model);
			if (msg != null && msg.isSuccess()) {
				return ResponseUtils.sendMsg(true, "登陆成功", LoginConstant.already_login_url);
			} else {
				return ResponseUtils.sendMsg(false, msg.getMessage());
			}
		} catch (Exception e) {
			logger.error("用户登陆后处理异常：", e);
			return ResponseUtils.sendMsg(false, "登陆异常");
		}
	}

}
