package com.dziadkouskaya.findinshop.by.parsing.model.entity.shops;

import android.util.Log;

import com.dziadkouskaya.findinshop.by.parsing.model.entity.request.Request;
import com.dziadkouskaya.findinshop.by.parsing.model.stringCorrect.StringCorrect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Buslic extends ShopNet {
    public static final String BUSLIK_NAME = "Буслик";

    public static final String BUSLIK_URL = "https://buslik.by";

    public static final String BUSLIK_START = "https://buslik.by/catalog/?q=";
    public static final String BUSLIK_FINISH = "&category=0&PAGEN_2=";

    public static final String BUSLIK_COMMON = "border-block";

    public static final int BUSLIC_FIRST_SEARCH_PAGE = 1;
    public static final int BUSLIC_QUANTITY_OF_UNUSED_SYMBOLS_IN_PAGINATION = 2;


    public Buslic() {
        super.setShopName(BUSLIK_NAME);
        super.setMainUrl(BUSLIK_URL);
        super.setUrlStart(BUSLIK_START);
        super.setUrlFinish(BUSLIK_FINISH);
        super.setItemCommon(BUSLIK_COMMON);
    }

    @Override
    public ArrayList<ArrayList<String>> searchProduct(Request request) {
        //main container for data tranfer to main ArrayMap
        ArrayList<ArrayList<String>> container = new ArrayList<ArrayList<String>>();

        //arraylist for 1 item in main container
        ArrayList<String> responseContainer = new ArrayList<>();

        String urlRequest = getUrlStart() + request.getRequest() + getUrlFinish();
        try {
            //document of 1 search page
            Document document = Jsoup.connect(urlRequest + BUSLIC_FIRST_SEARCH_PAGE).get();

            //search all pages with results for search in the shop
            Elements searchPages = null;
            searchPages = document.select("ul[class = pagination] > li > a");

            // заводим переменную для обозначения последней страницы результатов поиска в магазине
            String pagesWithResualtsOfSearch = "";

            //заводим контейнер для хранения инфы о номерах страниц поиска
            ArrayList<String> containerForPages = new ArrayList<>();

            //last number for pages count
            int lastPage = 0;

            if (searchPages.text().equals("")) {
                lastPage = BUSLIC_FIRST_SEARCH_PAGE;

            } else {

                for (Element page : searchPages) {
                    String eachPage = page.text();
                    containerForPages.add(eachPage);
                }

                int length = containerForPages.size();


                String lastPoint = containerForPages.get(length - BUSLIC_QUANTITY_OF_UNUSED_SYMBOLS_IN_PAGINATION);

                lastPage += Integer.parseInt(lastPoint);

            }

            //request for each page with results for search in the shop
            for (int i = 1; i <= lastPage; i++) {
                pagesWithResualtsOfSearch = urlRequest + i;
                //Log.d("LOGG_link", pagesWithResualtsOfSearch);


                //search elements on each page of search
                Document docEachPage = Jsoup.connect(pagesWithResualtsOfSearch).get();

                Elements elements = docEachPage.select("div[class = " + getItemCommon() + "]");

                String name;
                String price;
                String image;
                String link;

                for (Element element : elements) {
                    name = element.select("a[class = border-block__name]").text();

                    name = StringCorrect.stringCorrectForParsing(name);

                    image = getMainUrl() + element.select("img").attr("src");
                    //Log.d("LOG_BUSLIK_photo", image);

                    link = getMainUrl() + element.select("p > a").attr("href");

                    price = element.select("p[class = product-price]").text();
                    price = StringCorrect.priceRubCorrection(price);
                    price = StringCorrect.priceCorrectionFrom_OT(price);

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
