package com.nube.api.small.menu.request;

import com.nube.api.small.menu.vo.MenuItem;

public class MenuItemRequest extends MenuItem{
	
	private String context;
	
	private String menuId;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
	
	
	

}
