package com.dziadkouskaya.findinshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.logging.LogManager;


import com.dziadkouskaya.findinshop.by.parsing.model.entity.request.Request;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.results.ResultInfo;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.shops.Buslic;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.shops.Edostavka;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.shops.Hypermall;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.shops.Mila;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.shops.OstrovChistoty;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.shops.Oz;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.shops.ShopList;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.shops.ShopNet;
import com.dziadkouskaya.findinshop.by.parsing.model.entity.shops.Wildberries;
import com.dziadkouskaya.findinshop.by.parsing.model.logic.addElementsToHashMap.HashMapElementsAddition;
import com.dziadkouskaya.findinshop.by.parsing.model.logic.structuresForComparison.CompareHashMap;
import com.dziadkouskaya.findinshop.by.parsing.model.logic.validation.Validation;
import com.dziadkouskaya.findinshop.by.parsing.model.stringCorrect.StringCorrect;

public class MainActivity extends AppCompatActivity {


    Hypermall hypermall;
    OstrovChistoty chistoty;
    Mila mila;
    Edostavka edostavka;
    Buslic buslic;
    Oz oz;
    //Wildberries wildberries;


    ShopList list;
    ShopList listCopy;
    ArrayMap<String, ArrayList<ArrayMap<String, String>>> resultHashMap;
    ArrayMap<String, ArrayList<ArrayMap<String, String>>> linkHashMap;
    ArrayMap<String, String> imageHashMap;

    ArrayMap<String, String> middleHashMap;
    ArrayMap<String, String> middleHashMapLink;


    ArrayMap<String, ArrayList<String>> hashmapForEquals;


    EditText textForParsing;
    Button buttonForParsing;
    TextView infoShopsAmount;
    String textForRequest;
    Request request;
    ResultInfo resultInfo;
    Button btnChangeUser;
    Button btnUserManual;
    Button playMarket;


    //String myLog = "my_log";
    ArrayList<ResultInfo> mResultInfoList = new ArrayList<ResultInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textForParsing = findViewById(R.id.textForParsing);
        textForParsing.setSelected(true);
        textForParsing.setFocusable(true);


        list = new ShopList();
        chistoty = new OstrovChistoty();
        mila = new Mila();
        hypermall = new Hypermall();
        edostavka = new Edostavka();
        buslic = new Buslic();
        oz = new Oz();
        //wildberries = new Wildberries();

        list.addShopNetInList(chistoty);
        list.addShopNetInList(mila);
        list.addShopNetInList(hypermall);
        list.addShopNetInList(edostavka);
        list.addShopNetInList(oz);
        list.addShopNetInList(buslic);

        //list.addShopNetInList(wildberries);


        resultHashMap = new ArrayMap<String, ArrayList<ArrayMap<String, String>>>();
        linkHashMap = new ArrayMap<String, ArrayList<ArrayMap<String, String>>>();
        imageHashMap = new ArrayMap<>();
        hashmapForEquals = new ArrayMap<String, ArrayList<String>>();
        middleHashMap = new ArrayMap<String, String>();
        middleHashMapLink = new ArrayMap<>();

        buttonForParsing = findViewById(R.id.buttonForParsing);

        infoShopsAmount = findViewById(R.id.textShopsAmount);
        String textShops = "Поиск информации о товаре осуществляется в следующих торговых сетях: ";

        int countShops = 0;
        for (ShopNet item : list.getShopList()) {
            textShops += item.getShopName();
            countShops += 1;
            if (countShops != list.getShopList().size()) {
                textShops += ", ";
            }
        }

        infoShopsAmount.setText(textShops);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonForParsing.setClickable(false);

                textForRequest = textForParsing.getText().toString();
                while (textForRequest.length() == 0 ||
                        !Validation.validateOnlySpaces(textForRequest)) {
                    Toast toast = Toast.makeText(MainActivity.this, "Неверный " +
                            "запрос. Введите запрос еще раз", Toast.LENGTH_SHORT);
                    toast.show();
                    buttonForParsing.setClickable(true);
                    return;
                }
                Toast toastMain = Toast.makeText(MainActivity.this, "Ваш запрос " +
                        "обрабатывается", Toast.LENGTH_LONG);
                toastMain.show();
                request = new Request(textForRequest);

