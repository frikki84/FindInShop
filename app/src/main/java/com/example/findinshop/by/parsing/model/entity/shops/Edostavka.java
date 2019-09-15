package com.example.findinshop.by.parsing.model.entity.shops;

import com.example.findinshop.by.parsing.model.entity.request.Request;
import com.example.findinshop.by.parsing.model.stringCorrect.StringCorrect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Edostavka extends ShopNet{
    public static final String EDOSTAVKA_SHOP_NAME = "Е-доставка";
    public static final String EDOSTAVKA_MAIN_URL = "https://e-dostavka.by";
    public static final String EDOSTAVKA_URL_START = "https://e-dostavka.by/search/?searchtext=";
    public static final String EDOSTAVKA_URL_FINISH = "";
    public static final String EDOSTAVKA_ITEM_COMMON = "form_wrapper";


    public Edostavka() {
        super.setUrlStart(EDOSTAVKA_URL_START);
        super.setUrlFinish(EDOSTAVKA_URL_FINISH);
        super.setShopName(EDOSTAVKA_SHOP_NAME);
        super.setMainUrl(EDOSTAVKA_MAIN_URL);
        super.setItemCommon(EDOSTAVKA_ITEM_COMMON);
    }

    @Override
    public String toString() {
        return "Edostavka{}";
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
