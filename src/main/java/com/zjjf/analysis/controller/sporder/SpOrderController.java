package com.zjjf.analysis.controller.sporder;

import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjjf.analysis.common.constants.SessionConfig;
import com.zjjf.analysis.controller.BaseController;
import com.zjjf.analysis.services.orders.SupportOrdersServcie;

@Controller
@RequestMapping(value = "/api/sp_order")
public class SpOrderController extends BaseController {

	private final Integer limit = 5;

	private final Integer offset = 5;

	@Autowired
	private SupportOrdersServcie supportOrdersServcie;

	private static Logger logger = LoggerFactory.getLogger(SpOrderController.class);

	@RequestMapping(value = "/loadPage.do")
	public String orderLoaded(HttpServletRequest request) {

		return "analysis/sporder/sp_order";
	}

	/**
	 * 获取交易订单数据
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/spOrderList.do")
	@ResponseBody
	public HashMap<String, Object> querySpOrders(HttpServletRequest request) {

		HashMap<String, Object> anthorityData = (HashMap<String, Object>) SecurityUtils.getSubject().getSession().getAttribute(SessionConfig.authorityDataKey);
		String filterKeys = anthorityData.get(SessionConfig.filterKeys) + "";
		
		String currentPage = request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage");
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		HashMap<String, Object> paramMap = getQueryMap(request, Arrays.asList("cityId", "areaId", "spGroupId", "supportmetho", "supportStatus", "status", "orderNos",
				"storeName", "supplierName", "addTimeBegin", "addTimeEnd"));

		paramMap.put("pageNo", Integer.valueOf(currentPage) * limit);
		paramMap.put("offset", offset);
		logger.info("交易订单传入参数 paramMap:" + paramMap);
	
		resultMap.put("optionList", supportOrdersServcie.getOptionList());
		resultMap.put("key_cn", supportOrdersServcie.getOrderTitle());//1为id， 0为key
		resultMap.put("dataList", supportOrdersServcie.getOrderData(paramMap));
		return resultMap;
	}
}
