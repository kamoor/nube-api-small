package com.nube.api.small.menu.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nube.api.small.menu.dao.MenuDao;
import com.nube.api.small.menu.vo.Menu;
import com.nube.api.small.menu.vo.MenuItem;
import com.nube.core.constants.ErrorCodes;
import com.nube.core.exception.NubeException;
import com.nube.core.util.string.StringUtil;

@Service
public class MenuServiceImpl implements MenuService {

	static Logger logger = Logger.getLogger(MenuServiceImpl.class);

	@Autowired
	MenuDao menuDao;

	/**
	 * insert a menu
	 */
	public void insert(Menu menu) throws NubeException {
		
		
		try{
			Menu existing = menuDao.read(menu.getContext(), menu.getMenuId());
			throw new NubeException(ErrorCodes.RECORD_ALREADY_EXISTS, "menu_exists");
		}catch(NubeException e){
			if(e.getErrorCode() == ErrorCodes.RECORD_NOT_FOUND){
				menu.setMenuId(StringUtil.toKey(menu.getMenuId()));
				menuDao.insert(menu);
			}else{
				throw e;
			}
		}

	}

	/**
	 * delete menu
	 */
	public void delete(String context, String menuId) throws NubeException {
		menuDao.delete(context, menuId);
	}

	/**
	 * add menu item
	 */
	public void insertMenuItem(String context, String menuId, MenuItem item)
			throws NubeException {
		Menu menu = menuDao.read(context, menuId);

		if (!CollectionUtils.isEmpty(menu.getMenuItems())  && menu.getMenuItems().contains(new MenuItem(item.getMenuItemId()))) {
				logger.error("menu item already exists");
				throw new NubeException(ErrorCodes.RECORD_ALREADY_EXISTS, "menu_item_already_exists");
		}
		item.setMenuItemId(StringUtil.toKey(item.getMenuItemId()));
		menu.getMenuItems().add(item);
		menuDao.update(context, menuId, menu);

	}

	/**
	 * delete menu item
	 */
	public void deleteMenuItem(String context, String menuId, String menuItemId)
			throws NubeException {

		Menu menu = menuDao.read(context, menuId);

		if (!CollectionUtils.isEmpty(menu.getMenuItems())) {
			menu.getMenuItems().remove(new MenuItem(menuItemId));
			logger.info(String.format("Removed menu item %s from %s, newSize=%s",
					menuItemId, menuId, menu.getMenuItems().size()));
		}
		menuDao.update(context, menuId, menu);

	}

	/**
	 * Read the whole menu for a context
	 */
	public List<Menu> read(String context) throws NubeException {

		return menuDao.read(context);

	}

	/**
	 * Read the whole menu for a context
	 */
	public Menu read(String context, String menuId) throws NubeException {

		return menuDao.read(context, menuId);
	}

}
