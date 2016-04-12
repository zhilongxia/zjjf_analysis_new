package com.zjjf.analysis.services.orders;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjjf.analysis.common.constants.ViewMap;
import com.zjjf.analysis.mapper.analysis.SupportOrderPageMapper;
import com.zjjf.analysis.services.AbstractBaseServcie;

@Service
public class SupportOrdersServcie extends AbstractBaseServcie {

	private final String[][] orderMapView = ViewMap.orderMapView();
	
	@Autowired
	private SupportOrderPageMapper supportOrderPageMapper;

	public String[] getOrderTitle( Integer index) {

		return getColumn(orderMapView, 0);
	}

	public String[] getOrderColumnName(Integer index) {

		return getColumn(orderMapView, 1);
	}

	@Override
	public Object[] sort_by_viewTitle(HashMap<String, Object> t, String[] viewTitle) {

		Object[] row = new Object[viewTitle.length];
		for (int i = 0; i < viewTitle.length; i++) {
			if (t.containsKey(viewTitle[i])) {
				row[i] = t.get(viewTitle[i]);
			}
		}
		return row;
	}

	/**
	 * 导出文件
	 * 
	 * @param request
	 * @param response
	 */
	public InputStream exportOrderList(HttpServletRequest request, HttpServletResponse response, String sheetName, HashMap<String, Object> paramMap) {

		List<Object[]> dataList = getExcelData(paramMap);
		String[] titleColumn = getColumn(orderMapView, 1);
		return this.createExcel(sheetName, titleColumn, dataList);
	}
	
	public List<Object[]> getOrderData(HashMap<String, Object> paramMap) {

		List<HashMap<String, Object>> dataList = supportOrderPageMapper.getOrderData(paramMap);
		return stand_by_title(dataList, getOrderTitle(1));
	}

	public List<Object[]> getExcelData(HashMap<String, Object> paramMap) {

		List<HashMap<String, Object>> dataList = supportOrderPageMapper.getExcelData(paramMap);
		return stand_by_title(dataList, getOrderTitle( 1));
	}
}
