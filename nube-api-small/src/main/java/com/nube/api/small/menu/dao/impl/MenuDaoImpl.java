package com.nube.api.small.menu.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.nube.api.small.menu.dao.MenuDao;
import com.nube.api.small.menu.vo.Menu;
import com.nube.api.small.menu.vo.MenuItem;
import com.nube.core.constants.ErrorCodes;
import com.nube.core.dao.common.ContentProcessor;
import com.nube.core.dao.mongo.AbstractMongoDao;
import com.nube.core.dao.mongo.MongoConnection;
import com.nube.core.exception.NubeException;
import com.nube.core.vo.idm.Role;
import com.nube.core.vo.idm.User;
import com.nube.core.vo.media.Image;

/**
 * Store and retrieve menu in mongo, "menu" collection
 * @author kamoorr
 *
 */
@Repository
@Profile("default")
public class MenuDaoImpl extends AbstractMongoDao<BasicDBObject, Menu> implements MenuDao{

	@Autowired
	MongoConnection mongoConnection;

	private static final String COLLECTION = "smb-menu";

	static Logger logger = Logger.getLogger(MenuDaoImpl.class);

	public DBCollection getCollection() {
		return mongoConnection.getCollection(COLLECTION);
	}

	/**
	 * insert a menu
	 */
	public void insert(Menu menu) throws NubeException {
		getCollection().save(this.serialize(menu));

	}
	
	/**
	 * Update
	 */
	public void update(String context, String menuId, Menu menu) throws NubeException {
			logger.info("Updating "+ menuId.toString());
			BasicDBObject newDoc = new BasicDBObject().append("$set", this.serialize(menu));
			getCollection().update(new BasicDBObject().append("menuId", menuId).append("context", context), newDoc);
	}

	/**
	 * Read the whole menu for a context
	 */
	public List<Menu> read(String context) throws NubeException {

		DBCursor cursor = getCollection().find(new BasicDBObject().append("context", context));
		if (cursor.count() == 0) {
			throw new NubeException(ErrorCodes.RECORD_NOT_FOUND, "menu_not_found");
		}
		List<Menu> results = new ArrayList<Menu>();
		while (cursor.hasNext()) {
			results.add(this.parse((BasicDBObject) cursor.next()));
		}
		return results;

	}

	/**
	 * Read the whole menu for a context
	 */
	public Menu read(String context, String menuId) throws NubeException {

		BasicDBObject dbObject = (BasicDBObject) getCollection().findOne(
				new BasicDBObject().append("context", context).append("menuId", menuId));
		if (dbObject == null || dbObject.isEmpty()) {
			throw new NubeException(ErrorCodes.RECORD_NOT_FOUND, "menu_not_found");
		}
		return this.parse(dbObject);
	}
	
	

	public void delete(String context, String menuId) throws NubeException {
		getCollection().remove(new BasicDBObject().append("context", context).append("menuId", menuId));

	}

	
	public Menu parse(BasicDBObject dbObject) {
		
		BasicDBList menuItemListObj = (BasicDBList)dbObject.get("menuItems");
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		if(!CollectionUtils.isEmpty(menuItemListObj)){
			for(Object menuItemObj: menuItemListObj){
				
				MenuItem menuItem = new MenuItem();
				menuItem.setMenuItemId(((BasicDBObject)menuItemObj).getString("menuItemId"));
				menuItem.setCategory(((BasicDBObject)menuItemObj).getString("category"));
				menuItem.setTitle(((BasicDBObject)menuItemObj).getString("title"));
				menuItem.setDescr(((BasicDBObject)menuItemObj).getString("descr"));
				menuItem.setOrder((short)((BasicDBObject)menuItemObj).getInt("order"));
				menuItem.setPrice(((BasicDBObject)menuItemObj).getDouble("price"));
				menuItem.setPrice2(((BasicDBObject)menuItemObj).getDouble("price2"));
				menuItem.setPrice3(((BasicDBObject)menuItemObj).getDouble("price3"));
				
				menuItem.setMarketingContent(((BasicDBObject)menuItemObj).getString("marketingContent"));
				menuItems.add(menuItem);
				//images todo
			}
		}
		Menu menu= new Menu(dbObject.getString("context"), dbObject.getString("menuId"), menuItems);
		menu.setDescr(dbObject.getString("descr"));
		return menu;
		
	}

	public BasicDBObject serialize(Menu pojo) {
		BasicDBObject menuObject = new BasicDBObject().append("context", pojo.getContext())
				.append("menuId", pojo.getMenuId())
				.append("descr", pojo.getDescr());
		if(!CollectionUtils.isEmpty(pojo.getMenuItems())){
			BasicDBList mItemsList = new BasicDBList();
			for(MenuItem mItem: pojo.getMenuItems()){
				mItemsList.add(
						new BasicDBObject()
						.append("menuItemId", mItem.getMenuItemId())
						.append("category", mItem.getCategory())
						.append("title", mItem.getTitle())
						.append("descr", mItem.getDescr())
						.append("order", mItem.getOrder())
						.append("price", mItem.getPrice())
						.append("price2", mItem.getPrice2())
						.append("price3", mItem.getPrice3())
						.append("marketingContent", mItem.getMarketingContent())
						.append("images", mItem.getImages()));
			}
			menuObject.append("menuItems",mItemsList);
			
		}else{
			menuObject.append("menuItems",null);
		}
		return menuObject;
	}

	

}
