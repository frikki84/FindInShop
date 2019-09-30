package com.dziadkouskaya.findinshop.by.parsing.model.entity.shops;

import java.util.ArrayList;

public class ShopList {
	private ArrayList<ShopNet> shopList;
	
	public ShopList() {
		shopList = new ArrayList<ShopNet>();
	}
	
	public ShopList(ShopList list) {
		this.shopList = shopList;
	}
	
	public ArrayList<ShopNet> getShopList() {
		return shopList;
	}
	
	public void setShopList(ArrayList<ShopNet> shopList) {
		this.shopList = shopList;
	}
	
	public  void addShopNetInList(ShopNet net) {
		shopList.add(net);
	}
	
	public  void addShopNetInList(ShopNet [] net) {
		for (int i = 0; i < net.length; i++) {
			shopList.add(net[i]);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shopList == null) ? 0 : shopList.hashCode());
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
		ShopList other = (ShopList) obj;
		if (shopList == null) {
			if (other.shopList != null)
				return false;
		} else if (!shopList.equals(other.shopList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ShopList [shopList=" + shopList + "]";
	}
	
	
	
	
	
	
	

}