                ShopsParsing shopsParsing = new ShopsParsing();
                shopsParsing.execute();


            }

        };
        buttonForParsing.setOnClickListener(onClickListener);

        btnChangeUser = findViewById(R.id.changeUser);
        btnChangeUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationForm.class);
                Toast.makeText(MainActivity.this, "Идет смена пользователя", Toast.LENGTH_LONG).show();
                LogManager.getLogManager().reset();
                startActivity(intent);
            }
        });

        btnUserManual = findViewById(R.id.btn_user_manual);

        View.OnClickListener onClickListener_change_user = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserManual.class);
                startActivity(intent);
            }
        };


        btnUserManual.setOnClickListener(onClickListener_change_user);

        playMarket = findViewById(R.id.rateApp);
        playMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String playID = "com.dziadkouskaya.findinshop";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                            ("market://details?id=" + playID)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                            ("https://play.google.com/store/apps/details?id=" + playID)));
                } catch (Exception e) {
                    Log.d("LOG_EXCEPTION", "EXCEPTION");
                }
            }
        });

    }


    class ShopsParsing extends AsyncTask<Void, Void, ArrayList<ResultInfo>> {
        @Override
        protected ArrayList<ResultInfo> doInBackground(Void... voids) {

            int indName = 0;
            int indPrice = 1;
            int indImage = 2;
            int indLink = 3;

            ArrayList<ArrayList<String>> result = list.getShopList().get(0).searchProduct(request);

            resultHashMap = HashMapElementsAddition.addElementsToMainResultHashMap(result
                    , resultHashMap, list, indName, indPrice);


            linkHashMap = HashMapElementsAddition.addElementsToLinkHashMap(result, linkHashMap
                    , list, indName, indLink);

            imageHashMap = HashMapElementsAddition.addElementsToImageHashMap(result, imageHashMap
                    , indName, indImage);

            CompareHashMap.divideStringIntoPiecesFromList(result, hashmapForEquals);
            //Log.d("Compare1", String.valueOf(list.getShopList().get(0)));

            list.getShopList().remove(0);

            while (list.getShopList().size() > 0) {

                ArrayList<ArrayList<String>> innerResult = list.getShopList().get(0).searchProduct(request);

                //for (ArrayList<String> mid : innerResult) {
                //   Log.d("MIDDLE", String.valueOf(mid));
                //}

                ArrayMap<String, ArrayList<String>> hashForEqualsLittle = new ArrayMap<String, ArrayList<String>>();
                CompareHashMap.divideStringIntoPiecesFromList(innerResult, hashForEqualsLittle);

                // Log.d("Compare2", String.valueOf(list.getShopList().get(0)));

                try {
                    int n = 0;
                    for (Entry<String, ArrayList<String>> itemLittle : hashForEqualsLittle.entrySet()) {
                        int counter = 0;
                        for (Entry<String, ArrayList<String>> itemBig : hashmapForEquals.entrySet()) {
                            //Log.d("equals", itemBig.getValue() + " --- " + itemLittle.getValue());
                            //for (String l_e : itemLittle.getValue()) {
                            //    for (String b_e : itemBig.getValue()) {
                            //        Log.d("Positions_Equals", l_e + " --- " + b_e + " ---" + l_e.equals(b_e));
                            //    }
                            //}

                            if (itemLittle.getValue().equals(itemBig.getValue())) {

                                ArrayMap<String, String> innerMap = new ArrayMap<String, String>();
                                ArrayMap<String, String> innerMapLink = new ArrayMap<String, String>();


                                String inerKey = list.getShopList().get(0).getShopName();

                                String innerValue = "";
                                String innerValueLink = "";


                                for (int i = 0; i < innerResult.size(); i++) {
                                    if (innerResult.get(i).get(indName).equals(itemBig.getKey())) {
                                        innerValue += innerResult.get(i).get(indPrice);
                                        innerValueLink += innerResult.get(i).get(indLink);
                                    }
                                }
                                String innerValueForHash = innerValue.toString();
                                String innerLinkForHash = innerValueLink.toString();

                                innerMap.put(inerKey, innerValueForHash);
                                innerMapLink.put(inerKey, innerLinkForHash);

                                resultHashMap.get(itemBig.getKey()).add(innerMap);
                                linkHashMap.get(itemBig.getKey()).add(innerMapLink);


                            } else {
                                counter += 1;
                            }


                        }

                        if (counter == hashmapForEquals.size()) {
                            for (int i = 0; i < innerResult.size(); i++) {
                                if (innerResult.get(i).get(indName).equals(itemLittle.getKey())) {
                                    middleHashMap.put(innerResult.get(i).get(indName), innerResult.get(i).get(indPrice));
                                    middleHashMapLink.put(innerResult.get(i).get(indName), innerResult.get(i).get(indLink));
                                    imageHashMap.put(innerResult.get(i).get(indName), innerResult.get(i).get(indImage));
                                    counter = 0;
                                }
                            }
                        }
                        n += 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (Entry<String, String> itemMid : middleHashMap.entrySet()) {
                    ArrayMap<String, String> innerMap = new ArrayMap<String, String>();
                    String inerKey = list.getShopList().get(0).getShopName();
                    String innerValue = itemMid.getValue();
                    innerMap.put(inerKey, innerValue);
                    ArrayList<ArrayMap<String, String>> valueArray = new ArrayList<ArrayMap<String, String>>();
                    valueArray.add(innerMap);
                    String innerRequest = StringCorrect.stringCorrectForParsing(itemMid.getKey());
                    resultHashMap.put(innerRequest, valueArray);

                }
                //for (ArrayMap.Entry<String, ArrayList<ArrayMap<String, String>>> mid : resultHashMap.entrySet()) {
                //    Log.d("result", String.valueOf(mid.getKey() + " --- " + mid.getValue()));
                //}

                for (Entry<String, String> itemMid : middleHashMapLink.entrySet()) {
                    ArrayMap<String, String> innerMap = new ArrayMap<String, String>();
                    String inerKey = list.getShopList().get(0).getShopName();
                    String innerValue = itemMid.getValue();
                    innerMap.put(inerKey, innerValue);
                    ArrayList<ArrayMap<String, String>> valueArray = new ArrayList<ArrayMap<String, String>>();
                    valueArray.add(innerMap);
                    String innerRequest = StringCorrect.stringCorrectForParsing(itemMid.getKey().toString());
                    linkHashMap.put(innerRequest, valueArray);

                }

                CompareHashMap.divideStringIntoPiecesFromMap(resultHashMap, hashmapForEquals);
                middleHashMap.clear();
                middleHashMapLink.clear();


                list.getShopList().remove(0);
            }


            for (ArrayMap.Entry<String, ArrayList<ArrayMap<String, String>>> entry : resultHashMap.entrySet()) {
                String name;
                ArrayList<String> shopPrice = new ArrayList<>();
                ArrayList<String> links = new ArrayList<>();
                String imageUrl = "";


                name = entry.getKey();

                String shPr;

                for (ArrayMap<String, String> item : entry.getValue()) {
                    for (ArrayMap.Entry<String, String> it : item.entrySet()) {
                        shPr = it.getKey() + " --- " + it.getValue();
                        shopPrice.add(shPr);
                    }
                }


                String link = "";
                for (ArrayMap.Entry<String, ArrayList<ArrayMap<String, String>>> it : linkHashMap.entrySet()) {
                    if (entry.getKey().equals(it.getKey())) {
                        for (ArrayMap<String, String> vEnt : entry.getValue()) {
                            for (ArrayMap<String, String> vIt : it.getValue()) {
                                for (ArrayMap.Entry<String, String> vEntL : vEnt.entrySet()) {
                                    for (ArrayMap.Entry<String, String> vItL : vIt.entrySet()) {

                                        if (vEntL.getKey().equals(vItL.getKey())) {
                                            link = vItL.getValue();
                                            links.add(link);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                for (ArrayMap.Entry<String, String> pi : imageHashMap.entrySet()) {
                    if (entry.getKey().equals(pi.getKey())) {
                        imageUrl = pi.getValue();
                    }
                }


                mResultInfoList.add(new ResultInfo(name, shopPrice, links, imageUrl));


            }
            resultHashMap.clear();
            linkHashMap.clear();
            imageHashMap.clear();
            hashmapForEquals.clear();
            middleHashMap.clear();
            middleHashMapLink.clear();

            list.addShopNetInList(chistoty);
            list.addShopNetInList(mila);
            list.addShopNetInList(hypermall);
            list.addShopNetInList(edostavka);
            list.addShopNetInList(oz);
            list.addShopNetInList(buslic);

           // list.addShopNetInList(wildberries);
            request.setRequest("");




            return mResultInfoList;
        }

        @Override
        protected void onPostExecute(ArrayList<ResultInfo> mResultInfoList) {
            /*action for clearning MainActivity after going to the 2 page + making
            button "Find" Clickable
            */

            Intent intent = new Intent(MainActivity.this, ResultList.class);
            intent.putParcelableArrayListExtra("result"
                    , (ArrayList<? extends Parcelable>) mResultInfoList);

            startActivity(intent);

            mResultInfoList.clear();
            buttonForParsing.setClickable(true);


        }

    }
}

