package com.nube.api.small.menu.vo;

import com.nube.core.vo.media.Image;

/**
 * A simple representation of menu items 
 * @author kamoorr
 *
 */
public class MenuItem {
	
	private String menuItemId;
	
	private String category;
	
	private String title;
	
	private String descr;
	
	private short order;
	
	private double price;
	
	private double price2;
	
	private double price3;
	
	private String marketingContent;
	
	private Image[] images;
	
	public MenuItem(){
		
	}
	
	public MenuItem(String menuItemId){
		this.menuItemId = menuItemId;
	}
	
	public short getOrder() {
		return order;
	}

	public void setOrder(short order) {
		this.order = order;
	}

	public Image[] getImages() {
		return images;
	}

	public void setImages(Image[] images) {
		this.images = images;
	}

	

	public void setMenuItemId(String menuItemId) {
		this.menuItemId = menuItemId;
	}

	
	
	public String getMenuItemId() {
		return menuItemId;
	}

	public void setMenuId(String menuItemId) {
		this.menuItemId = menuItemId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getMarketingContent() {
		return marketingContent;
	}

	public void setMarketingContent(String marketingContent) {
		this.marketingContent = marketingContent;
	}
	
	
	

	public double getPrice2() {
		return price2;
	}

	public void setPrice2(double price2) {
		this.price2 = price2;
	}

	public double getPrice3() {
		return price3;
	}

	public void setPrice3(double price3) {
		this.price3 = price3;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((menuItemId == null) ? 0 : menuItemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		if (menuItemId == null) {
			if (other.menuItemId != null)
				return false;
		} else if (!menuItemId.equals(other.menuItemId))
			return false;
		return true;
	}

	
	

	
	
	
	
	

}
