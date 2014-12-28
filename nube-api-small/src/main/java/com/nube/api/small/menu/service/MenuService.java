package com.nube.api.small.menu.service;

import java.util.List;

import com.nube.api.small.menu.vo.Menu;
import com.nube.api.small.menu.vo.MenuItem;
import com.nube.core.exception.NubeException;

public interface MenuService {

	/**
	 * Insert menu
	 * 
	 * @param menu
	 * @throws NubeException
	 */
	public void insert(Menu menu) throws NubeException;

	/**
	 * Insert menu item
	 * 
	 * @param menuId
	 * @param item
	 * @throws NubeException
	 */
	public void insertMenuItem(String context, String menuId, MenuItem item) throws NubeException;
	
	/**
	 * delete a menu item
	 * @param context
	 * @param menuId
	 * @param menuItemId
	 * @throws NubeException
	 */
	public void deleteMenuItem(String context, String menuId, String menuItemId) throws NubeException;


	/**
	 * Read all menus for a context
	 * @param context
	 * @return
	 * @throws NubeException
	 */
	public List<Menu> read(String context) throws NubeException;
	
	/**
	 * Read one menu
	 * @param context
	 * @return
	 * @throws NubeException
	 */
	public Menu read(String context, String menuId) throws NubeException;

	
	
	/**
	 * Delete item or all
	 * 
	 * @param menuId
	 * @param menuItemId
	 * @throws NubeException
	 */
	public void delete(String context, String menuId) throws NubeException;
}
