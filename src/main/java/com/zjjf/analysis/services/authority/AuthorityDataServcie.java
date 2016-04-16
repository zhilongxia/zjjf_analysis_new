package com.zjjf.analysis.services.authority;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjjf.analysis.beans.analysis.authority.BaseRole;
import com.zjjf.analysis.beans.analysis.base.BaseRoleData;
import com.zjjf.analysis.common.constants.Constants;
import com.zjjf.analysis.mapper.analysis.BaseRoleDataMapper;

@Service
public class AuthorityDataServcie extends BaseRoleServcie {

	@Autowired
	private BaseRoleDataMapper baseRoleDataMapper;

	/**
	 * 获取权限字段
	 * 
	 * @param paramMap
	 */
	public Object[][] getAuthorityFilter(String userId, Integer menuId, String[][] tableView) {

		List<BaseRole> roleList = getBaseRoleByUserId(userId);
		String roleIds = "(";
		for (BaseRole baseRole : roleList) {
			roleIds += baseRole.getId() + ",";
		}
		HashMap<String, Object> dataParam = new HashMap<String, Object>();
		dataParam.put("menuId", menuId);
		dataParam.put("roleIds", roleIds.substring(0, roleIds.length() - 1) + ")");
		BaseRoleData baseRoleData = baseRoleDataMapper.getAuthorityData(dataParam);
		if (baseRoleData == null) {
			return new Object[][] {};
		}
		return orderBy_view(baseRoleData.getTableKey().split(","), tableView);
	}

	private Object[][] orderBy_view(String needKeys[], String[][] tableView) {

		Object[][] authorityArray = new Object[needKeys.length][2];
		int k = 0;
		for (int i = 0; i < tableView.length; i++) {
			String[] viewKey = tableView[i];
			for (int j = 0; j < needKeys.length; j++) {
				String key = needKeys[j].trim();
				if (viewKey != null && viewKey[0].equals(key)) {
					authorityArray[k][0] = Constants.authority_query + viewKey[0];
					authorityArray[k][1] = viewKey[1];
					k++;
				}
			}
		}
		return authorityArray;
	}
}
