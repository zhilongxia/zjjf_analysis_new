package com.zjjf.analysis.controller.authority;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjjf.analysis.controller.BaseController;
import com.zjjf.analysis.services.authority.AuthorityServcie;

import net.sf.json.JSONObject;

@Controller
@RequestMapping({ "/api/authority" })
public class AuthorityController extends BaseController {
	
	@Autowired
	private AuthorityServcie authorityServcie;

	@RequestMapping(value = "/loadPage.do")
	public String loaded(HttpServletRequest request) {

		return "analysis/authority/roles";
	} 
	

	/**
	 * 获取交易订单数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addRoles.do")
	@ResponseBody
	public HashMap<String, Object> addRoles(HttpServletRequest request, @RequestBody JSONObject jsonObj) {


		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		authorityServcie.addRoles(jsonObj);
		return resultMap;
	}
	
}
