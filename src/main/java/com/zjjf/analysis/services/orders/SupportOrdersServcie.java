package com.zjjf.analysis.services.orders;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjjf.analysis.beans.vo.DictionaryVo;
import com.zjjf.analysis.common.constants.ViewMap;
import com.zjjf.analysis.mapper.analysis.SupportOrderPageMapper;
import com.zjjf.analysis.services.AbstractBaseServcie;

@Service
public class SupportOrdersServcie extends AbstractBaseServcie {

	private final String[][] orderMapView = ViewMap.orderMapView();

	@Autowired
	private SupportOrderPageMapper supportOrderPageMapper;

	public String[] getOrderTitle() {

		return getColumn(orderMapView, 1);
	}

	public String[] getOrderColumnName() {

		return getColumn(orderMapView, 0);
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
		return stand_by_title(dataList, getOrderColumnName());
	}

	public List<Object[]> getExcelData(HashMap<String, Object> paramMap) {

		List<HashMap<String, Object>> dataList = supportOrderPageMapper.getExcelData(paramMap);
		return stand_by_title(dataList, getOrderColumnName());
	}

	public List<List<DictionaryVo>> getOptionList() {

		List<List<DictionaryVo>> optionList = new ArrayList<List<DictionaryVo>>();

		HashMap<String, Object> cityMap = new HashMap<String, Object>();
		cityMap.put("regionLevel", 3);
		cityMap.put("pid", 6);
		optionList.add(0, getRegionCodeList(cityMap));
		HashMap<String, Object> areaMap = new HashMap<String, Object>();
		areaMap.put("regionLevel", 4);
		areaMap.put("pid", 77);
		optionList.add(1, getRegionCodeList(areaMap));
		HashMap<String, Object> spGroupMap = new HashMap<String, Object>();
		spGroupMap.put("regionLevel", 4);
		spGroupMap.put("pid", 77);
		optionList.add(2, getSpGroupCodeList(spGroupMap));
		return optionList;
	}
}
