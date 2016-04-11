package com.zjjf.analysis.controller.sporder;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjjf.analysis.controller.BaseController;
import com.zjjf.analysis.services.orders.SupportOrdersServcie;

@Controller
@RequestMapping(value = "/api/sp_order")
public class SpOrderController extends BaseController {

	private final Integer limit = 5;
	
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
	@RequestMapping(value = "/spOrderList.do")
	@ResponseBody
	public HashMap<String, Object> querySpOrders(HttpServletRequest request) {

		String nextPage = request.getParameter("nextPage") == null? "1" : request.getParameter("nextPage");
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		logger.info("交易订单传入参数 paramMap:" + paramMap);
		paramMap.put("limit", Integer.valueOf(nextPage) * limit);
		resultMap.put("key_cn", supportOrdersServcie.getOrderColumnName());
		resultMap.put("dataList", supportOrdersServcie.getOrderData(paramMap));
		return resultMap;
	}
}
