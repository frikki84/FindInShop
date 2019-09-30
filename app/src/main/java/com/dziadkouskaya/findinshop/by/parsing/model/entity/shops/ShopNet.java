package com.dziadkouskaya.findinshop.by.parsing.model.entity.shops;

import java.util.ArrayList;

import com.dziadkouskaya.findinshop.by.parsing.model.entity.request.Request;

public abstract class ShopNet {
	private String shopName;
	private String mainUrl;
	private String urlStart;
	private String urlFinish;
	private String itemCommon;



	public ShopNet() {

	}

	public ShopNet(String shopName, String urlStart, String urlFinish) {
		this.shopName = shopName;
		this.urlStart = urlStart;
		this.urlFinish = urlFinish;
	}

	public ShopNet(String shopName,String urlStart, String urlFinish, String mainUrl) {
		this.shopName = shopName;
		this.urlStart = urlStart;
		this.urlFinish = urlFinish;
		this.mainUrl = mainUrl;
	}

	public ShopNet(String shopName,String urlStart, String urlFinish, String mainUrl, String itemCommon) {
		this.shopName = shopName;
		this.urlStart = urlStart;
		this.urlFinish = urlFinish;
		this.mainUrl = mainUrl;
		this.itemCommon = itemCommon;
	}



	public ShopNet(ShopNet shopNet) {
		this.shopName = shopNet.shopName;
		this.urlStart = shopNet.urlStart;
		this.urlFinish = shopNet.urlFinish;
		this.mainUrl = shopNet.mainUrl;
		this.itemCommon = shopNet.itemCommon;

	}



	@Override
	public String toString() {
		return "ShopNet [shopName=" + shopName + ", mainUrl=" + mainUrl + ", urlStart=" + urlStart + ", urlFinish="
				+ urlFinish + ", itemCommon=" + itemCommon + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemCommon == null) ? 0 : itemCommon.hashCode());
		result = prime * result + ((mainUrl == null) ? 0 : mainUrl.hashCode());
		result = prime * result + ((shopName == null) ? 0 : shopName.hashCode());
		result = prime * result + ((urlFinish == null) ? 0 : urlFinish.hashCode());
		result = prime * result + ((urlStart == null) ? 0 : urlStart.hashCode());
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
		ShopNet other = (ShopNet) obj;
		if (itemCommon == null) {
			if (other.itemCommon != null)
				return false;
		} else if (!itemCommon.equals(other.itemCommon))
			return false;
		if (mainUrl == null) {
			if (other.mainUrl != null)
				return false;
		} else if (!mainUrl.equals(other.mainUrl))
			return false;
		if (shopName == null) {
			if (other.shopName != null)
				return false;
		} else if (!shopName.equals(other.shopName))
			return false;
		if (urlFinish == null) {
			if (other.urlFinish != null)
				return false;
		} else if (!urlFinish.equals(other.urlFinish))
			return false;
		if (urlStart == null) {
			if (other.urlStart != null)
				return false;
		} else if (!urlStart.equals(other.urlStart))
			return false;
		return true;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public String getUrlStart() {
		return urlStart;
	}

	public void setUrlStart(String urlStart) {
		this.urlStart = urlStart;
	}

	public String getUrlFinish() {
		return urlFinish;
	}

	public void setUrlFinish(String urlFinish) {
		this.urlFinish = urlFinish;
	}

	public String getItemCommon() {
		return itemCommon;
	}

	public void setItemCommon(String itemCommon) {
		this.itemCommon = itemCommon;
	}

	public abstract ArrayList<ArrayList<String>> searchProduct(Request request);



}
