package com.example.findinshop.by.parsing.model.entity.shops;

import android.util.ArrayMap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.findinshop.by.parsing.model.entity.request.Request;
import com.example.findinshop.by.parsing.model.stringCorrect.StringCorrect;

public class Mila extends ShopNet {
    public static final String MILA_SHOP_NAME = "Мила";

    public static final String MILA_URL_START = "https://mila.by/search/?q=";
    public static final String MILA_URL_FINISH = "&send=Y&r=Y";
    public static final String MILA_MAIN_URL = "https://mila.by";

    public static final String MILA_ITEM_COMMON = "tabloid";

    public static final String MILA_ITEM_COMMON_FOR_ONE_PRODUCT = "catalogElement";


    private String itemCommonForOneProduct;

    public Mila() {
        super.setUrlStart(MILA_URL_START);
        super.setUrlFinish(MILA_URL_FINISH);
        super.setItemCommon(MILA_ITEM_COMMON);
        super.setShopName(MILA_SHOP_NAME);
        super.setMainUrl(MILA_MAIN_URL);
        itemCommonForOneProduct = MILA_ITEM_COMMON_FOR_ONE_PRODUCT;
    }

    public String getItemCommonForOneProduct() {
        return itemCommonForOneProduct;
    }

    public void setItemCommonForOneProduct(String itemCommonForOneProduct) {
        this.itemCommonForOneProduct = itemCommonForOneProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mila)) return false;
        if (!super.equals(o)) return false;
        Mila mila = (Mila) o;
        return Objects.equals(itemCommonForOneProduct, mila.itemCommonForOneProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), itemCommonForOneProduct);
    }

    @Override
    public String toString() {
        return "Mila{" +
                "itemCommonForOneProduct='" + itemCommonForOneProduct + '\'' +
                '}';
    }

    @Override
    public ArrayList<ArrayList<String>> searchProduct(Request request) {
        ArrayList<String> responseContainer = new ArrayList<>();
        ArrayList<ArrayList<String>> container = new ArrayList<ArrayList<String>>();

        String name = "";
        String price = "";
        String image = "";
        String link = "";

        String urlRequest = getUrlStart() + request.getRequest() + getUrlFinish();
        try {
            Document document = Jsoup.connect(urlRequest).get();

            Elements elements = document.select("div[class = " + getItemCommon() + "]");



            if (!elements.equals(null)) {

                for (Element element : elements) {

                    image = getMainUrl() + element.select("img").attr("src");

                    name = element.select("img").attr("title");
                    name = StringCorrect.stringCorrectForParsing(name);


                    String cents = element.select("div[class = price-left] > span[class = pr] > sup").text();

                    price = element.select("div[class = price-left] > span[class = pr]").text();
                    price = price.replace(cents, "") + "." + cents;

                    if (price.equals(".")) {

                        String cen = element.select("div[class = price-block] > a[class = price] > sup").text();

                        price = element.select("div[class = price-block] > a[class = price]").text();

                        price = (price.replace(cen, "") + "." + cen).replace("руб.", "");

                    }

                    for (Element it : element.select("a")) {
                        if (it.attr("href").equals("#")) {

                        } else {
                            link = getMainUrl() + it.attr("href");
                            break;
                        }
                    }
                    responseContainer.add(name);

                    responseContainer.add(price);
                    responseContainer.add(image);
                    responseContainer.add(link);


                    container.add(new ArrayList<String>(responseContainer));
                    responseContainer.clear();
                }


            } else {
                /*
                Elements elems = document.select("div[id = " + getItemCommonForOneProduct() +"]");

                if (!elems.equals(null)) {
                    Log.d("loi", "null");
                } else {
                    Log.d("loi", "NOT null");
                }
                Log.d("LOGG", String.valueOf(elems));


                for (Element el : elems) {
                    image = getMainUrl() + el.select("img").attr("src");

                    name = el.select("img").attr("title");
                    name = StringCorrect.stringCorrectForParsing(name);

                    price = el.select("div[class = price malin] > div[class = value]").text();
                    link = getMainUrl() + el.attr("href");

                    responseContainer.add(name);

                    responseContainer.add(price);
                    responseContainer.add(image);
                    responseContainer.add(link);


                    container.add(new ArrayList<String>(responseContainer));
                    responseContainer.clear();
                }

                 */
            }


            //Log.d("LOG_MILA", String.valueOf(container));



    } catch(
    Exception e)

    {
        e.printStackTrace();
    }

            return container;
}

}




