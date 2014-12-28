package com.nube.api.small.menu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nube.api.small.menu.request.MenuItemRequest;
import com.nube.api.small.menu.service.MenuService;
import com.nube.api.small.menu.vo.Menu;
import com.nube.api.small.menu.vo.MenuItem;
import com.nube.core.exception.NubeException;
import com.nube.core.vo.response.ValidResponse;
import com.nube.core.vo.response.VoidResponse;

/**
 * Menu Rest controller. See method comments to use this api
 * 
 * @author kamoorr
 *
 */
@Controller
@RequestMapping("/v1/menu")
public class MenuController {

	static Logger logger = Logger.getLogger(MenuController.class);

	@Autowired
	MenuService menuService;

	@PostConstruct
	public void postConstruct() {
		logger.info("menu api initialized");
	}

	/**
	 * Create a new menu, Content-Type should be application/json. Request Body
	 * should map to Menu.java Request Method: POST
	 * 
	 * @see Menu
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ValidResponse<VoidResponse> create(
			@RequestBody Menu menu) {
		logger.info("Create a new menu " + menu.getMenuId());
		try {
			menuService.insert(menu);
			return new ValidResponse(new VoidResponse());
		} catch (NubeException nbe) {
			logger.error("error creating menu", nbe);
			return new ValidResponse<VoidResponse>(nbe);

		}

	}

	/**
	 * delete a menu context and menuId are mandatory Content-Type should be
	 * application/json. Request Body should map to Menu.java (only menuId and
	 * context is required) Request Method: POST
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ValidResponse<VoidResponse> delete(
			@RequestBody Menu menu) {
		logger.debug(String.format("Delete context=%s, menu=%s ",
				menu.getContext(), menu.getMenuId()));
		try {
			menuService.delete(menu.getContext(), menu.getMenuId());
			return new ValidResponse(new VoidResponse());
		} catch (NubeException nbe) {
			logger.error("error deleting menu", nbe);
			return new ValidResponse<VoidResponse>(nbe);
		}
	}

	/**
	 * Add new menu item Content-Type should be application/json. Request Body
	 * should map to MenuItem.java Request Method: POST
	 * 
	 * @see MenuItem
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add-item", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ValidResponse<VoidResponse> addMenuItem(
			@RequestBody MenuItemRequest menuItem) {
		logger.debug("Add a new menu item " + menuItem.getMenuId());
		try {
			menuService.insertMenuItem(menuItem.getContext(),
					menuItem.getMenuId(), menuItem);

			return new ValidResponse(new VoidResponse());
		} catch (NubeException nbe) {
			logger.error("error creating menu", nbe);
			return new ValidResponse<VoidResponse>(nbe);

		}

	}

	/**
	 * delete a menu context and menuId are mandatory Content-Type should be
	 * application/json. Request Body should map to MenuItemRequest.java (only
	 * menuItemId, menuId and context is required) Request Method: POST
	 * 
	 * @see MenuItemRequest
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/delete-item", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ValidResponse<VoidResponse> deleteMenuItem(
			@RequestBody MenuItemRequest menuItem) {
		logger.debug(String.format(
				"Delete MenuItem context=%s, menuId=%s, menuItemId ",
				menuItem.getContext(), menuItem.getMenuId(),
				menuItem.getMenuItemId()));
		try {
			menuService.deleteMenuItem(menuItem.getContext(),
					menuItem.getMenuId(), menuItem.getMenuItemId());
			return new ValidResponse(new VoidResponse());
		} catch (NubeException nbe) {
			logger.error("error deleting menu", nbe);
			return new ValidResponse<VoidResponse>(nbe);
		}
	}

	/**
	 * Get all menus for a context or a specific menu for a context 
	 * Example: "/my?context=xyz" Example: "/my?context=xyz&menuId=xyss"
	 * 
	 * @return
	 */
	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public @ResponseBody ValidResponse<List<Menu>> getMenu(
			@RequestParam(value = "context", required = true) String context,
			@RequestParam(value = "menuId", required = false) String menuId) {

		try {
			logger.debug("Get menu for  " + context);
			if (menuId == null) {
				return new ValidResponse<List<Menu>>(menuService.read(context));
			} else {
				List<Menu> menu = new ArrayList<Menu>();
				menu.add(menuService.read(context, menuId));
				return new ValidResponse<List<Menu>>(menu);
			}

		} catch (NubeException nbe) {
			logger.error("error getting menu: context =" + context + "-menuId="
					+ menuId);
			return new ValidResponse<List<Menu>>(nbe);
		}
	}

}
