package com.dziadkouskaya.findinshop.by.parsing.model.entity.shops;


import java.util.ArrayList;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dziadkouskaya.findinshop.by.parsing.model.entity.request.Request;
import com.dziadkouskaya.findinshop.by.parsing.model.stringCorrect.StringCorrect;

public class OstrovChistoty extends ShopNet {
    public static final String OCH_SHOP_NAME = "Остров Чистоты";

    public static final String OCH_MAIN_URL = "https://ostrov-shop.by";

    public static final String OCH_URL_START = "https://ostrov-shop.by/catalog/?q=";
    public static final String OCH_URL_FINISH = "&how=";

    public static final String OCH_ITEM_COMMON = "catalog_item_wrapp";



    public OstrovChistoty() {
        super.setUrlStart(OCH_URL_START);
        super.setUrlFinish(OCH_URL_FINISH);
        super.setShopName(OCH_SHOP_NAME);
        super.setMainUrl(OCH_MAIN_URL);
        super.setItemCommon(OCH_ITEM_COMMON);


    }



    @Override
    public String toString() {
        return "OstrovChistoty [toString()=" + super.toString() + ", hashCode()=" + hashCode() + ", getShopName()="
                + getShopName() + ", getMainUrl()=" + getMainUrl() + ", getUrlStart()=" + getUrlStart()
                + ", getUrlFinish()=" + getUrlFinish() + ", getItemCommon()=" + getItemCommon() + ", getClass()="
                + getClass() + "]";
    }





    @Override
    public ArrayList <ArrayList<String>> searchProduct(Request request) {
        ArrayList <String> responseContainer = new ArrayList<>();
        ArrayList<ArrayList<String>> container = new ArrayList<ArrayList<String>>();

        String urlRequest = getUrlStart() + request.getRequest() + getUrlFinish();
        try {
            Document document = Jsoup.connect(urlRequest).get();

            Elements elements = document.select("div[class = " + getItemCommon() +"]");

            String name;
            String price;
            String image;
            String link;

            for (Element element : elements) {
                name = element.select("img").attr("title");

                name = StringCorrect.stringCorrectForParsing(name);

                image = getMainUrl() + element.select("img").attr("src");

                link = getMainUrl() + element.select("a").attr("href");

                price = element.select("div[class = price]").text();
                price = StringCorrect.priceRubCorrection(price);

                responseContainer.add(name);
                responseContainer.add(price);
                responseContainer.add(image);
                responseContainer.add(link);

                ArrayList<String> mArr = new ArrayList<String>();
                mArr = (ArrayList<String>) responseContainer.clone();

                container.add(mArr);

                responseContainer.clear();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return container;
    }



}

	
	

	



