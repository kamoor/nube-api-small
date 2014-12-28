package com.nube.api.small.menu.dao;

import java.util.List;

import com.nube.api.small.menu.vo.Menu;
import com.nube.api.small.menu.vo.MenuItem;
import com.nube.core.exception.NubeException;

public interface MenuDao {

	/**
	 * Insert menu
	 * 
	 * @param menu
	 * @throws NubeException
	 */
	public void insert(Menu menu) throws NubeException;

	
	/**
	 * Update menu
	 * @param menuId
	 * @param menu
	 * @throws NubeException
	 */
	public void update(String context, String menuId, Menu menu)throws NubeException;
	
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
	 * delete
	 * 
	 * @param menuId
	 * @throws NubeException
	 */
	public void delete(String context, String menuId) throws NubeException;

	
}
