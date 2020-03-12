package com.dziadkouskaya.findinshop.by.parsing.model.entity.shops;

import android.util.Log;

import com.dziadkouskaya.findinshop.by.parsing.model.entity.request.Request;
import com.dziadkouskaya.findinshop.by.parsing.model.stringCorrect.StringCorrect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Oz extends ShopNet{
    public static final String OZ_NAME = "Oz.by";

    public static final String OZ_URL = "https://oz.by";

    public static final String OZ_START = "https://oz.by/search/?q=";
    public static final String OZ_FINISH = "";

    public static final String OZ_COMMON = "viewer-type-card__li ";

    public static final String OZ_NO_PRODUCTS = "Оставить";

    public Oz() {
        this.setShopName(OZ_NAME);
        this.setMainUrl(OZ_URL);
        this.setUrlStart(OZ_START);
        this.setUrlFinish(OZ_FINISH);
        this.setItemCommon(OZ_COMMON);
    }


    @Override
    public ArrayList<ArrayList<String>> searchProduct(Request request) {
        ArrayList<String> responseContainer = new ArrayList<>();
        ArrayList<ArrayList<String>> container = new ArrayList<ArrayList<String>>();

        String urlRequest = getUrlStart() + request.getRequest() + getUrlFinish();
        try {
            Document document = Jsoup.connect(urlRequest).get();

            Elements elements = document.select("li[class = " + getItemCommon() + "]");

            String name = "";
            String price = "";
            String image = "";
            String link = "";

            for (Element element : elements) {
                String itemUrl = getMainUrl() + element.select("div[class = item-type-card__inner] > a").attr("href");
                Document itemDocument = Jsoup.connect(itemUrl).get();

                String itemName = itemDocument.select("div[class = b-product-title__heading]").text();
                String itemProducer = itemDocument.select("div[class = b-product-title__author] > a").text();


                String priceRub = itemDocument.select("div[class = b-product-control__row]").text();
                Log.d("PriceOZ", priceRub);

                if (priceRub.contains(OZ_NO_PRODUCTS)) {
                    continue;

                } else {
                    //Log.d("PriceOZ", priceRub);
                    price = StringCorrect.priceRubCorrection(priceRub);

                    name = itemName + " " + itemProducer;

                    //Log.d("Log_OZ_name", name);

                    name = StringCorrect.stringCorrectForParsing(name);

                    image = itemDocument.select("div[class = b-product-photo__picture-self] " +
                            "> figure > a").attr("href");

                    link = itemUrl;


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

    @Override
    public String toString() {
        return super.toString();
    }
}
