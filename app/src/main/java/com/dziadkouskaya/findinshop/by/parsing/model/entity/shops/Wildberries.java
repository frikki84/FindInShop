package com.dziadkouskaya.findinshop.by.parsing.model.entity.shops;

import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewRenderProcess;
import android.webkit.WebViewRenderProcessClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dziadkouskaya.findinshop.MainActivity;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.request.Request;
import com.dziadkouskaya.findinshop.by.parsing.model.logic.paginator.Paginator;
import com.dziadkouskaya.findinshop.by.parsing.model.stringCorrect.StringCorrect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Wildberries extends ShopNet {
    public static final String WILDBERRIES_NAME = "Wildberries";
    public static final String WILDBERRIES_URL = "https://www.wildberries.by";
    public static final String WILDBERRIES_START = "https://www.wildberries.by/catalog/0/search.aspx?search=";
    public static final String WILDBERRIES_FINISH = "&page=";
    public static final String WILDBERRIES_COMMON = "dtList i-dtList j-card-item";

    public static final int WILDBERRIES_FIRST_SEARCH_PAGE = 1;


    private int first_search_page;

    public Wildberries() {
        super.setShopName(WILDBERRIES_NAME);
        super.setMainUrl(WILDBERRIES_URL);
        super.setUrlStart(WILDBERRIES_START);
        super.setUrlFinish(WILDBERRIES_FINISH);
        super.setItemCommon(WILDBERRIES_COMMON);
        this.first_search_page = WILDBERRIES_FIRST_SEARCH_PAGE;
    }

    public int getFirst_search_page() {
        return first_search_page;
    }

    public void setFirst_search_page(int first_search_page) {
        if (first_search_page > 0) {
            this.first_search_page = first_search_page;
        }
    }

    @Override
    public ArrayList<ArrayList<String>> searchProduct(Request request) {
        //main container for data tranfer to main ArrayMap
        ArrayList<ArrayList<String>> container = new ArrayList<ArrayList<String>>();

        //arraylist for 1 item in main container
        ArrayList<String> responseContainer = new ArrayList<>();

        String urlRequest = getUrlStart() + request.getRequest() + getUrlFinish() + getFirst_search_page();
        String urlReuestForEachPage = getUrlStart() + request.getRequest() + getUrlFinish();

        try {

            int lastPage = Paginator.findResultsFromAllSearchPages(
                    urlRequest, "→", "div[class = pageToInsert] > a");
            //request for each page with results for search in the shop

            String pagesWithResualtsOfSearch = "";

            for (int i = 1; i <= lastPage; i++) {
                pagesWithResualtsOfSearch = urlReuestForEachPage + i;
                Log.d("LOGG_link", pagesWithResualtsOfSearch);

                Document document = Jsoup.connect(pagesWithResualtsOfSearch)
                        .get();

                Elements elements = document.select("div[class = " + getItemCommon() + "]");

                String name = "";
                String price = "";
                String image = "";
                String link = "";


                for (Element element : elements) {
                    link = element.select("a[class = ref_goods_n_p]").attr("href");
                    Log.d("Wild_Log_Link", link);

                    Document itemDocument = Jsoup.connect(link).get();
                    Log.d("WILD_DOC", itemDocument.text());

                    name = itemDocument.select("div[class = brand-and-name j-product-title]").text();

                    name = StringCorrect.stringCorrectForParsing(name);
                    Log.d("Wild_name", name);

                    String imageStaticURL = itemDocument.select("a[class = j-carousel-image enabledZoom current]")
                            .attr("href");
                    image = "https:" + imageStaticURL;

                    price = itemDocument.select("div [class = final-price-block]")
                            .text();
                    String price2 = itemDocument
                              .select("div [class = final-price-block]" +
                            "> span[class = discount-tooltipster-value]")
                            .text();
                    Log.d("LOG_WILD_PRICE", price);
                    Log.d("LOG_WILD_PRICE2", price2);



/*
                    Log.d("Wild_Exper", itemDocument.select("div[class = j-promo-tooltip-content hide]").text());
                    Element priceDiscount = itemDocument.select("div[class = discount-tooltipster-content] > p > span[class = discount-tooltipster-value]").first();

                    if (priceDiscount != null) {
                        String priceDiscountL = itemDocument.select("div[class = discount-tooltipster-content] > p > span[class = discount-tooltipster-value]").last().text();
                        String priceDiscountF = priceDiscount.text();

                        priceDiscountF = StringCorrect.priceCorrectionWildberries(priceDiscountF);
                        Log.d("Wild_priceDiscountF", priceDiscountF);
                        priceDiscountL = StringCorrect.priceCorrectionWildberries(priceDiscountL);
                        Log.d("Wild_priceDiscountL", priceDiscountL);

                        Float priceLit = Float.valueOf(priceDiscountF);
                        Log.d("Wild_floatLit", "" + priceLit);
                        Float priceBig = Float.valueOf(priceDiscountL);
                        Log.d("Wild_floatBig", "" + priceBig);
                        Float res = priceBig - priceLit;
                        Log.d("Wild_res", "" + res);

                        price = "";
                        price +=res;

                    } else {
                        price = itemDocument.select("span[class = final-cost]").text();
                        price = StringCorrect.priceCorrectionWildberries(price);
                    }

*/

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





