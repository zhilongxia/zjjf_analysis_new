package com.zjjf.analysis.controller;

public interface IFilter {
	
	/**
	 * key 0 为id key 1为name
	 * 
	 * @param tableView
	 * @param key
	 * @return
	 */
	String [] getColumn(String[][] tableView, Integer key);
}
