package com.dziadkouskaya.findinshop.by.parsing.model.entity.shops;

import java.util.ArrayList;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dziadkouskaya.findinshop.by.parsing.model.entity.request.Request;
import com.dziadkouskaya.findinshop.by.parsing.model.stringCorrect.StringCorrect;

public class Hypermall extends ShopNet {
    public static final String HYPERMALL_SHOP_NAME = "Гипермолл";
    public static final String HYPERMALL_MAIN_URL = "https://gipermall.by";
    public static final String HYPERMALL_URL_START = "https://gipermall.by/search/?searchtext=";
    public static final String HYPERMALL_URL_FINISH = "";
    public static final String HYPERMALL_ITEM_COMMON = "form_wrapper";


    public Hypermall() {
        super.setUrlStart(HYPERMALL_URL_START);
        super.setUrlFinish(HYPERMALL_URL_FINISH);
        super.setShopName(HYPERMALL_SHOP_NAME);
        super.setMainUrl(HYPERMALL_MAIN_URL);
        super.setItemCommon(HYPERMALL_ITEM_COMMON);
    }

    @Override
    public String toString() {
        return "Hypermall{}";
    }



    @Override
    public ArrayList<ArrayList<String>> searchProduct(Request request) {
        ArrayList<String> responseContainer = new ArrayList<>();
        ArrayList<ArrayList<String>> container = new ArrayList<ArrayList<String>>();

        String urlRequest = getUrlStart() + request.getRequest() + getUrlFinish();
        try {
            Document document = Jsoup.connect(urlRequest).get();

            Elements elements = document.select("div[class = " + getItemCommon() + "]");

            String name = "";
            String price = "";
            String image = "";
            String link = "";

            for (Element element : elements) {
                name = element.select("div[class = img] > a > img").attr("title");

                name = StringCorrect.stringCorrectForParsing(name);

                image = element.select("div[class = img] > a > img").attr("src");

                link = element.select("div[class = img] > a").attr("href");


                String priceRub = element.select("div[class = price]").text();
                priceRub = StringCorrect.priceRubCorrection(priceRub);
                String priceCent = element.select("span[class = cent]").text();
                priceCent = StringCorrect.priceCentCorrection(priceCent);

                price = priceRub + "." + priceCent;

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
