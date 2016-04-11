package com.zjjf.analysis.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zjjf.analysis.common.constants.ViewMap;
import com.zjjf.analysis.controller.IView;

@Service
public abstract class AbstractBaseServcie implements IView{
	
	public abstract Object[] sort_by_viewTitle(HashMap<String, Object> t, String [] viewTitle);
	
	public String [] getColumnId(){
		
		String[][] tableView = ViewMap.orderMapView();
		String [] idColumn = new String[tableView.length];
		for (int i = 0; i < tableView.length; i++) {
			String[] str = tableView[i];
			idColumn[i] = str[0];
		}
		return idColumn;
	}
	
	public String [] getColumnName(){

		String[][] tableView = ViewMap.orderMapView();
		String [] nameColumn = new String[tableView.length];
		for (int i = 0; i < tableView.length; i++) {
			String[] str = tableView[i];
			nameColumn[i] = str[1];
		}
		return nameColumn;
	}
	
	
	public List<Object[]> stand_by_title(List<HashMap<String, Object>> _list, String [] idColumn){
		
		 List<Object[]> idColumnList = new ArrayList<Object[]>();
		for (HashMap<String, Object> t : _list) {
			Object[] temp = sort_by_viewTitle(t, idColumn);
			idColumnList.add(temp);
		}
		return idColumnList;
	}
	
}
