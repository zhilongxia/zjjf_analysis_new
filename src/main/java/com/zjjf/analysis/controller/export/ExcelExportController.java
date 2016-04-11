package com.zjjf.analysis.controller.export;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zjjf.analysis.controller.BaseController;
import com.zjjf.analysis.services.orders.SupportOrdersServcie;

@Controller
@RequestMapping(value = "/report/excelExport")
public class ExcelExportController extends BaseController {

	@Autowired(required = false)
	private SupportOrdersServcie supportOrdersServcie;

	private static Logger logger = LoggerFactory.getLogger(ExcelExportController.class);

	/**
	 * 获取交易订单数据
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/portExcel.do")
	public void portExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

		OutputStream outputStream = response.getOutputStream();  
		 
		HashMap<String, Object> paramMap = getQueryMap(request, Arrays.asList("cityId", "areaId", "spGroupId", "supportmetho", "status", "orderNo",
				"chirdOrderNo", "storeName", "supplierName", "addTimeBegin", "addTimeEnd"));
		logger.info("交易订单传入参数 paramMap:" + paramMap);
		 
		String sheetName = "交易订单";
		
		paramMap.put("pageNo", 10000);
		paramMap.put("offset", 10000);
		InputStream inputStream = supportOrdersServcie.exportOrderList(request, response, sheetName, paramMap);
		
		//设置文件类型  
		response.setContentType("application/vnd.ms-excel; charset=utf-8");
		response.setHeader("Content-Disposition","attachment;filename=" + sheetName);
		response.setCharacterEncoding("utf-8");
          
        byte[] buffer = new byte[1024];   
        int bytesRead;  
        while ((bytesRead = inputStream.read(buffer)) != -1){  
            outputStream.write(buffer, 0, bytesRead);  
        }  
          
        outputStream.close();
	}
}
