package com.dziadkouskaya.findinshop.by.parsing.model.entity.shops;


import android.util.Log;

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

    public static final String OCH_URL_START = "https://ostrov-shop.by/catalog/?PAGEN_2=";
    public static final String OCH_URL_FINISH = "&q=";

    public static final String OCH_ITEM_COMMON = "catalog_item_wrapp";

    public static final int NUMBER_OF_FIRST_PAGE_OF_SEACH = 1;


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
    public ArrayList<ArrayList<String>> searchProduct(Request request) {
        ArrayList<String> responseContainer = new ArrayList<>();
        ArrayList<ArrayList<String>> container = new ArrayList<ArrayList<String>>();

        String urlRequest = getUrlStart() + NUMBER_OF_FIRST_PAGE_OF_SEACH + getUrlFinish() + request.getRequest();

        try {
            //document of 1 search page
            Document document = Jsoup.connect(urlRequest).get();

            //create container for pages because there's no 1 page on site
            ArrayList<String> containerForPages = new ArrayList<>();
            containerForPages.add("1");

            //search all pages with results for search in the shop
            Elements searchPages = document.select("div[class = module-pagination] > span[class = nums] > a");

            for (Element page : searchPages) {
                String eachPage = page.text();
                containerForPages.add(eachPage);
            }

                for (String pageInContainer : containerForPages) {

                    //request for each page with results for search in the shop
                    String pagesWithResualtsOfSearch = getUrlStart() + pageInContainer + getUrlFinish() + request.getRequest();

                    //search elements on each page of search
                    Document docEachPage = Jsoup.connect(pagesWithResualtsOfSearch).get();

                    Elements elements = docEachPage.select("div[class = " + getItemCommon() + "]");

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

                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return container;
    }

}

	
	

	



